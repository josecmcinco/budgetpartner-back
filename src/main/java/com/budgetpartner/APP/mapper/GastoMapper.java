package com.budgetpartner.APP.mapper;

import com.budgetpartner.APP.dto.request.GastoDtoRequest;
import com.budgetpartner.APP.entity.Gasto;
import com.budgetpartner.APP.dto.response.GastoDtoResponse;
import com.budgetpartner.APP.service.GastoService;
import com.budgetpartner.APP.service.MiembroService;
import com.budgetpartner.APP.service.PlanService;
import com.budgetpartner.APP.service.TareaService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GastoMapper {

    @Autowired
    private static PlanService planService;
    @Autowired
    private static TareaService tareaService;
    @Autowired
    private static MiembroService miembroService;

    // Convierte Gasto en GastoDtoResponse
    public static GastoDtoResponse toDtoResponse(Gasto gasto) {
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

    // Convierte GastoDtoRequest en Gasto
    public static Gasto toEntity(GastoDtoRequest dto) {
        if (dto == null) return null;

        return new Gasto(
                tareaService.getTareaById(dto.getTareaId()),
                planService.getPlanById(dto.getPlanId()),
                dto.getCantidad(),
                dto.getNombre(),
                miembroService.getMiembroById(dto.getPagadorId()),
                dto.getDescripcion()
        );

    }

    // Actualiza entidad existente con los valores del DTO
    public static void updateEntityFromDtoRes(GastoDtoRequest dto, Gasto gasto) {
        if (dto == null || gasto == null) return;

        //NO SE PERMITE MODIFICAR DESDE EL DTO:
        //Tarea-Plan-Pagador
        if (dto.getCantidad() != 0) gasto.setCantidad(dto.getCantidad());
        if (dto.getNombre() != null) gasto.setNombre(dto.getNombre());
        if (dto.getDescripcion() != null) gasto.setDescripcion(dto.getDescripcion());

    }
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
