package com.budgetpartner.APP.mapper;

import com.budgetpartner.APP.entity.Gasto;
import com.budgetpartner.APP.dto.GastoDto;

public class GastoMapper {

    // Convierte Gasto → GastoDto
    public static GastoDto toDto(Gasto gasto) {
        if (gasto == null) return null;

        return new GastoDto(
                gasto.id,
                gasto.getTarea(),
                gasto.getPlan(),
                gasto.getCantidad(),
                gasto.getNombre(),
                gasto.getPagador(),
                gasto.getDescripcion()
        );
    }

    // Convierte GastoDto → Gasto (OJO: no incluye fechas)
    public static Gasto toEntity(GastoDto dto) {
        if (dto == null) return null;

        Gasto gasto = new Gasto(
                dto.getTarea(),
                dto.getPlan(),
                dto.getCantidad(),
                dto.getNombre(),
                dto.getPagador(),
                dto.getDescripcion()
        );

        gasto.id = dto.getId(); // Solo si el ID es necesario para actualización

        return gasto;
    }

    // Actualiza campos de un Gasto desde su DTO (no toca fechas)
    public static void updateEntityFromDto(GastoDto dto, Gasto gasto) {
        if (dto == null || gasto == null) return;

        //TODO ERROR POR CAMBIOS RAROS (VER COMENTADOS)
        //if (dto.getTarea() != null) gasto.setTarea(dto.getTarea());
        //if (dto.getPlan() != null) gasto.setPlan(dto.getPlan());
        if (dto.getCantidad() != 0) gasto.setCantidad(dto.getCantidad());
        if (dto.getNombre() != null) gasto.setNombre(dto.getNombre());
       // if (dto.getPagador() != null) gasto.setPagador(dto.getPagador());
        if (dto.getDescripcion() != null) gasto.setDescripcion(dto.getDescripcion());

        // NOTA: actualizadoEn debería ser manejado automáticamente en el service o en la entity
    }
}
