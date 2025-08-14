package com.budgetpartner.APP.mapper;

import com.budgetpartner.APP.dto.estimacion.EstimacionDtoPostRequest;
import com.budgetpartner.APP.dto.estimacion.EstimacionDtoResponse;
import com.budgetpartner.APP.dto.estimacion.EstimacionDtoUpdateRequest;
import com.budgetpartner.APP.entity.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EstimacionMapper {

    // Entity a DTO de respuesta
    public static EstimacionDtoResponse toDtoResponse(Estimacion estimacion) {
        if (estimacion == null) return null;

        Long tareaId = null;
        if(estimacion.getTarea() != null){
            tareaId = estimacion.getPlan().getId();
        }

        return new EstimacionDtoResponse(
                estimacion.getId(),
                tareaId,
                estimacion.getPlan().getId(),
                estimacion.getCreador().getId(),
                estimacion.getCantidad(),
                estimacion.getTipoEstimacion(),
                estimacion.getTipoMoneda(),
                estimacion.getDescripcion(),
                estimacion.getPagador() != null ? estimacion.getPagador().getId() : null,
                estimacion.getGasto() != null ? estimacion.getGasto().getId() : null
        );
    }

    // DTO de creación a Entity
    public static Estimacion toEntity(EstimacionDtoPostRequest dto, Tarea tarea, Plan plan, Miembro creador, Miembro pagador, Gasto gasto) {
        if (dto == null) return null;

        return new Estimacion(
                plan,
                tarea,
                creador,
                dto.getCantidad(),
                dto.getTipoEstimacion(),
                dto.getTipoMoneda(),
                dto.getDescripcion(),
                pagador,
                gasto
        );
    }

    // Actualiza una entidad existente con campos del DTO
    public static void updateEntityFromDto(EstimacionDtoUpdateRequest dto, Estimacion estimacion, Miembro pagador, Gasto gasto) {
        if (dto == null || estimacion == null) return;

        if (dto.getCantidad() != 0) estimacion.setCantidad(dto.getCantidad());
        if (dto.getTipoMoneda() != null) estimacion.setTipoMoneda(dto.getTipoMoneda());
        if (dto.getDescripcion() != null) estimacion.setDescripcion(dto.getDescripcion());
        if (pagador != null) estimacion.setPagador(pagador);
        if (gasto != null) estimacion.setGasto(gasto);

        estimacion.setActualizadoEn(LocalDateTime.now());
    }

    public static List<EstimacionDtoResponse> toDtoResponseListEstimacion(List<Estimacion> estimaciones) {
        if (estimaciones == null || estimaciones.isEmpty()) {
            return Collections.emptyList();
        }

        List<EstimacionDtoResponse> lista = new ArrayList<>();
        for (Estimacion estimacion : estimaciones) {
            lista.add(toDtoResponse(estimacion));
        }
        return lista;
    }
}
