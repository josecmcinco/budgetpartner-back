package com.budgetpartner.APP.mapper;

import com.budgetpartner.APP.entity.Plan;
import com.budgetpartner.APP.entity.Tarea;
import com.budgetpartner.APP.dto.TareaDto;
import com.budgetpartner.APP.enums.EstadoTarea;
import jakarta.persistence.*;

import java.time.LocalDateTime;

public class TareaMapper {
    // Convierte Tarea → TareaDto
    public static TareaDto toDto(Tarea tarea) {
        if (tarea == null) return null;

        return new TareaDto(
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

    // Convierte TareaDto → Tarea (sin fechas ni lista de miembros)
    public static Tarea toEntity(TareaDto dto) {
        if (dto == null) return null;

        Tarea tarea = new Tarea(
                dto.getPlan(),
                dto.getTitulo(),
                dto.getDescripcion(),
                dto.getFechaFin(),
                dto.getCosteEstimado(),
                dto.getMoneda(),
                null // Lista de miembros requiere lógica externa
        );

        // También puedes setear el ID si es necesario
        //TODO buscar alternativa
        tarea.setEstado(dto.getEstado());

        return tarea;
    }

    // Actualiza entidad existente con los valores del DTO
    public static void updateEntityFromDto(TareaDto dto, Tarea tarea) {
        if (dto == null || tarea == null) return;

        //TODO revisar comentario de abajo
        //if (dto.getPlan() != null) tarea.setPlan(dto.getPlan());
        if (dto.getTitulo() != null) tarea.setTitulo(dto.getTitulo());
        if (dto.getDescripcion() != null) tarea.setDescripcion(dto.getDescripcion());
        if (dto.getFechaFin() != null) tarea.setFechaFin(dto.getFechaFin());
        if (dto.getEstado() != null) tarea.setEstado(dto.getEstado());
        if (dto.getCosteEstimado() != 0) tarea.setCosteEstimado(dto.getCosteEstimado());
        if (dto.getMoneda() != null) tarea.setMoneda(dto.getMoneda());

    }
}
