package com.budgetpartner.APP.service;

import com.budgetpartner.APP.dto.tarea.TareaDtoPostRequest;
import com.budgetpartner.APP.dto.tarea.TareaDtoResponse;
import com.budgetpartner.APP.dto.tarea.TareaDtoUpdateRequest;
import com.budgetpartner.APP.entity.*;
import com.budgetpartner.APP.exceptions.NotFoundException;
import com.budgetpartner.APP.mapper.TareaMapper;
import com.budgetpartner.APP.repository.MiembroRepository;
import com.budgetpartner.APP.repository.PlanRepository;
import com.budgetpartner.APP.repository.RepartoTareaRepository;
import com.budgetpartner.APP.repository.TareaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TareaService {

    @Autowired
    private TareaRepository tareaRepository;
    @Autowired
    private PlanRepository planRepository;
    @Autowired
    private MiembroRepository miembroRepository;
    @Autowired
    private RepartoTareaRepository repartoTareaRepository;

    //ESTRUCTURA GENERAL DE LA LÓGICA DE LOS CONTROLADORES
    //Pasar de DtoRequest a Entity-> Insertar en DB->Pasar de Entity a DtoRequest->Return


    //ENDPOINTS

    //Llamada para Endpoint
    //Crea una Entidad usando el DTO recibido por el usuario
    @Transactional //Interacción con un many to many
    public Tarea postTarea(TareaDtoPostRequest dto) {


        //TODO VALIDAR CAMPOS REPETIDOS (título, descripción, fechas, estado, etc.)
        Plan plan = planRepository.findById(dto.getPlanId())
                .orElseThrow(() -> new NotFoundException("Plan no encontrado con id: " + dto.getPlanId()));

        Tarea tarea = TareaMapper.toEntity(dto, plan);
        tareaRepository.save(tarea);

        //Crea las deudas de cada miembro en base al gasto
        List<Long> idAtareadosList = dto.getListaAtareados();
        postRepartoTarea(idAtareadosList, tarea);


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
    @Transactional //Interacción con un many to many
    public Tarea deleteTareaById(Long id) {
        //Obtener tarea usando el id pasado en la llamada
        Tarea tarea = tareaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Tarea no encontrada con id: " + id));

        //Elimina primero la relación muchos a muchos con Miembro
        repartoTareaRepository.eliminarRepartoTareaPorTareaId(id);

        //Borra tarea en cascada
        tareaRepository.delete(tarea);

        return tarea;
    }

    //Llamada para Endpoint
    //Actualiza una Entidad usando el id recibido por el usuario
    @Transactional //Interacción con un many to many
    public Tarea patchTarea(TareaDtoUpdateRequest dto, Long id) {
        //Obtener tarea usando el id pasado en la llamada
        Tarea tarea = tareaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Tarea no encontrada con id: " + id));

        TareaMapper.updateEntityFromDtoRes(dto, tarea);
        tareaRepository.save(tarea);

        if(dto.getListaAtareados() != null){
            //Eliminar las reparticiones previas
            repartoTareaRepository.eliminarRepartoTareaPorTareaId(id);

            //Añadir reparticiones nuevas
            List<Long> idAtareadosList = dto.getListaAtareados();
            postRepartoTarea(idAtareadosList, tarea);
        }

        return tarea;
    }

    //OTROS MÉTODOS
    //Crea las deudas de cada miembro en base al gasto
    public void postRepartoTarea(List<Long> idAtareadosList, Tarea tarea){

        //Crear un elemento repartoDeuda por cada endeudado y meterlo en la DB
        for (Long idAtareado: idAtareadosList){
            Miembro miembro = miembroRepository.findById(idAtareado)
                    .orElseThrow(() -> new NotFoundException("Miembro no encontrado con id: " + idAtareado));


            //Creación del elemento intermedio al muchos a muchos
            RepartoTarea reparto = new RepartoTarea();
            reparto.setTarea(tarea);
            reparto.setMiembro(miembro);

            //Creación del id del elemento intermedio al muchos a muchos
            reparto.setId(new RepartoTareaId(tarea.getId(), miembro.getId()));

            repartoTareaRepository.save(reparto);
        }

    }
}
