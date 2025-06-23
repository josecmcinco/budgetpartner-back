package com.budgetpartner.APP.service;

import com.budgetpartner.APP.dto.estimacion.EstimacionDtoResponse;
import com.budgetpartner.APP.dto.gasto.GastoDtoResponse;
import com.budgetpartner.APP.dto.miembro.MiembroDtoResponse;
import com.budgetpartner.APP.dto.organizacion.OrganizacionDtoResponse;
import com.budgetpartner.APP.dto.plan.PlanDtoPostRequest;
import com.budgetpartner.APP.dto.plan.PlanDtoResponse;
import com.budgetpartner.APP.dto.plan.PlanDtoUpdateRequest;
import com.budgetpartner.APP.dto.tarea.TareaDtoResponse;
import com.budgetpartner.APP.entity.*;
import com.budgetpartner.APP.exceptions.NotFoundException;
import com.budgetpartner.APP.mapper.*;
import com.budgetpartner.APP.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlanService {

    @Autowired
    private PlanRepository planRepository;
    @Autowired
    private OrganizacionRepository organizacionRepository;
    @Autowired
    private MiembroRepository miembroRepository;
    @Autowired
    private GastoRepository gastoRepository;
    @Autowired
    private TareaRepository tareaRepository;
    @Autowired
    private EstimacionRepository estimacionRepository;

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

        //Obtener plan
        Plan plan = planRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Plan no encontrado con id: " + id));

        //Transformar plan en dto
        PlanDtoResponse planDto = PlanMapper.toDtoResponse(plan);

        //La organizacion ya la tiene el Plan


        //Enviar los miembros de la organizacion como dto en planDto
        List< Miembro > miembros = miembroRepository.obtenerMiembrosPorOrganizacionId(planDto.getOrganizacionDtoResponse().getId());
        List<MiembroDtoResponse> ListMiembroDto = MiembroMapper.toDtoResponseListMiembro(miembros);
        planDto.getOrganizacionDtoResponse().setMiembros(ListMiembroDto);

        //Enviar los gastos como dto en planDto
        List<Gasto> gastos = gastoRepository.obtenerGastosPorPlanId(planDto.getId());
        List<GastoDtoResponse> ListGastoDto = GastoMapper.toDtoResponseListGasto(gastos);
        planDto.setGastos(ListGastoDto);

        //Enviar las tareas como dto en planDto
        List<Tarea> tareas = tareaRepository.obtenerTareasPorPlanId(planDto.getId());
        List<TareaDtoResponse> ListTareaDto = TareaMapper.toDtoResponseListTarea(tareas);
        planDto.setTareas(ListTareaDto);
/*
        List<Estimacion> estimaciones = estimacionRepository.obtenerEstimacionesPorPlanId(planDto.getId());
        List<EstimacionDtoResponse> ListEstimacionDto = EstimacionMapper.toDtoResponseListEstimacion(estimaciones);
        planDto.setEstimaciones(ListEstimacionDto);
*/

        return planDto;
    }

    //Llamada para Endpoint
    //Elimina una Entidad usando el id recibido por el usuario
    public Plan deletePlanById(Long id) {
        //Obtener plan usando el id pasado en la llamada
        Plan plan = planRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Plan no encontrado con id: " + id));

        //Borra el plan en cascada
        planRepository.delete(plan);

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

}
