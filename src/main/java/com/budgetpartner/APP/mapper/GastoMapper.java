package com.budgetpartner.APP.mapper;

import com.budgetpartner.APP.dto.gasto.GastoDtoPostRequest;
import com.budgetpartner.APP.dto.gasto.GastoDtoResponse;
import com.budgetpartner.APP.dto.gasto.GastoDtoUpdateRequest;
import com.budgetpartner.APP.dto.plan.PlanDtoResponse;
import com.budgetpartner.APP.dto.tarea.TareaDtoResponse;
import com.budgetpartner.APP.entity.Gasto;
import com.budgetpartner.APP.entity.Miembro;
import com.budgetpartner.APP.entity.Plan;
import com.budgetpartner.APP.entity.Tarea;
import com.budgetpartner.APP.service.MiembroService;
import com.budgetpartner.APP.service.PlanService;
import com.budgetpartner.APP.service.TareaService;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GastoMapper {


    //Entity a GastoDtoRequest
    public static GastoDtoResponse toDtoResponse(Gasto gasto) {
        if (gasto == null) return null;

        return new GastoDtoResponse(
                gasto.id,
                TareaMapper.toDtoResponse(gasto.getTarea()),
                PlanMapper.toDtoResponse(gasto.getPlan()),
                gasto.getCantidad(),
                gasto.getNombre(),
                gasto.getDescripcion()
        );
    }

    //Obtener Entity desde GastoDtoPostRequest
    //No se hacen llamadas al servicio desde aquí
    public static Gasto toEntity(GastoDtoPostRequest dto, Tarea tarea, Plan plan, Miembro pagador) {
        if (dto == null) return null;

        return new Gasto(
                tarea,
                plan,
                dto.getCantidad(),
                dto.getNombre(),
                pagador,
                dto.getDescripcion()
        );

    }

    //Obtener Entity desde GastoDtoUpdateRequest
    //No se hacen llamadas al servicio desde aquí
    public static Gasto toEntity(GastoDtoUpdateRequest dto, Tarea tarea, Plan plan, Miembro pagador) {
        if (dto == null) return null;

        return new Gasto(
                dto.getId(),
                tarea,
                plan,
                dto.getCantidad(),
                dto.getNombre(),
                pagador,
                dto.getDescripcion(),
                LocalDateTime.now(),
                LocalDateTime.now()
        );

    }

    // Actualiza entidad existente con los valores del DTO
    public static void updateEntityFromDtoRes(GastoDtoUpdateRequest dto, Gasto gasto) {
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
