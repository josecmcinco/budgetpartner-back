package com.budgetpartner.APP.mapper;

import com.budgetpartner.APP.entity.Gasto;
import com.budgetpartner.APP.dto.response.GastoDtoResponse;

public class GastoMapper {

    // Convierte Gasto en GastoDto
    public static GastoDtoResponse toDto(Gasto gasto) {
        if (gasto == null) return null;

        return new GastoDtoResponse(
                gasto.id,
                gasto.getTarea(),
                gasto.getPlan(),
                gasto.getCantidad(),
                gasto.getNombre(),
                gasto.getPagador(),
                gasto.getDescripcion()
        );
    }

    // Convierte GastoDto en Gasto
    public static Gasto toEntity(GastoDtoResponse dto) {
        if (dto == null) return null;

        return new Gasto(
                dto.getTarea(),
                dto.getPlan(),
                dto.getCantidad(),
                dto.getNombre(),
                dto.getPagador(),
                dto.getDescripcion()
        );

    }

    // Actualiza entidad existente con los valores del DTO
    public static void updateEntityFromDtoRes(GastoDtoResponse dto, Gasto gasto) {
        if (dto == null || gasto == null) return;

        //NO SE PERMITE MODIFICAR DESDE EL DTO:
        //Tarea-Plan-Pagador
        if (dto.getCantidad() != 0) gasto.setCantidad(dto.getCantidad());
        if (dto.getNombre() != null) gasto.setNombre(dto.getNombre());
        if (dto.getDescripcion() != null) gasto.setDescripcion(dto.getDescripcion());

    }
}
