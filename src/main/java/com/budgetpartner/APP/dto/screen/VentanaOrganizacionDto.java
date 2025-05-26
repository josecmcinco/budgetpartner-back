package com.budgetpartner.APP.dto.screen;

import com.budgetpartner.APP.dto.response.MiembroDtoResponse;
import com.budgetpartner.APP.dto.response.OrganizacionDtoResponse;
import com.budgetpartner.APP.dto.response.PlanDtoResponse;
import com.budgetpartner.APP.entity.Miembro;
import com.budgetpartner.APP.entity.Organizacion;
import com.budgetpartner.APP.entity.Plan;

import java.util.Arrays;
import java.util.List;

public class VentanaOrganizacionDto {

    private OrganizacionDtoResponse organizacion;
    private Plan Plan;
    private List<Miembro> miembros;
/*
    public VentanaOrganizacionDto(OrganizacionDtoResponse organizacion, PlanDtoResponse plan, List<MiembroDtoResponse> miembros) {
        this.organizacion = organizacion;
        Plan = plan;
        this.miembros = miembros;
    }*/

}
