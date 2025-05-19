package com.budgetpartner.APP.mapper;

import com.budgetpartner.APP.dto.request.TareaDtoRequest;
import com.budgetpartner.APP.entity.Tarea;
import com.budgetpartner.APP.dto.response.TareaDtoResponse;

public class TareaMapper {

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
                dto.getPlan(),
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
}
