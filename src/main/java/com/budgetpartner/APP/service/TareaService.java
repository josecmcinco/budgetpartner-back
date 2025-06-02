package com.budgetpartner.APP.service;

import com.budgetpartner.APP.controller.PlanController;
import com.budgetpartner.APP.dto.tarea.TareaDtoPostRequest;
import com.budgetpartner.APP.dto.tarea.TareaDtoResponse;
import com.budgetpartner.APP.dto.tarea.TareaDtoUpdateRequest;
import com.budgetpartner.APP.entity.Plan;
import com.budgetpartner.APP.entity.Tarea;
import com.budgetpartner.APP.exceptions.NotFoundException;
import com.budgetpartner.APP.mapper.TareaMapper;
import com.budgetpartner.APP.repository.TareaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TareaService {

    @Autowired
    private TareaRepository tareaRepository;
    @Autowired
    private PlanService planService;

    //ESTRUCTURA GENERAL DE LA LÓGICA DE LOS CONTROLADORES
    //Pasar de DtoRequest a Entity-> Insertar en DB->Pasar de Entity a DtoRequest->Return


    //ENDPOINTS

    public Tarea postTarea(TareaDtoPostRequest dto) {

        //TODO VALIDAR CAMPOS REPETIDOS (título, descripción, fechas, estado, etc.)
        Plan plan = planService.getPlanById(dto.getPlanId());

        Tarea tarea = TareaMapper.toEntity(dto, plan);
        tareaRepository.save(tarea);
        return tarea;
    }

    public TareaDtoResponse getTareaByIdAndTransform(Long id) {
        Tarea tarea = getTareaById(id);
        TareaDtoResponse dto = TareaMapper.toDtoResponse(tarea);
        return dto;
    }

    public Tarea deleteTareaById(Long id) {
        //Obtener tarea usando el id pasado en la llamada
        Tarea tarea = tareaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Tarea no encontrada con id: " + id));

        tareaRepository.delete(tarea);

        //TODO AJUSTAR DEPENDENCIAS DE BORRADO (comentarios, asignaciones, etc.)
        return tarea;
    }

    public Tarea patchTarea(TareaDtoUpdateRequest dto) {
        //Obtener tarea usando el id pasado en la llamada
        Tarea tarea = tareaRepository.findById(dto.getId())
                .orElseThrow(() -> new NotFoundException("Tarea no encontrada con id: " + dto.getId()));

        TareaMapper.updateEntityFromDtoRes(dto, tarea);
        tareaRepository.save(tarea);
        return tarea;
    }

    //OTROS MÉTODOS
    public Tarea getTareaById(Long id) {
        //Obtener tarea usando el id pasado en la llamada
        Tarea tarea = tareaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Tarea no encontrada con id: " + id));

        return tarea;
    }
}
