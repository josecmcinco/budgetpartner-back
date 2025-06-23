package com.budgetpartner.APP.service;

import com.budgetpartner.APP.dto.tarea.TareaDtoPostRequest;
import com.budgetpartner.APP.dto.tarea.TareaDtoResponse;
import com.budgetpartner.APP.dto.tarea.TareaDtoUpdateRequest;
import com.budgetpartner.APP.entity.Plan;
import com.budgetpartner.APP.entity.Tarea;
import com.budgetpartner.APP.exceptions.NotFoundException;
import com.budgetpartner.APP.mapper.TareaMapper;
import com.budgetpartner.APP.repository.PlanRepository;
import com.budgetpartner.APP.repository.TareaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TareaService {

    @Autowired
    private TareaRepository tareaRepository;
    @Autowired
    private PlanRepository planRepository;

    //ESTRUCTURA GENERAL DE LA LÓGICA DE LOS CONTROLADORES
    //Pasar de DtoRequest a Entity-> Insertar en DB->Pasar de Entity a DtoRequest->Return


    //ENDPOINTS

    //Llamada para Endpoint
    //Crea una Entidad usando el DTO recibido por el usuario
    public Tarea postTarea(TareaDtoPostRequest dto) {

        //TODO VALIDAR CAMPOS REPETIDOS (título, descripción, fechas, estado, etc.)
        Plan plan = planRepository.findById(dto.getPlanId())
                .orElseThrow(() -> new NotFoundException("Plan no encontrado con id: " + dto.getPlanId()));

        Tarea tarea = TareaMapper.toEntity(dto, plan);
        tareaRepository.save(tarea);
        return tarea;
    }

    //Llamada para Endpoint
    //Obtiene una Entidad usando el id recibido por el usuario
        /*DEVUELVE AL USUARIO:
        organizacion
            |-
            |-numeroPlanes : number
        numeroTareas : number
        actividadReciente : array de objetos
    */
    public TareaDtoResponse getTareaDtoById(Long id) {
        Tarea tarea = tareaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Plan no encontrado con id: " + id));
        TareaDtoResponse dto = TareaMapper.toDtoResponse(tarea);
        return dto;
    }

    //Llamada para Endpoint
    //Elimina una Entidad usando el id recibido por el usuario
    public Tarea deleteTareaById(Long id) {
        //Obtener tarea usando el id pasado en la llamada
        Tarea tarea = tareaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Tarea no encontrada con id: " + id));

        //Borra tarea en cascada
        tareaRepository.delete(tarea);

        return tarea;
    }

    //Llamada para Endpoint
    //Actualiza una Entidad usando el id recibido por el usuario
    public Tarea patchTarea(TareaDtoUpdateRequest dto, Long id) {
        //Obtener tarea usando el id pasado en la llamada
        Tarea tarea = tareaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Tarea no encontrada con id: " + id));

        TareaMapper.updateEntityFromDtoRes(dto, tarea);
        tareaRepository.save(tarea);
        return tarea;
    }

    //OTROS MÉTODOS

}
