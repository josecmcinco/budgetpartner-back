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

@Service
public class GastoService {

    @Autowired
    private GastoRepository gastoRepository;
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private TareaRepository tareaRepository;
    @Autowired
    private PlanRepository planRepository;
    @Autowired
    private MiembroRepository miembroRepository;
    @Autowired
    private RepartoGastoRepository repartoGastoRepository;
    @Autowired
    private DivisaService divisaService;
    @Autowired
    private OrganizacionRepository organizacionRepository;

    //ESTRUCTURA GENERAL DE LA LÓGICA DE LOS CONTROLADORES
    //Pasar de DtoRequest a Entity-> Insertar en DB->Pasar de Entity a DtoRequest->Return

    //ENDPOINTS

    //Llamada para Endpoint
    //Crea una Entidad usando el DTO recibido por el usuario
    @Transactional //Interacción con un many to many
    public GastoDtoResponse postGasto(GastoDtoPostRequest gastoDtoReq) {
        //TODO VALIDAR CAMPOS REPETIDOS (DESCRIPCIÓN, MONTO, FECHA, ETC.)

        //Validar Token
        Usuario usuario = usuarioService.devolverUsuarioAutenticado();
        //Ver que permisos ok
        //TODO

        //Obtener elementos necesiarios para insertar el gasto


        Plan plan = planRepository.findById(gastoDtoReq.getPlanId())
                .orElseThrow(() -> new NotFoundException("Plan no encontrado con id: " + gastoDtoReq.getPlanId()));

        //Valor por defecto de la tarea asignada
        Tarea tarea = null;

        //Gestión de los planes por tipo
        if(plan.getModoPlan().equals(ModoPlan.simple) && gastoDtoReq.getTareaId() != null){
            throw new BadRequestException("Se está tratando de asignar una tarea a un gasto en un plan simple");}

        else if(gastoDtoReq.getTareaId() != null){
            tarea = tareaRepository.findById(gastoDtoReq.getTareaId())
                .orElseThrow(() -> new NotFoundException("Gasto no encontrado con id: " + gastoDtoReq.getPlanId()));}


        Miembro pagador = miembroRepository.findById(gastoDtoReq.getPagadorId())
                .orElseThrow(() -> new NotFoundException("Miembro no encontrado con id: " + gastoDtoReq.getPlanId()));;

        //Crea el gasto
        Gasto gasto = GastoMapper.toEntity(gastoDtoReq, tarea, plan, pagador);
        //Enviar elemento insertado en la db porque tiene el id
        gasto = gastoRepository.save(gasto);

        //Crea las deudas de cada miembro en base al gasto
        List<Long> idEndeudadosList = gastoDtoReq.getListaMiembrosEndeudados();
        postRepartoGastos(idEndeudadosList, gasto, pagador);

        return GastoMapper.toDtoResponse(gasto);
    }

    //Llamada para Endpoint
    //Obtiene una Entidad usando el id recibido por el usuario
    /*DEVUELVE AL USUARIO:

     */
    public GastoDtoResponse getGastoDtoById(Long id) {
        //Obtener gasto usando el id pasado en la llamada
        Gasto gasto = gastoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Gasto no encontrado con id: " + id));

        List<Miembro> miembrosEndeudados = gastoRepository.findMiembrosByGastoId(id);

        GastoDtoResponse gastoDtoResp = GastoMapper.toDtoResponse(gasto);
        return gastoDtoResp;

    }

    //Llamada para Endpoint
    //Elimina una Entidad usando el id recibido por el usuario
    public Gasto deleteGastoById(Long id) {
        //Obtener gasto usando el id pasado en la llamada
        Gasto gasto = gastoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Gasto no encontrado con id: " + id));

        //Elimina primero la relación muchos a muchos con Miembro
        repartoGastoRepository.eliminarRepartoGastoPorGastoId(id);

        //Elimina el gasto en cascada
        gastoRepository.delete(gasto);
        return gasto;
    }

    //Llamada para Endpoint
    //Actualiza una Entidad usando el id recibido por el usuario
    @Transactional //Interacción con un many to many
    public Gasto patchGasto(GastoDtoUpdateRequest gastoDtoResp, Long id) {
        //Obtener gasto usando el id pasado en la llamada
        Gasto gasto = gastoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Gasto no encontrado con id: " + id));

        GastoMapper.updateEntityFromDtoRes(gastoDtoResp, gasto);
        gastoRepository.save(gasto);

        //Crea las deudas de cada miembro en base al gasto
        if(gastoDtoResp.getListaMiembrosEndeudados() != null){
            //Eliminar las reparticiones previas
            repartoGastoRepository.eliminarRepartoGastoPorGastoId(id);
            //Añadir reparticiones nuevas
            List<Long> idEndeudadosList = gastoDtoResp.getListaMiembrosEndeudados();
            postRepartoGastos(idEndeudadosList, gasto, gasto.getPagador());
        }

        return gasto;
    }


    //OTROS MÉTODOS

    //Crea las deudas de cada miembro en base al gasto
    public void postRepartoGastos(List<Long> idEndeudadosList, Gasto gasto, Miembro pagador){
        double cantidad;
        Organizacion organizacion = organizacionRepository.obtenerOrganizacionPorPlanId(gasto.getPlan().getId())
                .orElseThrow(() -> new NotFoundException("Organización no encontrada con id: " + gasto.getPlan().getId()));
        if(organizacion.getMoneda() == gasto.getMoneda()){
            cantidad =  gasto.getCantidad();
        }
        else{
            cantidad = divisaService.convertCurrency(gasto.getCantidad(), gasto.getMoneda(), organizacion.getMoneda());
        }

        //Obtener deuda como double con dos decimales
        int prepDeudaSinDecimales = (int) cantidad * 100 / idEndeudadosList.size();
        double deudaPorPersona = (double) prepDeudaSinDecimales / 100 ;

        //Crear un elemento repartoDeuda por cada endeudado y meterlo en la DB
        for (Long idEndeudado: idEndeudadosList){
            Miembro miembro = miembroRepository.findById(idEndeudado)
                    .orElseThrow(() -> new NotFoundException("Miembro no encontrado con id: " + idEndeudado));


            RepartoGasto reparto = new RepartoGasto();
            reparto.setGasto(gasto);
            reparto.setMiembro(miembro);

            //Asignar gasto de pico al pagador
            if(Objects.equals(pagador.getId(), idEndeudado)){reparto.setCantidad(BigDecimal.valueOf(deudaPorPersona * (idEndeudadosList.size() -1)));}
            else{reparto.setCantidad(BigDecimal.valueOf(-deudaPorPersona));}

            reparto.setId(new RepartoGastoId(gasto.getId(), miembro.getId()));

            repartoGastoRepository.save(reparto);
        }

    }

}
