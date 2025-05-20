package com.budgetpartner.APP.mapper;

import com.budgetpartner.APP.dto.request.PlanDtoRequest;
import com.budgetpartner.APP.entity.Plan;
import com.budgetpartner.APP.dto.response.PlanDtoResponse;
import com.budgetpartner.APP.service.OrganizacionService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PlanMapper {
    @Autowired
    private static OrganizacionService organizacionService;

    // Convierte Plan en PlanDtoResponse
    public static PlanDtoResponse toDtoResponse(Plan plan) {
        if (plan == null) return null;

        return new PlanDtoResponse(
                plan.getId(),
                plan.getOrganizacion(),
                plan.getNombre(),
                plan.getDescripcion(),
                plan.getFechaInicio(),
                plan.getFechaFin()
        );
    }

    // Convierte PlanDtoRequest en Plan
    public static Plan toEntity(PlanDtoRequest dto) {
        if (dto == null) return null;

        return new Plan(
                organizacionService.getOrganizacionById(dto.getOrganizacion()),
                dto.getNombre(),
                dto.getDescripcion(),
                dto.getFechaInicio(),
                dto.getFechaFin()
        );
    }

    // Actualiza entidad existente con los valores del DTO
    public static void updateEntityFromDtoRes(PlanDtoRequest dto, Plan plan) {
        if (dto == null || plan == null) return;

        //NO SE PERMITE MODIFICAR DESDE EL DTO:
        //Organizacion
        if (dto.getNombre() != null) plan.setNombre(dto.getNombre());
        if (dto.getDescripcion() != null) plan.setDescripcion(dto.getDescripcion());
        if (dto.getFechaInicio() != null) plan.setFechaInicio(dto.getFechaInicio());
        if (dto.getFechaFin() != null) plan.setFechaFin(dto.getFechaFin());
    }
    public static List<PlanDtoResponse> toDtoResponseListPlan(List<Plan> planes) {
        List<PlanDtoResponse> listaPlanesDtoResp = new ArrayList<>();
        if (planes == null || planes.isEmpty()) {
            return Collections.emptyList();
        } else {
            for (Plan plan : planes) {
                PlanDtoResponse planDtoResp = PlanMapper.toDtoResponse(plan);
                listaPlanesDtoResp.add(planDtoResp);
            }
            return listaPlanesDtoResp;
        }
    }



}
