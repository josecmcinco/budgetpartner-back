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

/**
 * Servicio encargado de la gestión de tareas.
 * Permite crear, consultar, actualizar, eliminar tareas y asignar miembros.
 */
@Service
public class TareaService {

    private final TareaRepository tareaRepository;
    private final PlanRepository planRepository;
    private final MiembroRepository miembroRepository;
    private final RepartoTareaRepository repartoTareaRepository;

    @Autowired
    public TareaService(TareaRepository tareaRepository,
                        PlanRepository planRepository,
                        MiembroRepository miembroRepository,
                        RepartoTareaRepository repartoTareaRepository) {
        this.tareaRepository = tareaRepository;
        this.planRepository = planRepository;
        this.miembroRepository = miembroRepository;
        this.repartoTareaRepository = repartoTareaRepository;
    }

    /**
     * Crea una nueva tarea y asigna miembros según la lista proporcionada.
     *
     * @param dto DTO con los datos de la tarea a crear
     * @return entidad Tarea creada
     * @throws NotFoundException si el plan no existe
     */
    @Transactional //Interacción con un many to many
    public Tarea postTarea(TareaDtoPostRequest dto) {

        //TODO VALIDAR CAMPOS REPETIDOS (título, descripción, fechas, estado, etc.)
        Plan plan = planRepository.findById(dto.getPlanId())
                .orElseThrow(() -> new NotFoundException("Plan no encontrado con id: " + dto.getPlanId()));

        Tarea tarea = TareaMapper.toEntity(dto, plan);
        tarea = tareaRepository.save(tarea);

        // Crear asignaciones de miembros para la tarea
        List<Long> idAtareadosList = dto.getListaAtareados();
        postRepartoTarea(idAtareadosList, tarea);

        return tarea;
    }

    /**
     * Obtiene una tarea por su ID transformada a DTO.
     *
     * @param id ID de la tarea
     * @return DTO de la tarea
     * @throws NotFoundException si la tarea no existe
     */
    public TareaDtoResponse getTareaDtoById(Long id) {
        Tarea tarea = tareaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Plan no encontrado con id: " + id));

        return TareaMapper.toDtoResponse(tarea);
    }

    /**
     * Elimina una tarea y sus asignaciones a miembros.
     *
     * @param id ID de la tarea
     * @throws NotFoundException si la tarea no existe
     */
    @Transactional //Interacción con un many to many
    public void deleteTareaById(Long id) {
        Tarea tarea = tareaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Tarea no encontrada con id: " + id));

        // Eliminar asignaciones de miembros
        repartoTareaRepository.eliminarRepartoTareaPorTareaId(id);

        // Eliminar tarea en cascada
        tareaRepository.delete(tarea);

    }

    /**
     * Actualiza una tarea existente y sus asignaciones a miembros.
     *
     * @param dto DTO con los datos a actualizar
     * @param id  ID de la tarea
     * @throws NotFoundException si la tarea no existe
     */
    @Transactional
    public void patchTarea(TareaDtoUpdateRequest dto, Long id) {
        Tarea tarea = tareaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Tarea no encontrada con id: " + id));

        TareaMapper.updateEntityFromDtoRes(dto, tarea);
        tareaRepository.save(tarea);

        if (dto.getListaAtareados() != null) {
            // Eliminar asignaciones previas
            repartoTareaRepository.eliminarRepartoTareaPorTareaId(id);

            // Añadir nuevas asignaciones
            postRepartoTarea(dto.getListaAtareados(), tarea);
        }

    }

    /**
     * Crea las asignaciones de miembros para una tarea.
     *
     * @param idAtareadosList lista de IDs de miembros a asignar
     * @param tarea           tarea a la que se asignan los miembros
     * @throws NotFoundException si algún miembro no existe
     */
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
