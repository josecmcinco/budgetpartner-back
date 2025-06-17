package com.budgetpartner.APP.service;

import com.budgetpartner.APP.dto.dashborard.DashboardDtoResponse;
import com.budgetpartner.APP.dto.token.TokenDtoRequest;
import com.budgetpartner.APP.dto.usuario.UsuarioDtoResponse;
import com.budgetpartner.APP.entity.Usuario;
import com.budgetpartner.APP.exceptions.NotFoundException;
import com.budgetpartner.APP.mapper.UsuarioMapper;
import com.budgetpartner.APP.repository.MiembroRepository;
import com.budgetpartner.APP.repository.PlanRepository;
import com.budgetpartner.APP.repository.TareaRepository;
import com.budgetpartner.APP.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DashboardService {

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    PlanRepository planRepository;
    @Autowired
    MiembroRepository miembroRepository;
    @Autowired
    TareaRepository tareaRepository;


    /*
     Llamada para Endpoint

    DEVUELVE AL USUARIO:
        usuario : objeto completo con todos sus atributos
        numeroOrganizaciones/NumeroMiembros : number
        numeroPlanes : number
        numeroTareas : number
        actividadReciente : array de objetos*/
    public DashboardDtoResponse getDashboard(){

        //Validar Token
        Usuario usuario = usuarioService.devolverUsuarioAutenticado();
        Long idUsuario = usuario.getId();

        Integer numOrganizaciones = miembroRepository.contarMiembrosPorUsuarioId(idUsuario);
        Integer numPlanes = planRepository.contarPlanesPorUsuarioId(idUsuario);
        Integer numTareas = tareaRepository.contarTareasPorUsuarioId(idUsuario);

        UsuarioDtoResponse usuarioDtoResponse = UsuarioMapper.toDtoResponse(usuario);

        DashboardDtoResponse dto = new DashboardDtoResponse(usuarioDtoResponse, numOrganizaciones, numPlanes, numTareas);
        return dto;
    }

}
