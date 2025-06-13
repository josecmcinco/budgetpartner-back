package com.budgetpartner.APP.mapper;

import com.budgetpartner.APP.dto.organizacion.OrganizacionDtoResponse;
import com.budgetpartner.APP.dto.plan.PlanDtoPostRequest;
import com.budgetpartner.APP.dto.plan.PlanDtoUpdateRequest;
import com.budgetpartner.APP.entity.Organizacion;
import com.budgetpartner.APP.entity.Plan;
import com.budgetpartner.APP.dto.plan.PlanDtoResponse;
import com.budgetpartner.APP.service.OrganizacionService;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PlanMapper {

    // Convierte Plan en PlanDtoResponse
    public static PlanDtoResponse toDtoResponse(Plan plan) {
        if (plan == null) return null;

        return new PlanDtoResponse(
                plan.getId(),
                OrganizacionMapper.toDtoResponse(plan.getOrganizacion()),
                plan.getNombre(),
                plan.getDescripcion(),
                plan.getFechaInicio(),
                plan.getFechaFin(),
                plan.getModoPlan()
        );
    }

    //Obtener Entity desde GastoDtoPostRequest
    //No se hacen llamadas al servicio desde aquí
    public static Plan toEntity(PlanDtoPostRequest dto, Organizacion organizacion) {
        if (dto == null) return null;

        return new Plan(
                organizacion,
                dto.getNombre(),
                dto.getDescripcion(),
                dto.getFechaInicio(),
                dto.getFechaFin(),
                dto.getModoPlan()

        );
    }

    //Obtener Entity desde GastoDtoUpdateRequest
    //No se hacen llamadas al servicio desde aquí
    public static Plan toEntity(PlanDtoUpdateRequest dto, Organizacion organizacion) {
        if (dto == null) return null;

        return new Plan(
                dto.getId(),
                organizacion,
                dto.getNombre(),
                dto.getDescripcion(),
                dto.getFechaInicio(),
                dto.getFechaFin(),
                dto.getModoPlan(),
                LocalDateTime.now(), //TODO
                LocalDateTime.now()
        );
    }

    // Actualiza entidad existente con los valores del DTO
    public static void updateEntityFromDtoRes(PlanDtoUpdateRequest dto, Plan plan) {
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
