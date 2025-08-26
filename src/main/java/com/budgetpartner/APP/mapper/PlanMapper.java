package com.budgetpartner.APP.mapper;

import com.budgetpartner.APP.dto.plan.PlanDtoPostRequest;
import com.budgetpartner.APP.dto.plan.PlanDtoUpdateRequest;
import com.budgetpartner.APP.entity.Organizacion;
import com.budgetpartner.APP.entity.Plan;
import com.budgetpartner.APP.dto.plan.PlanDtoResponse;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PlanMapper {

    /**
     * Convierte una entidad Plan en su DTO de respuesta.
     */
    public static PlanDtoResponse toDtoResponse(Plan plan) {
        if (plan == null) return null;

        return new PlanDtoResponse(
                plan.getId(),
                OrganizacionMapper.toDtoResponse(plan.getOrganizacion()),
                plan.getNombre(),
                plan.getDescripcion(),
                plan.getFechaInicio(),
                plan.getFechaFin(),
                plan.getModoPlan(),
                plan.getLatitud(),
                plan.getLongitud()
        );
    }

    /**
     * Convierte un DTO de creación a una entidad Plan.
     */
    public static Plan toEntity(PlanDtoPostRequest dto, Organizacion organizacion) {
        if (dto == null) return null;

        return new Plan(
                organizacion,
                dto.getNombre(),
                dto.getDescripcion(),
                dto.getFechaInicio(),
                dto.getFechaFin(),
                dto.getModoPlan(),
                dto.getLatitud(),
                dto.getLongitud()
        );
    }

    /**
     * Actualiza una entidad Plan existente con los valores del DTO de actualización.
     */
    public static void updateEntityFromDtoRes(PlanDtoUpdateRequest dto, Plan plan) {
        if (dto == null || plan == null) return;

        if (dto.getNombre() != null) plan.setNombre(dto.getNombre());
        if (dto.getDescripcion() != null) plan.setDescripcion(dto.getDescripcion());
        if (dto.getFechaInicio() != null) plan.setFechaInicio(dto.getFechaInicio());
        if (dto.getFechaFin() != null) plan.setFechaFin(dto.getFechaFin());
        if (dto.getLatitud() != null) plan.setLatitud(dto.getLatitud());
        if (dto.getLongitud() != null) plan.setLongitud(dto.getLongitud());
    }

    /**
     * Convierte una lista de entidades Plan a una lista de DTOs de respuesta.
     */
    public static List<PlanDtoResponse> toDtoResponseListPlan(List<Plan> planes) {
        if (planes == null || planes.isEmpty()) return Collections.emptyList();

        List<PlanDtoResponse> lista = new ArrayList<>();
        for (Plan plan : planes) {
            lista.add(toDtoResponse(plan));
        }
        return lista;
    }
}
