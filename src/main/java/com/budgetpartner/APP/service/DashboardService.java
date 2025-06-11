package com.budgetpartner.APP.service;

import com.budgetpartner.APP.dto.dashborard.DashboardDtoResponse;
import com.budgetpartner.APP.dto.token.TokenDtoRequest;
import com.budgetpartner.APP.entity.Usuario;
import com.budgetpartner.APP.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DashboardService {

    @Autowired
    PlanService planService;
    @Autowired
    MiembroService miembroService;
    @Autowired
    TareaService tareaService;
    @Autowired
    UsuarioService usuarioService;

    public DashboardDtoResponse getDashboard(String authHeader){

        Usuario usuario = usuarioService.validarTokenYDevolverUsuario(authHeader);

        Long idUsuario = usuario.getId();

        Integer numOrganizaciones = miembroService.contarMiembrosPorUsuario(idUsuario);
        Integer numPlanes = planService.contarPlanesPorUsuario(idUsuario);
        Integer numTareas = tareaService.contarTareasPorUsuario(idUsuario);

        DashboardDtoResponse dto = new DashboardDtoResponse(numOrganizaciones, numPlanes, numTareas);
        return dto;
    }

}
