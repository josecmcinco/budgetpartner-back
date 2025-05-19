package com.budgetpartner.APP.service;

import com.budgetpartner.APP.dto.request.GastoDtoRequest;
import com.budgetpartner.APP.dto.request.TareaDtoRequest;
import com.budgetpartner.APP.dto.response.GastoDtoResponse;
import com.budgetpartner.APP.dto.response.TareaDtoResponse;
import com.budgetpartner.APP.entity.Tarea;
import com.budgetpartner.APP.exceptions.AppExceptions.TareaNotFoundException;
import com.budgetpartner.APP.mapper.TareaMapper;
import com.budgetpartner.APP.repository.GastoRepository;
import com.budgetpartner.APP.repository.TareaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TareaService {

    @Autowired
    private TareaRepository tareaRepository;

    //ESTRUCTURA GENERAL DE LA LÓGICA DE LOS CONTROLADORES
    //Pasar de DtoRequest a Entity-> Insertar en DB->Pasar de Entity a DtoRequest->Return

    public TareaDtoResponse postTarea(TareaDtoRequest tareaDtoReq) {

        //TODO VALIDAR CAMPOS REPETIDOS (título, descripción, fechas, estado, etc.)
        Tarea tarea = TareaMapper.toEntity(tareaDtoReq);
        tareaRepository.save(tarea);
        return TareaMapper.toDtoResponse(tarea);
    }

    public TareaDtoResponse getTareaById(Long id) {
        //Obtener tarea usando el id pasado en la llamada
        Tarea tarea = tareaRepository.findById(id)
                .orElseThrow(() -> new TareaNotFoundException("Tarea no encontrada con id: " + id));

        return TareaMapper.toDtoResponse(tarea);
    }

    public TareaDtoResponse deleteTareaById(Long id) {
        //Obtener tarea usando el id pasado en la llamada
        Tarea tarea = tareaRepository.findById(id)
                .orElseThrow(() -> new TareaNotFoundException("Tarea no encontrada con id: " + id));

        tareaRepository.delete(tarea);

        //TODO AJUSTAR DEPENDENCIAS DE BORRADO (comentarios, asignaciones, etc.)
        return TareaMapper.toDtoResponse(tarea);
    }

    public TareaDtoResponse actualizarTarea(TareaDtoRequest dto, Long id) {
        //Obtener tarea usando el id pasado en la llamada
        Tarea tarea = tareaRepository.findById(id)
                .orElseThrow(() -> new TareaNotFoundException("Tarea no encontrada con id: " + id));

        TareaMapper.updateEntityFromDtoRes(dto, tarea);
        tareaRepository.save(tarea);
        return TareaMapper.toDtoResponse(tarea);
    }
}
