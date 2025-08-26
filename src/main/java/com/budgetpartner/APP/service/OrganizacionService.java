package com.budgetpartner.APP.service;

import com.budgetpartner.APP.dto.gasto.GastoDtoResponse;
import com.budgetpartner.APP.dto.miembro.MiembroDtoResponse;
import com.budgetpartner.APP.dto.organizacion.OrganizacionDtoPostRequest;
import com.budgetpartner.APP.dto.organizacion.OrganizacionDtoResponse;
import com.budgetpartner.APP.dto.organizacion.OrganizacionDtoUpdateRequest;
import com.budgetpartner.APP.dto.plan.PlanDtoResponse;
import com.budgetpartner.APP.entity.*;
import com.budgetpartner.APP.enums.NombreRol;
import com.budgetpartner.APP.mapper.GastoMapper;
import com.budgetpartner.APP.mapper.MiembroMapper;
import com.budgetpartner.APP.mapper.OrganizacionMapper;
import com.budgetpartner.APP.mapper.PlanMapper;
import com.budgetpartner.APP.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.budgetpartner.APP.exceptions.NotFoundException;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Servicio encargado de la gestión de organizaciones.
 * Permite crear, consultar, actualizar, eliminar y obtener listas de organizaciones.
 */
@Service
public class OrganizacionService {

    private final AutorizacionService autorizacionService;
    private final OrganizacionRepository organizacionRepository;
    private final MiembroRepository miembroRepository;
    private final GastoRepository gastoRepository;
    private final PlanRepository planRepository;
    private final RolRepository rolRepository;

    @Autowired
    public OrganizacionService(AutorizacionService autorizacionService,
                               OrganizacionRepository organizacionRepository,
                               MiembroRepository miembroRepository,
                               GastoRepository gastoRepository,
                               PlanRepository planRepository,
                               RolRepository rolRepository) {
        this.autorizacionService = autorizacionService;
        this.organizacionRepository = organizacionRepository;
        this.miembroRepository = miembroRepository;
        this.gastoRepository = gastoRepository;
        this.planRepository = planRepository;
        this.rolRepository = rolRepository;
    }

    /**
     * Crea una organización y asigna un miembro administrador como creador.
     *
     * @param organizacionDtoReq DTO con los datos de la organización y nick del miembro creador
     * @return DTO de la organización creada con el miembro creador incluido
     */
    public OrganizacionDtoResponse postOrganizacion(OrganizacionDtoPostRequest organizacionDtoReq){

        //Autenticar el usuario
        Usuario usuario = autorizacionService.devolverUsuarioAutenticado();

        // Crear entidad Organización
        Organizacion organizacion = OrganizacionMapper.toEntity(organizacionDtoReq);
        organizacion = organizacionRepository.save(organizacion);

        // Obtener rol ADMIN
        Rol rol = rolRepository.obtenerRolPorNombre(NombreRol.ROLE_ADMIN)
                .orElseThrow(() -> new NotFoundException("ERROR INTERNO"));

        // Crear miembro administrador
        Miembro miembro = new Miembro(organizacion, rol, organizacionDtoReq.getNickMiembroCreador());
        miembro.setUsuario(usuario);
        miembro.setAsociado(true);
        miembro.setFechaIngreso(LocalDateTime.now());
        miembro = miembroRepository.save(miembro);


        MiembroDtoResponse miembroDtoRes = MiembroMapper.toDtoResponse(miembro);

        OrganizacionDtoResponse organizacionDtoResp =  OrganizacionMapper.toDtoResponse(organizacion);

        //Preparar dto antes de enviarlo
        organizacionDtoResp.setMiembros(List.of(miembroDtoRes));
        organizacionDtoResp.setMiembroCreador(miembro.getId());

        return organizacionDtoResp;
    }

    /**
     * Obtiene una organización por su ID junto con sus miembros y planes.
     *
     * @param id ID de la organización
     * @return DTO de la organización
     * @throws NotFoundException si la organización no existe
     */
    public OrganizacionDtoResponse getOrganizacionDtoById(Long id) {
        Organizacion organizacion = organizacionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Organizacion no encontrada con id: " + id));

        OrganizacionDtoResponse organizacionDto = OrganizacionMapper.toDtoResponse(organizacion);


        //Agregar miembros
        List<Miembro> miembros = miembroRepository.obtenerMiembrosPorOrganizacionId(organizacionDto.getId());
        List<MiembroDtoResponse> ListMiembroDto = MiembroMapper.toDtoResponseListMiembro(miembros);
        organizacionDto.setMiembros(ListMiembroDto);

        // Agregar planes y sus gastos
        List<Plan> planes = planRepository.obtenerPlanesPorOrganizacionId(organizacionDto.getId());
        List<PlanDtoResponse> ListPlanDto = PlanMapper.toDtoResponseListPlan(planes);
        for ( PlanDtoResponse planDto: ListPlanDto) {
            List<Gasto> gastos = gastoRepository.obtenerGastosPorPlanId(planDto.getId());
            List<GastoDtoResponse> ListGastoDto = GastoMapper.toDtoResponseListGasto(gastos);
            planDto.setGastos(ListGastoDto);
        }

        organizacionDto.setPlanes(ListPlanDto);

        return organizacionDto;
    }


    /**
     * Elimina una organización y sus planes asociados.
     *
     * @param organizacionId ID de la organización
     * @return entidad Organización eliminada
     * @throws NotFoundException si la organización no existe
     */
    @Transactional //Implica interactuar con el muchos a muchos de repartoGastos y repartoTareas
    public Organizacion deleteOrganizacionById(Long organizacionId) {
        //Obtener organización usando el id pasado en la llamada
        Organizacion organizacion = organizacionRepository.findById(organizacionId)
                .orElseThrow(() -> new NotFoundException("Organización no encontrada con id: " + organizacionId));

        /*
        IMPORTANTE
        Esta linea es hacer una llamada que devuelva los planes,
        así, el gestor la DB tiene una instancia de ellos antes del delete
        */
        // Eliminar planes asociados primero
        List<Plan> listaPlanes = planRepository.obtenerPlanesPorOrganizacionId(organizacionId);
        for (Plan plan : listaPlanes) {
            planRepository.delete(plan);
        }

        // Eliminar organización en cascada
        organizacionRepository.delete(organizacion);
        return organizacion;
    }

    /**
     * Actualiza los datos de una organización existente.
     *
     * @param dto DTO con los datos a actualizar
     * @param id  ID de la organización
     * @throws NotFoundException si la organización no existe
     */
    public void patchOrganizacion(OrganizacionDtoUpdateRequest dto, Long id) {
        Organizacion organizacion = organizacionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Organización no encontrada con id: " + id));

        OrganizacionMapper.updateEntityFromDtoRes(dto, organizacion);
        organizacionRepository.save(organizacion);
        OrganizacionMapper.toDtoResponse(organizacion);
    }

    /**
     * Obtiene todas las organizaciones asociadas al usuario autenticado.
     *
     * @return lista de DTOs de organizaciones con número de miembros
     */
    public List<OrganizacionDtoResponse> getOrganizacionesDtoByUsuarioId() {


        Usuario usuario = autorizacionService.devolverUsuarioAutenticado();
        List<Organizacion> organizaciones = organizacionRepository.obtenerOrganizacionesPorUsuarioId(usuario.getId());

        //TODO gestionar sin organizacion

        List<OrganizacionDtoResponse> ListaDtoOrganizacionDto = OrganizacionMapper.toDtoResponseListOrganizacion(organizaciones);

        // Añadir número de miembros a cada DTO
        for (OrganizacionDtoResponse organizacionDto : ListaDtoOrganizacionDto) {
            int numeroMiembros = miembroRepository.contarMiembrosPorOrganizacionId(organizacionDto.getId());
            organizacionDto.setNumeroMiembros(numeroMiembros);
        }

        return  ListaDtoOrganizacionDto;
    }

}
