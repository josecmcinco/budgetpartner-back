package com.budgetpartner.APP.service;

import com.budgetpartner.APP.dto.gasto.GastoDtoPostRequest;
import com.budgetpartner.APP.dto.gasto.GastoDtoResponse;
import com.budgetpartner.APP.dto.gasto.GastoDtoUpdateRequest;
import com.budgetpartner.APP.entity.Gasto;
import com.budgetpartner.APP.entity.Miembro;
import com.budgetpartner.APP.entity.Plan;
import com.budgetpartner.APP.entity.Tarea;
import com.budgetpartner.APP.exceptions.NotFoundException;
import com.budgetpartner.APP.mapper.GastoMapper;
import com.budgetpartner.APP.repository.GastoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GastoService {

    @Autowired
    private GastoRepository gastoRepository;
    private TareaService tareaService;
    private PlanService planService;
    private MiembroService miembroService;

    //ESTRUCTURA GENERAL DE LA LÓGICA DE LOS CONTROLADORES
    //Pasar de DtoRequest a Entity-> Insertar en DB->Pasar de Entity a DtoRequest->Return

    //ENDPOINTS
    public Gasto postGasto(GastoDtoPostRequest gastoDtoReq) {
        //TODO VALIDAR CAMPOS REPETIDOS (DESCRIPCIÓN, MONTO, FECHA, ETC.)

        Tarea tarea = tareaService.getTareaById(gastoDtoReq.getTareaId());
        Plan plan = planService.getPlanById(gastoDtoReq.getPlanId());
        Miembro pagador = miembroService.getMiembroById(gastoDtoReq.getPagadorId());


        Gasto gasto = GastoMapper.toEntity(gastoDtoReq, tarea, plan, pagador);
        gastoRepository.save(gasto);
        return gasto;
    }

    public GastoDtoResponse getGastoByIdAndTransform(Long id) {
        //Obtener gasto usando el id pasado en la llamada
        Gasto gasto = gastoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Gasto no encontrado con id: " + id));

        GastoDtoResponse gastoDtoResp = GastoMapper.toDtoResponse(gasto);
        return gastoDtoResp;

    }

    public Gasto deleteGastoById(Long id) {
        //Obtener gasto usando el id pasado en la llamada
        Gasto gasto = gastoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Gasto no encontrado con id: " + id));

        gastoRepository.delete(gasto);

        //TODO AJUSTAR DEPENDENCIAS DE BORRADO SI EXISTEN (referencias desde otras entidades)
        return gasto;
    }

    public Gasto patchGasto(GastoDtoUpdateRequest dto, Long id) {
        //Obtener gasto usando el id pasado en la llamada
        Gasto gasto = gastoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Gasto no encontrado con id: " + id));

        GastoMapper.updateEntityFromDtoRes(dto, gasto);
        gastoRepository.save(gasto);
        return gasto;
    }


    //OTROS MÉTODOS
    public Gasto getGastoById(Long id) {
        //Obtener gasto usando el id pasado en la llamada
        Gasto gasto = gastoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Gasto no encontrado con id: " + id));
        return gasto;

    }

}
