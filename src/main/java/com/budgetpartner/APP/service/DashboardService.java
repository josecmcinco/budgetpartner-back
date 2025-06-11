package com.budgetpartner.APP.service;

import com.budgetpartner.APP.dto.dashborard.DashboardDtoResponse;
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

    public DashboardDtoResponse getDashboard(){

        Long idUsuario = 0L;

        Integer numOrganizaciones = miembroService.contarMiembrosPorUsuario(idUsuario);
        Integer numPlanes = planService.contarPlanesPorUsuario(idUsuario);
        Integer numTareas = tareaService.contarTareasPorUsuario(idUsuario);

        DashboardDtoResponse dto = new DashboardDtoResponse(numOrganizaciones, numPlanes, numTareas);
        return dto;
    }

}
