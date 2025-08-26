package com.budgetpartner.APP.service;

import com.budgetpartner.APP.dto.dashborard.DashboardDtoResponse;
import com.budgetpartner.APP.dto.usuario.UsuarioDtoResponse;
import com.budgetpartner.APP.entity.Usuario;
import com.budgetpartner.APP.mapper.UsuarioMapper;
import com.budgetpartner.APP.repository.MiembroRepository;
import com.budgetpartner.APP.repository.PlanRepository;
import com.budgetpartner.APP.repository.TareaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DashboardService {

    private final AutorizacionService autorizacionService;
    private final PlanRepository planRepository;
    private final MiembroRepository miembroRepository;
    private final TareaRepository tareaRepository;

    @Autowired
    public DashboardService(AutorizacionService autorizacionService,
                   PlanRepository planRepository,
                   MiembroRepository miembroRepository,
                   TareaRepository tareaRepository) {
        this.autorizacionService = autorizacionService;
        this.planRepository = planRepository;
        this.miembroRepository = miembroRepository;
        this.tareaRepository = tareaRepository;
    }

    /**
     * Obtiene la información del dashboard para el usuario autenticado.
     *
     * Devuelve:
     * - usuario: objeto con sus atributos en formato DTO
     * - numeroOrganizaciones: cantidad de organizaciones a las que pertenece
     * - numeroPlanes: cantidad de planes asignados
     * - numeroTareas: cantidad de tareas asignadas
     *
     * @return DashboardDtoResponse con la información del usuario y estadísticas principales
     */
    public DashboardDtoResponse getDashboard(){

        //Validar y obtener usuario autentificado
        Usuario usuario = autorizacionService.devolverUsuarioAutenticado();
        Long idUsuario = usuario.getId();

        //Extraer información dashboard
        Integer numOrganizaciones = miembroRepository.contarMiembrosPorUsuarioId(idUsuario);
        Integer numPlanes = planRepository.contarPlanesPorUsuarioId(idUsuario);
        Integer numTareas = tareaRepository.contarTareasPorUsuarioId(idUsuario);

        //Crear dashboard y devolverlo con la información
        UsuarioDtoResponse usuarioDtoResponse = UsuarioMapper.toDtoResponse(usuario);
        return new DashboardDtoResponse(usuarioDtoResponse, numOrganizaciones, numPlanes, numTareas);
    }

}
