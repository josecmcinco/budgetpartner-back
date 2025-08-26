package com.budgetpartner.APP.mapper;

import com.budgetpartner.APP.dto.gasto.GastoDtoPostRequest;
import com.budgetpartner.APP.dto.gasto.GastoDtoResponse;
import com.budgetpartner.APP.dto.gasto.GastoDtoUpdateRequest;
import com.budgetpartner.APP.entity.Gasto;
import com.budgetpartner.APP.entity.Miembro;
import com.budgetpartner.APP.entity.Plan;
import com.budgetpartner.APP.entity.Tarea;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GastoMapper {

    /**
     * Convierte una entidad Gasto a su DTO de respuesta.
     */
    public static GastoDtoResponse toDtoResponse(Gasto gasto) {
        if (gasto == null) return null;

        Long tareaId = null;
        if(gasto.getTarea() != null){
            tareaId = gasto.getTarea().getId();
        }

        return new GastoDtoResponse(
                gasto.id,
                tareaId,
                gasto.getPlan().getId(),
                gasto.getCantidad(),
                gasto.getNombre(),
                gasto.getDescripcion(),
                MiembroMapper.toDtoResponse(gasto.getPagador()),
                MiembroMapper.toDtoResponseListMiembro(gasto.getMiembrosEndeudados()),
                gasto.getMoneda(),
                gasto.getCreadoEn(),
                gasto.getActualizadoEn()
        );
    }

    /**
     * Convierte un DTO de creación a una entidad Gasto.
     */
    public static Gasto toEntity(GastoDtoPostRequest dto, Tarea tarea, Plan plan, Miembro pagador) {
        if (dto == null) return null;

        return new Gasto(
                tarea,
                plan,
                dto.getCantidad(),
                dto.getNombre(),
                pagador,
                dto.getDescripcion(),
                dto.getMoneda()
        );

    }


    /**
     * Actualiza una entidad Gasto existente con los campos del DTO de actualización.
     * No se permite modificar ciertos campos como: tarea, plan, pagador desde el DT
     */
    public static void updateEntityFromDtoRes(GastoDtoUpdateRequest dto, Gasto gasto) {
        if (dto == null || gasto == null) return;

        if (dto.getCantidad() != 0) gasto.setCantidad(dto.getCantidad());
        if (dto.getNombre() != null) gasto.setNombre(dto.getNombre());
        if (dto.getDescripcion() != null) gasto.setDescripcion(dto.getDescripcion());

    }


    /**
     * Convierte una lista de entidades Gasto a una lista de DTOs de respuesta.
     */
    public static List<GastoDtoResponse> toDtoResponseListGasto(List<Gasto> gastos) {
        List<GastoDtoResponse> listaGastosDtoResp = new ArrayList<>();
        if (gastos == null || gastos.isEmpty()) {
            return Collections.emptyList();
        } else {
            for (Gasto gasto : gastos) {
                GastoDtoResponse gastoDtoResp = GastoMapper.toDtoResponse(gasto);
                listaGastosDtoResp.add(gastoDtoResp);
            }
            return listaGastosDtoResp;
        }
    }


}
