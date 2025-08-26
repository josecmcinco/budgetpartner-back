package com.budgetpartner.APP.mapper;

import com.budgetpartner.APP.dto.tarea.TareaDtoPostRequest;
import com.budgetpartner.APP.dto.tarea.TareaDtoUpdateRequest;
import com.budgetpartner.APP.entity.Plan;
import com.budgetpartner.APP.entity.Tarea;
import com.budgetpartner.APP.dto.tarea.TareaDtoResponse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TareaMapper {

    /**
     * Convierte una entidad Tarea en su DTO de respuesta.
     */
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

    /**
     * Convierte un DTO de creación en una entidad Tarea.
     */
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

    /**
     * Actualiza una entidad Tarea existente con los valores del DTO de actualización.
     */
    public static void updateEntityFromDtoRes(TareaDtoUpdateRequest dto, Tarea tarea) {
        if (dto == null || tarea == null) return;

        if (dto.getTitulo() != null) tarea.setTitulo(dto.getTitulo());
        if (dto.getDescripcion() != null) tarea.setDescripcion(dto.getDescripcion());
        if (dto.getFechaFin() != null) tarea.setFechaFin(dto.getFechaFin());
        if (dto.getEstado() != null) tarea.setEstado(dto.getEstado());
        if (dto.getCosteEstimado() != 0) tarea.setCosteEstimado(dto.getCosteEstimado());
        if (dto.getMoneda() != null) tarea.setMoneda(dto.getMoneda());
    }

    /**
     * Convierte una lista de entidades Tarea en una lista de DTOs de respuesta.
     */
    public static List<TareaDtoResponse> toDtoResponseListTarea(List<Tarea> tareas) {
        if (tareas == null || tareas.isEmpty()) return Collections.emptyList();

        List<TareaDtoResponse> lista = new ArrayList<>();
        for (Tarea tarea : tareas) {
            lista.add(toDtoResponse(tarea));
        }
        return lista;
    }
}
