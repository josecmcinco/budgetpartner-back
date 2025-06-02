package com.budgetpartner.APP.service;

import com.budgetpartner.APP.dto.dashborard.DashboardDtoResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DashboardService {

    @Autowired
    UsuarioService usuarioService;

    public DashboardDtoResponse getDashboard(){

        Long idUsuario = 0L;

        Integer numOrganizaciones = usuarioService.contarOrganizacionesPorUsuario(idUsuario);
        Integer numPlanes = usuarioService.contarPlanesPorUsuario(idUsuario);
        Integer numTareas = usuarioService.contarTareasPorUsuario(idUsuario);

        DashboardDtoResponse dto = new DashboardDtoResponse(numOrganizaciones, numPlanes, numTareas);
        return dto;
    }

}
