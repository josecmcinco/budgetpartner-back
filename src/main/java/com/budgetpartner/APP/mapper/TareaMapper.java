package com.budgetpartner.APP.mapper;

import com.budgetpartner.APP.dto.tarea.TareaDtoPostRequest;
import com.budgetpartner.APP.dto.tarea.TareaDtoUpdateRequest;
import com.budgetpartner.APP.entity.Miembro;
import com.budgetpartner.APP.entity.Plan;
import com.budgetpartner.APP.entity.Tarea;
import com.budgetpartner.APP.dto.tarea.TareaDtoResponse;
import com.budgetpartner.APP.service.PlanService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TareaMapper {

    // Convierte Tarea en TareaDtoResponse
    public static TareaDtoResponse toDtoResponse(Tarea tarea) {
        if (tarea == null) return null;

        return new TareaDtoResponse(
                tarea.getId(),
                PlanMapper.toDtoResponse(tarea.getPlan()),
                tarea.getTitulo(),
                tarea.getDescripcion(),
                tarea.getFechaFin(),
                tarea.getEstado(),
                tarea.getCosteEstimado(),
                tarea.getMoneda()
        );
    }

    // Convierte TareaDtoPostRequest en Tarea
    public static Tarea toEntity(TareaDtoPostRequest dto, Plan plan) {
        if (dto == null) return null;

        return new Tarea(
                plan,
                dto.getTitulo(),
                dto.getDescripcion(),
                dto.getFechaFin(),
                dto.getCosteEstimado(),
                dto.getMoneda()
        );
    }

    // Actualiza entidad existente con los valores del DTO
    public static void updateEntityFromDtoRes(TareaDtoUpdateRequest dto, Tarea tarea) {
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
