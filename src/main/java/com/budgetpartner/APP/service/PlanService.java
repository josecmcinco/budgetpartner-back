package com.budgetpartner.APP.service;

import com.budgetpartner.APP.dto.plan.PlanDtoPostRequest;
import com.budgetpartner.APP.dto.plan.PlanDtoResponse;
import com.budgetpartner.APP.dto.plan.PlanDtoUpdateRequest;
import com.budgetpartner.APP.entity.Organizacion;
import com.budgetpartner.APP.entity.Plan;
import com.budgetpartner.APP.exceptions.NotFoundException;
import com.budgetpartner.APP.mapper.PlanMapper;
import com.budgetpartner.APP.repository.OrganizacionRepository;
import com.budgetpartner.APP.repository.PlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlanService {

    @Autowired
    private PlanRepository planRepository;
    @Autowired
    private OrganizacionRepository organizacionRepository;

    //ESTRUCTURA GENERAL DE LA LÓGICA DE LOS CONTROLADORES
    //Pasar de DtoRequest a Entity-> Insertar en DB->Pasar de Entity a DtoRequest->Return

    //ENDPOINTS

    //Llamada para Endpoint
    //Crea una Entidad usando el DTO recibido por el usuario
    public Plan postPlan(PlanDtoPostRequest planDtoReq) {

        Organizacion organizacion= organizacionRepository.findById(planDtoReq.getOrganizacionId())
                .orElseThrow(() -> new NotFoundException("Organización no encontrada con id: " + planDtoReq.getOrganizacionId()));

        //TODO VALIDAR CAMPOS REPETIDOS (nombre, fechas, organización, etc.)
        Plan plan = PlanMapper.toEntity(planDtoReq, organizacion);
        planRepository.save(plan);
        return plan;
    }

    //Llamada para Endpoint
    //Obtiene una Entidad usando el id recibido por el usuario
        /*DEVUELVE AL USUARIO:

    */
    public PlanDtoResponse getPlanByIdAndTrasnform(Long id) {
        Plan plan = encontarPlanPorId(id);
        PlanDtoResponse dto = PlanMapper.toDtoResponse(plan);
        return dto;
    }

    //Llamada para Endpoint
    //Elimina una Entidad usando el id recibido por el usuario
    public Plan deletePlanById(Long id) {
        //Obtener plan usando el id pasado en la llamada
        Plan plan = planRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Plan no encontrado con id: " + id));

        planRepository.delete(plan);

        //TODO AJUSTAR DEPENDENCIAS DE BORRADO (tareas, miembros asignados, etc.)
        return plan;
    }

    //Llamada para Endpoint
    //Actualiza una Entidad usando el id recibido por el usuario
    public Plan patchPlan(PlanDtoUpdateRequest dto, Long id) {
        //Obtener plan usando el id pasado en la llamada
        Plan plan = planRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Plan no encontrado con id: " + id));

        PlanMapper.updateEntityFromDtoRes(dto, plan);
        planRepository.save(plan);
        return plan;
    }

    //OTROS MÉTODOS
    public Plan encontarPlanPorId(Long id) {
        //Obtener plan usando el id pasado en la llamada
        Plan plan = planRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Plan no encontrado con id: " + id));

        return plan;
    }

    public Integer contarPlanesPorUsuarioId(Long id){
        return planRepository.contarPlanesPorUsuarioId(id);
    }
}
