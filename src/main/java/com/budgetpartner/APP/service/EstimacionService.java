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
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EstimacionService {

    @Autowired
    private EstimacionRepository estimacionRepository;
    @Autowired
    private PlanRepository planRepository;
    @Autowired
    private MiembroRepository miembroRepository;
    @Autowired
    private GastoRepository gastoRepository;
    @Autowired
    private TareaRepository tareaRepository;

    public EstimacionDtoResponse postEstimacion(EstimacionDtoPostRequest estimacionDtoReq) {

        Tarea tarea = null;
        if (estimacionDtoReq.getTareaId() != null && estimacionDtoReq.getTipoEstimacion() == TipoEstimacion.ESTIMACION_PLAN ) {
            throw new BadRequestException("Se está tratando de asignar una tarea a una estimación de tipo plan");
        }
        else if (estimacionDtoReq.getTipoEstimacion() == TipoEstimacion.ESTIMACION_TAREA && estimacionDtoReq.getTareaId() == null) {
            throw new BadRequestException("Debe proporcionar una tarea para una estimación de tipo tarea");
        }
        else if (estimacionDtoReq.getTipoEstimacion() == TipoEstimacion.ESTIMACION_TAREA && estimacionDtoReq.getTareaId() != null){
            tarea = tareaRepository.findById(estimacionDtoReq.getTareaId())
                    .orElseThrow(() -> new NotFoundException("Tarea no encontrada con id: " + estimacionDtoReq.getTareaId()));
        }

        Plan plan = planRepository.findById(estimacionDtoReq.getPlanId())
                .orElseThrow(() -> new NotFoundException("Plan no encontrado con id: " + estimacionDtoReq.getPlanId()));

        Miembro creador = miembroRepository.findById(estimacionDtoReq.getCreadorId())
                .orElseThrow(() -> new NotFoundException("Miembro creador no encontrado con id: " + estimacionDtoReq.getCreadorId()));

        Miembro pagador = miembroRepository.findById(estimacionDtoReq.getPagadorId())
                .orElseThrow(() -> new NotFoundException("Miembro pagador no encontrado con id: " + estimacionDtoReq.getPagadorId()));

        Gasto gasto = gastoRepository.findById(estimacionDtoReq.getGastoId())
                .orElseThrow(() -> new NotFoundException("Gasto no encontrado con id: " + estimacionDtoReq.getGastoId()));

        Estimacion estimacion = EstimacionMapper.toEntity(estimacionDtoReq, tarea, plan, creador, pagador, gasto);
        estimacionRepository.save(estimacion);

        return EstimacionMapper.toDtoResponse(estimacion);

    }

    public EstimacionDtoResponse getEstimacionDtoById(Long id) {
        Estimacion estimacion = estimacionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Estimación no encontrada con id: " + id));

        return EstimacionMapper.toDtoResponse(estimacion);
    }


    public void patchEstimacion(EstimacionDtoUpdateRequest estimacionDtoReq, Long id) {
        Estimacion estimacion = estimacionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Estimación no encontrada con id: " + id));
        Miembro pagador = miembroRepository.findById(estimacionDtoReq.getPagadorId())
                .orElseThrow(() -> new NotFoundException("Miembro pagador no encontrado con id: " + estimacionDtoReq.getPagadorId()));

        Gasto gasto = gastoRepository.findById(estimacionDtoReq.getGastoId())
                .orElseThrow(() -> new NotFoundException("Gasto no encontrado con id: " + estimacionDtoReq.getGastoId()));


        EstimacionMapper.updateEntityFromDto(estimacionDtoReq, estimacion, pagador, gasto);
    }

    public void deleteEstimacionById(Long id) {

        Estimacion estimacion = estimacionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Organización no encontrada con id: " + id));

        //Borrado en cascada de estimación
        estimacionRepository.delete(estimacion);

    }

}
