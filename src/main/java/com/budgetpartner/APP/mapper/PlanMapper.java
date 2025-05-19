package com.budgetpartner.APP.mapper;

import com.budgetpartner.APP.dto.request.PlanDtoRequest;
import com.budgetpartner.APP.entity.Plan;
import com.budgetpartner.APP.dto.response.PlanDtoResponse;

public class PlanMapper {

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
                dto.getOrganizacion(),
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


}
