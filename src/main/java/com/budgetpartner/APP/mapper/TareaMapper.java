package com.budgetpartner.APP.mapper;

import com.budgetpartner.APP.dto.request.TareaDtoRequest;
import com.budgetpartner.APP.entity.Tarea;
import com.budgetpartner.APP.dto.response.TareaDtoResponse;
import com.budgetpartner.APP.service.OrganizacionService;
import com.budgetpartner.APP.service.PlanService;
import com.budgetpartner.APP.service.TareaService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TareaMapper {
    @Autowired
    private static PlanService planService;

    // Convierte Tarea en TareaDtoResponse
    public static TareaDtoResponse toDtoResponse(Tarea tarea) {
        if (tarea == null) return null;

        return new TareaDtoResponse(
                tarea.getId(),
                tarea.getPlan(),
                tarea.getTitulo(),
                tarea.getDescripcion(),
                tarea.getFechaFin(),
                tarea.getEstado(),
                tarea.getCosteEstimado(),
                tarea.getMoneda()
        );
    }

    // Convierte TareaDtoRequest en Tarea
    public static Tarea toEntity(TareaDtoRequest dto) {
        if (dto == null) return null;

        // Lista de miembros requiere lógica externa

        return new Tarea(
                planService.getPlanById(dto.getPlan()),
                dto.getTitulo(),
                dto.getDescripcion(),
                dto.getFechaFin(),
                dto.getCosteEstimado(),
                dto.getMoneda(),
                //TODO Lista de miembros requiere lógica externa
                null
        );
    }

    // Actualiza entidad existente con los valores del DTO
    public static void updateEntityFromDtoRes(TareaDtoRequest dto, Tarea tarea) {
        if (dto == null || tarea == null) return;

        //NO SE PERMITE MODIFICAR DESDE EL DTO:
        //Plan
        if (dto.getTitulo() != null) tarea.setTitulo(dto.getTitulo());
        if (dto.getDescripcion() != null) tarea.setDescripcion(dto.getDescripcion());
        if (dto.getFechaFin() != null) tarea.setFechaFin(dto.getFechaFin());
        if (dto.getEstado() != null) tarea.setEstado(dto.getEstado());
        if (dto.getCosteEstimado() != 0) tarea.setCosteEstimado(dto.getCosteEstimado());
        if (dto.getMoneda() != null) tarea.setMoneda(dto.getMoneda());

    }

    public static List<TareaDtoResponse> toDtoResponseListTarea(List<Tarea> tareas) {
        List<TareaDtoResponse> listaTareasDtoResp = new ArrayList<>();
        if (tareas == null || tareas.isEmpty()) {
            return Collections.emptyList();
        } else {
            for (Tarea tarea : tareas) {
                TareaDtoResponse tareaDtoResp = TareaMapper.toDtoResponse(tarea);
                listaTareasDtoResp.add(tareaDtoResp);
            }
            return listaTareasDtoResp;
        }
    }
}
