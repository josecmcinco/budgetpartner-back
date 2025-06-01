package com.budgetpartner.APP.service;

import com.budgetpartner.APP.dto.plan.PlanDtoPostRequest;
import com.budgetpartner.APP.dto.plan.PlanDtoResponse;
import com.budgetpartner.APP.dto.plan.PlanDtoUpdateRequest;
import com.budgetpartner.APP.entity.Organizacion;
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
    private OrganizacionService organizacionService;

    //ESTRUCTURA GENERAL DE LA LÓGICA DE LOS CONTROLADORES
    //Pasar de DtoRequest a Entity-> Insertar en DB->Pasar de Entity a DtoRequest->Return

    //ENDPOINTS
    public Plan postPlan(PlanDtoPostRequest planDtoReq) {

        Organizacion organizacion= organizacionService.getOrganizacionById(planDtoReq.getOrganizacionId());

        //TODO VALIDAR CAMPOS REPETIDOS (nombre, fechas, organización, etc.)
        Plan plan = PlanMapper.toEntity(planDtoReq, organizacion);
        planRepository.save(plan);
        return plan;
    }

    public PlanDtoResponse getPlanByIdAndTrasnform(Long id) {
        Plan plan = getPlanById(id);
        PlanDtoResponse dto = PlanMapper.toDtoResponse(plan);
        return dto;
    }

    public Plan deletePlanById(Long id) {
        //Obtener plan usando el id pasado en la llamada
        Plan plan = planRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Plan no encontrado con id: " + id));

        planRepository.delete(plan);

        //TODO AJUSTAR DEPENDENCIAS DE BORRADO (tareas, miembros asignados, etc.)
        return plan;
    }

    public Plan patchPlan(PlanDtoUpdateRequest dto) {
        //Obtener plan usando el id pasado en la llamada
        Plan plan = planRepository.findById(dto.getId())
                .orElseThrow(() -> new NotFoundException("Plan no encontrado con id: " + dto.getId()));

        PlanMapper.updateEntityFromDtoRes(dto, plan);
        planRepository.save(plan);
        return plan;
    }

    //OTROS MÉTODOS
    public Plan getPlanById(Long id) {
        //Obtener plan usando el id pasado en la llamada
        Plan plan = planRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Plan no encontrado con id: " + id));

        return plan;
    }
}
