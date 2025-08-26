package com.budgetpartner.APP.service;

import com.budgetpartner.APP.dto.gasto.GastoDtoPostRequest;
import com.budgetpartner.APP.dto.gasto.GastoDtoResponse;
import com.budgetpartner.APP.dto.gasto.GastoDtoUpdateRequest;
import com.budgetpartner.APP.entity.*;
import com.budgetpartner.APP.enums.ModoPlan;
import com.budgetpartner.APP.exceptions.BadRequestException;
import com.budgetpartner.APP.exceptions.NotFoundException;
import com.budgetpartner.APP.mapper.GastoMapper;
import com.budgetpartner.APP.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

/**
 * Servicio encargado de gestionar gastos:
 * creación, consulta, actualización y eliminación,
 * así como la asignación de deudas entre miembros.
 */
@Service
public class GastoService {

    private final GastoRepository gastoRepository;
    private final AutorizacionService autorizacionService;
    private final TareaRepository tareaRepository;
    private final PlanRepository planRepository;
    private final MiembroRepository miembroRepository;
    private final RepartoGastoRepository repartoGastoRepository;
    private final DivisaService divisaService;
    private final OrganizacionRepository organizacionRepository;

    // Constructor para inyección de dependencias
    @Autowired
    public GastoService(GastoRepository gastoRepository,
                        AutorizacionService autorizacionService,
                        TareaRepository tareaRepository,
                        PlanRepository planRepository,
                        MiembroRepository miembroRepository,
                        RepartoGastoRepository repartoGastoRepository,
                        DivisaService divisaService,
                        OrganizacionRepository organizacionRepository) {
        this.gastoRepository = gastoRepository;
        this.autorizacionService = autorizacionService;
        this.tareaRepository = tareaRepository;
        this.planRepository = planRepository;
        this.miembroRepository = miembroRepository;
        this.repartoGastoRepository = repartoGastoRepository;
        this.divisaService = divisaService;
        this.organizacionRepository = organizacionRepository;
    }

    /**
     * Crea un nuevo gasto y genera las deudas correspondientes
     * entre los miembros implicados.
     *
     * @param gastoDtoReq DTO con los datos del gasto a crear
     * @return DTO del gasto creado, incluyendo el ID
     * @throws BadRequestException si hay inconsistencias con el tipo de plan o tarea
     * @throws NotFoundException si algún recurso relacionado no se encuentra
     */
    @Transactional //Interacción con un many to many
    public GastoDtoResponse postGasto(GastoDtoPostRequest gastoDtoReq) {
        //TODO VALIDAR CAMPOS REPETIDOS (DESCRIPCIÓN, MONTO, FECHA, ETC.)

        // Validar usuario autenticado
        Usuario usuario = autorizacionService.devolverUsuarioAutenticado();
        // TODO: validar permisos del usuario

        // Recuperar plan asociado
        Plan plan = planRepository.findById(gastoDtoReq.getPlanId())
                .orElseThrow(() -> new NotFoundException("Plan no encontrado con id: " + gastoDtoReq.getPlanId()));

        // Valor por defecto de tarea
        Tarea tarea = null;

        // Validaciones según tipo de plan y presencia de tarea
        if(plan.getModoPlan().equals(ModoPlan.simple) && gastoDtoReq.getTareaId() != null){
            throw new BadRequestException("Se está tratando de asignar una tarea a un gasto en un plan simple");}
        else if(gastoDtoReq.getTareaId() != null){
            tarea = tareaRepository.findById(gastoDtoReq.getTareaId())
                .orElseThrow(() -> new NotFoundException("Gasto no encontrado con id: " + gastoDtoReq.getPlanId()));}

        // Recuperar pagador
        Miembro pagador = miembroRepository.findById(gastoDtoReq.getPagadorId())
                .orElseThrow(() -> new NotFoundException("Miembro no encontrado con id: " + gastoDtoReq.getPagadorId()));

        // Mapear DTO a entidad y guardar
        Gasto gasto = GastoMapper.toEntity(gastoDtoReq, tarea, plan, pagador);
        gasto = gastoRepository.save(gasto);

        // Crear deudas de cada miembro
        List<Long> idEndeudadosList = gastoDtoReq.getListaMiembrosEndeudados();
        postRepartoGastos(idEndeudadosList, gasto, pagador);

        return GastoMapper.toDtoResponse(gasto);
    }

    /**
     * Recupera un gasto por su ID.
     *
     * @param id ID del gasto
     * @return DTO del gasto
     * @throws NotFoundException si el gasto no existe
     */
    public GastoDtoResponse getGastoDtoById(Long id) {
        Gasto gasto = gastoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Gasto no encontrado con id: " + id));

        List<Miembro> miembrosEndeudados = gastoRepository.findMiembrosByGastoId(id);

        //TODO: obtener lista de miembros endeudados si es necesario
        return GastoMapper.toDtoResponse(gasto);

    }

    /**
     * Actualiza un gasto existente y sus deudas asociadas.
     *
     * @param gastoDtoReq DTO con los datos a actualizar
     * @param id          ID del gasto a actualizar
     * @return entidad Gasto actualizada
     * @throws NotFoundException si el gasto no existe
     */
    @Transactional //Interacción con un many to many
    public Gasto patchGasto(GastoDtoUpdateRequest gastoDtoReq, Long id) {
        Gasto gasto = gastoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Gasto no encontrado con id: " + id));

        // Actualizar campos de gasto
        GastoMapper.updateEntityFromDtoRes(gastoDtoReq, gasto);
        gastoRepository.save(gasto);

        // Actualizar repartición de deudas si se proporcionan miembros endeudados
        if (gastoDtoReq.getListaMiembrosEndeudados() != null) {
            repartoGastoRepository.eliminarRepartoGastoPorGastoId(id);
            List<Long> idEndeudadosList = gastoDtoReq.getListaMiembrosEndeudados();
            postRepartoGastos(idEndeudadosList, gasto, gasto.getPagador());
        }

        return gasto;
    }

    /**
     * Elimina un gasto y sus relaciones con miembros endeudados.
     *
     * @param id ID del gasto a eliminar
     * @throws NotFoundException si el gasto no existe
     */
    public Gasto deleteGastoById(Long id) {
        Gasto gasto = gastoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Gasto no encontrado con id: " + id));

        // Eliminar relaciones many-to-many
        repartoGastoRepository.eliminarRepartoGastoPorGastoId(id);

        // Eliminar gasto en cascada
        gastoRepository.delete(gasto);
        return gasto;
    }


    /**
     * Crea las deudas de cada miembro según el gasto.
     *
     * @param idEndeudadosList lista de IDs de miembros endeudados
     * @param gasto            gasto asociado
     * @param pagador          miembro que pagó el gasto
     */
    public void postRepartoGastos(List<Long> idEndeudadosList, Gasto gasto, Miembro pagador) {
        Organizacion organizacion = organizacionRepository.obtenerOrganizacionPorPlanId(gasto.getPlan().getId())
                .orElseThrow(() -> new NotFoundException("Organización no encontrada con id: " + gasto.getPlan().getId()));

        // Convertir monto a moneda de la organización si es necesario
        double cantidad = (organizacion.getMoneda() == gasto.getMoneda()) ?
                gasto.getCantidad() :
                divisaService.convertCurrency(gasto.getCantidad(), gasto.getMoneda(), organizacion.getMoneda());

        // Calcular deuda por persona con dos decimales
        int prepDeudaSinDecimales = (int) cantidad * 100 / idEndeudadosList.size();
        double deudaPorPersona = (double) prepDeudaSinDecimales / 100;

        // Crear repartos de gasto
        for (Long idEndeudado : idEndeudadosList) {
            Miembro miembro = miembroRepository.findById(idEndeudado)
                    .orElseThrow(() -> new NotFoundException("Miembro no encontrado con id: " + idEndeudado));

            RepartoGasto reparto = new RepartoGasto();
            reparto.setGasto(gasto);
            reparto.setMiembro(miembro);

            // Ajustar monto para el pagador
            if (Objects.equals(pagador.getId(), idEndeudado)) {
                reparto.setCantidad(BigDecimal.valueOf(deudaPorPersona * (idEndeudadosList.size() - 1)));
            } else {
                reparto.setCantidad(BigDecimal.valueOf(-deudaPorPersona));
            }

            reparto.setId(new RepartoGastoId(gasto.getId(), miembro.getId()));
            repartoGastoRepository.save(reparto);
        }
    }

}
