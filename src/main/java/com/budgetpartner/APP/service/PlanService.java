package com.budgetpartner.APP.service;

import com.budgetpartner.APP.dto.request.PlanDtoRequest;
import com.budgetpartner.APP.dto.response.PlanDtoResponse;
import com.budgetpartner.APP.entity.Plan;
import com.budgetpartner.APP.exceptions.NotFoundException;
import com.budgetpartner.APP.mapper.PlanMapper;
import com.budgetpartner.APP.repository.PlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlanService {

    @Autowired
    private PlanRepository planRepository;

    //ESTRUCTURA GENERAL DE LA LÓGICA DE LOS CONTROLADORES
    //Pasar de DtoRequest a Entity-> Insertar en DB->Pasar de Entity a DtoRequest->Return

    public PlanDtoResponse postPlan(PlanDtoRequest planDtoReq) {

        //TODO VALIDAR CAMPOS REPETIDOS (nombre, fechas, organización, etc.)
        Plan plan = PlanMapper.toEntity(planDtoReq);
        planRepository.save(plan);
        return PlanMapper.toDtoResponse(plan);
    }

    public PlanDtoResponse getPlanById(Long id) {
        //Obtener plan usando el id pasado en la llamada
        Plan plan = planRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Plan no encontrado con id: " + id));

        return PlanMapper.toDtoResponse(plan);
    }

    public PlanDtoResponse deletePlanById(Long id) {
        //Obtener plan usando el id pasado en la llamada
        Plan plan = planRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Plan no encontrado con id: " + id));

        planRepository.delete(plan);

        //TODO AJUSTAR DEPENDENCIAS DE BORRADO (tareas, miembros asignados, etc.)
        return PlanMapper.toDtoResponse(plan);
    }

    public PlanDtoResponse actualizarPlan(PlanDtoRequest dto, Long id) {
        //Obtener plan usando el id pasado en la llamada
        Plan plan = planRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Plan no encontrado con id: " + id));

        PlanMapper.updateEntityFromDtoRes(dto, plan);
        planRepository.save(plan);
        return PlanMapper.toDtoResponse(plan);
    }
}
