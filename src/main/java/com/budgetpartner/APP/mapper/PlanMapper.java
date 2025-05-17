package com.budgetpartner.APP.mapper;

import com.budgetpartner.APP.entity.Plan;
import com.budgetpartner.APP.dto.PlanDto;

public class PlanMapper {

    // Convierte Plan → PlanDto
    public static PlanDto toDto(Plan plan) {
        if (plan == null) return null;

        return new PlanDto(
                plan.getId(),
                plan.getOrganizacion(),
                plan.getNombre(),
                plan.getDescripcion(),
                plan.getFechaInicio(),
                plan.getFechaFin()
        );
    }

    // Convierte PlanDto → Plan (sin fechas de creación/modificación)
    public static Plan toEntity(PlanDto dto) {
        if (dto == null) return null;

        Plan plan = new Plan(
                dto.getOrganizacion(),
                dto.getNombre(),
                dto.getDescripcion(),
                dto.getFechaInicio(),
                dto.getFechaFin()
        );

        return plan;
    }

    // Actualiza Plan desde el DTO sin tocar campos como creadoEn o actualizadoEn
    public static void updateEntityFromDto(PlanDto dto, Plan plan) {
        if (dto == null || plan == null) return;

        //TODO ERROR POR CAMBIOS RAROS (VER COMENTADOS)
        //if (dto.getOrganizacion() != null) plan.setOrganizacion(dto.getOrganizacion());
        if (dto.getNombre() != null) plan.setNombre(dto.getNombre());
        if (dto.getDescripcion() != null) plan.setDescripcion(dto.getDescripcion());
        if (dto.getFechaInicio() != null) plan.setFechaInicio(dto.getFechaInicio());
        if (dto.getFechaFin() != null) plan.setFechaFin(dto.getFechaFin());
    }


}
