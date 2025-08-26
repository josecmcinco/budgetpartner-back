package com.budgetpartner.APP.service;

import com.budgetpartner.APP.dto.estimacion.EstimacionDtoPostRequest;
import com.budgetpartner.APP.dto.estimacion.EstimacionDtoResponse;
import com.budgetpartner.APP.dto.estimacion.EstimacionDtoUpdateRequest;
import com.budgetpartner.APP.entity.*;
import com.budgetpartner.APP.enums.TipoEstimacion;
import com.budgetpartner.APP.exceptions.BadRequestException;
import com.budgetpartner.APP.exceptions.NotFoundException;
import com.budgetpartner.APP.mapper.EstimacionMapper;
import com.budgetpartner.APP.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Servicio encargado de la gestión de estimaciones:
 * creación, consulta, actualización y eliminación.
 */
@Service
public class EstimacionService {

    private final EstimacionRepository estimacionRepository;
    private final PlanRepository planRepository;
    private final MiembroRepository miembroRepository;
    private final GastoRepository gastoRepository;
    private final TareaRepository tareaRepository;

    /**
     * Servicio encargado de la gestión de estimaciones.
     * <p>
     * Permite crear, consultar, actualizar y eliminar estimaciones
     * asociadas a tareas, planes, miembros y gastos.
     */

    @Autowired
    public EstimacionService(EstimacionRepository estimacionRepository,
                   PlanRepository planRepository,
                   MiembroRepository miembroRepository,
                   GastoRepository gastoRepository,
                   TareaRepository tareaRepository) {
        this.estimacionRepository = estimacionRepository;
        this.planRepository = planRepository;
        this.miembroRepository = miembroRepository;
        this.gastoRepository = gastoRepository;
        this.tareaRepository = tareaRepository;
    }


    /**
     * Crea una nueva estimación.
     *
     * @param estimacionDtoReq DTO con los datos de la estimación a crear
     * @return DTO de la estimación creada, incluyendo el ID
     * @throws BadRequestException si hay inconsistencias en los datos de tipo de estimación
     * @throws NotFoundException si algún recurso asociado (tarea, plan, miembro, gasto) no se encuentra
     */
    public EstimacionDtoResponse postEstimacion(EstimacionDtoPostRequest estimacionDtoReq) {

        Tarea tarea = null;

        // Validación de consistencia entre tipo de estimación y presencia de tarea
        if (estimacionDtoReq.getTareaId() != null && estimacionDtoReq.getTipoEstimacion() == TipoEstimacion.ESTIMACION_PLAN ) {
            throw new BadRequestException("Se está tratando de asignar una tarea a una estimación de tipo plan");
        }
        else if (estimacionDtoReq.getTipoEstimacion() == TipoEstimacion.ESTIMACION_TAREA && estimacionDtoReq.getTareaId() == null) {
            throw new BadRequestException("Debe proporcionar una tarea para una estimación de tipo tarea");
        }
        else if (estimacionDtoReq.getTipoEstimacion() == TipoEstimacion.ESTIMACION_TAREA && estimacionDtoReq.getTareaId() != null){
            // Recuperar tarea si corresponde
            tarea = tareaRepository.findById(estimacionDtoReq.getTareaId())
                    .orElseThrow(() -> new NotFoundException("Tarea no encontrada con id: " + estimacionDtoReq.getTareaId()));
        }

        // Recuperar plan asociado
        Plan plan = planRepository.findById(estimacionDtoReq.getPlanId())
                .orElseThrow(() -> new NotFoundException("Plan no encontrado con id: " + estimacionDtoReq.getPlanId()));

        // Recuperar miembros (creador y pagador)
        Miembro creador = miembroRepository.findById(estimacionDtoReq.getCreadorId())
                .orElseThrow(() -> new NotFoundException("Miembro creador no encontrado con id: " + estimacionDtoReq.getCreadorId()));

        Miembro pagador = miembroRepository.findById(estimacionDtoReq.getPagadorId())
                .orElseThrow(() -> new NotFoundException("Miembro pagador no encontrado con id: " + estimacionDtoReq.getPagadorId()));

        // Recuperar gasto asociado
        Gasto gasto = gastoRepository.findById(estimacionDtoReq.getGastoId())
                .orElseThrow(() -> new NotFoundException("Gasto no encontrado con id: " + estimacionDtoReq.getGastoId()));

        // Mapear DTO a entidad y guardar
        Estimacion estimacion = EstimacionMapper.toEntity(estimacionDtoReq, tarea, plan, creador, pagador, gasto);
        estimacion = estimacionRepository.save(estimacion);

        return EstimacionMapper.toDtoResponse(estimacion);
    }

    /**
     * Recupera una estimación por su ID.
     *
     * @param id ID de la estimación
     * @return DTO de la estimación
     * @throws NotFoundException si no existe la estimación
     */
    public EstimacionDtoResponse getEstimacionDtoById(Long id) {
        Estimacion estimacion = estimacionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Estimación no encontrada con id: " + id));

        return EstimacionMapper.toDtoResponse(estimacion);
    }

    /**
     * Actualiza los datos de una estimación existente.
     *
     * @param estimacionDtoReq DTO con los datos a actualizar
     * @param id               ID de la estimación
     * @throws NotFoundException si la estimación, pagador o gasto no existen
     */
    public void patchEstimacion(EstimacionDtoUpdateRequest estimacionDtoReq, Long id) {
        Estimacion estimacion = estimacionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Estimación no encontrada con id: " + id));
        Miembro pagador = miembroRepository.findById(estimacionDtoReq.getPagadorId())
                .orElseThrow(() -> new NotFoundException("Miembro pagador no encontrado con id: " + estimacionDtoReq.getPagadorId()));

        Gasto gasto = gastoRepository.findById(estimacionDtoReq.getGastoId())
                .orElseThrow(() -> new NotFoundException("Gasto no encontrado con id: " + estimacionDtoReq.getGastoId()));

        // Actualizar entidad desde DTO
        EstimacionMapper.updateEntityFromDto(estimacionDtoReq, estimacion, pagador, gasto);

        //TODO guardar cambios en la DB
    }

    /**
     * Elimina una estimación por su ID.
     *
     * @param id ID de la estimación
     * @throws NotFoundException si la estimación no existe
     */
    public void deleteEstimacionById(Long id) {

        Estimacion estimacion = estimacionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Organización no encontrada con id: " + id));

        // Eliminación en cascada de la estimación
        estimacionRepository.delete(estimacion);
    }

}
