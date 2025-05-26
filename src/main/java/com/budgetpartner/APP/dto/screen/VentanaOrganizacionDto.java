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

    private OrganizacionDtoResponse organizacionDto;
    private PlanDtoResponse planDto;
    private List<MiembroDtoResponse> miembrosDto;

    public VentanaOrganizacionDto(OrganizacionDtoResponse organizacionDto, PlanDtoResponse planDto, List<MiembroDtoResponse> miembrosDto) {
        this.organizacionDto = organizacionDto;
        this.planDto = planDto;
        this.miembrosDto = miembrosDto;
    }


}
