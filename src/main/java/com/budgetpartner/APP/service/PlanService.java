package com.budgetpartner.APP.service;

import com.budgetpartner.APP.dto.estimacion.EstimacionDtoResponse;
import com.budgetpartner.APP.dto.gasto.GastoDtoResponse;
import com.budgetpartner.APP.dto.miembro.MiembroDtoResponse;
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
import java.math.BigDecimal;
import java.util.List;


/**
 * Servicio encargado de la gestión de planes.
 * Permite crear, consultar, actualizar, eliminar planes y ajustar deudas de los miembros.
 */
@Service
public class PlanService {

    private final PlanRepository planRepository;
    private final OrganizacionRepository organizacionRepository;
    private final MiembroRepository miembroRepository;
    private final GastoRepository gastoRepository;
    private final TareaRepository tareaRepository;
    private final EstimacionRepository estimacionRepository;
    private final RepartoGastoRepository repartoGastoRepository;

    @Autowired
    public PlanService(PlanRepository planRepository,
                       OrganizacionRepository organizacionRepository,
                       MiembroRepository miembroRepository,
                       GastoRepository gastoRepository,
                       TareaRepository tareaRepository,
                       EstimacionRepository estimacionRepository,
                       RepartoGastoRepository repartoGastoRepository) {
        this.planRepository = planRepository;
        this.organizacionRepository = organizacionRepository;
        this.miembroRepository = miembroRepository;
        this.gastoRepository = gastoRepository;
        this.tareaRepository = tareaRepository;
        this.estimacionRepository = estimacionRepository;
        this.repartoGastoRepository = repartoGastoRepository;
    }

    /**
     * Crea un nuevo plan en la organización indicada.
     *
     * @param planDtoReq DTO con los datos del plan a crear
     * @return DTO del plan creado
     * @throws NotFoundException si la organización no existe
     */
    public PlanDtoResponse postPlan(PlanDtoPostRequest planDtoReq) {

        Organizacion organizacion= organizacionRepository.findById(planDtoReq.getOrganizacionId())
                .orElseThrow(() -> new NotFoundException("Organización no encontrada con id: " + planDtoReq.getOrganizacionId()));

        //TODO VALIDAR CAMPOS REPETIDOS (nombre, fechas, organización, etc.)
        //Crear entidad Plan
        Plan plan = PlanMapper.toEntity(planDtoReq, organizacion);
        plan = planRepository.save(plan);

        return PlanMapper.toDtoResponse(plan);
    }

    /**
     * Obtiene un plan por su ID y transforma toda la información relacionada en DTOs.
     *
     * @param id ID del plan
     * @return DTO del plan con miembros, gastos, tareas y estimaciones
     * @throws NotFoundException si el plan no existe
     */
    public PlanDtoResponse getPlanByIdAndTrasnform(Long id) {

        //Obtener plan
        Plan plan = planRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Plan no encontrado con id: " + id));

        //Transformar plan en dto
        PlanDtoResponse planDto = PlanMapper.toDtoResponse(plan);

        // Agregar miembros de la organización
        List< Miembro > miembros = miembroRepository.obtenerMiembrosPorOrganizacionId(planDto.getOrganizacionDtoResponse().getId());
        List<MiembroDtoResponse> ListMiembroDto = MiembroMapper.toDtoResponseListMiembro(miembros);
        planDto.getOrganizacionDtoResponse().setMiembros(ListMiembroDto);

        // Agregar gastos
        List<Gasto> gastos = gastoRepository.obtenerGastosPorPlanId(planDto.getId());
        List<GastoDtoResponse> ListGastoDto = GastoMapper.toDtoResponseListGasto(gastos);
        planDto.setGastos(ListGastoDto);

        // Agregar tareas
        List<Tarea> tareas = tareaRepository.obtenerTareasPorPlanId(planDto.getId());
        List<TareaDtoResponse> ListTareaDto = TareaMapper.toDtoResponseListTarea(tareas);
        planDto.setTareas(ListTareaDto);

        // Agregar estimaciones
        List<Estimacion> estimaciones = estimacionRepository.obtenerEstimacionesPorPlanId(planDto.getId());
        List<EstimacionDtoResponse> ListEstimacionDto = EstimacionMapper.toDtoResponseListEstimacion(estimaciones);
        planDto.setEstimaciones(ListEstimacionDto);

        return planDto;
    }

    /**
     * Elimina un plan por su ID.
     *
     * @param id ID del plan
     * @return entidad Plan eliminada
     * @throws NotFoundException si el plan no existe
     */
    public Plan deletePlanById(Long id) {
        Plan plan = planRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Plan no encontrado con id: " + id));

        planRepository.delete(plan);
        return plan;
    }

    /**
     * Actualiza un plan existente con los datos recibidos.
     *
     * @param dto DTO con los datos a actualizar
     * @param id  ID del plan
     * @return entidad Plan actualizada
     * @throws NotFoundException si el plan no existe
     */
    public Plan patchPlan(PlanDtoUpdateRequest dto, Long id) {
        Plan plan = planRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Plan no encontrado con id: " + id));

        PlanMapper.updateEntityFromDtoRes(dto, plan);
        planRepository.save(plan);
        return plan;
    }

    /**
     * Obtiene los miembros de la organización del plan con su deuda acumulada en el plan.
     *
     * @param planId ID del plan
     * @return lista de DTOs de miembros con la deuda en el plan
     * @throws NotFoundException si el plan no existe
     */
    public List<MiembroDtoResponse> getAjusteDeudasByOrganizacionId(Long planId){
        Plan plan = planRepository.findById(planId)
                .orElseThrow(() -> new NotFoundException("ERROR: Plan no encontrado con id: " + planId));

        //Obtener lso miembros del plan (tienen el mismo idOrg que el plan)
        List<Miembro> miembrosOrganizacion = miembroRepository.obtenerMiembrosPorOrganizacionId(plan.getOrganizacion().getId());
        List<MiembroDtoResponse> miembroDtoList = new java.util.ArrayList<>(List.of());

        for (Miembro miembro : miembrosOrganizacion) {
            MiembroDtoResponse miembroDto = MiembroMapper.toDtoResponse(miembro);

            // Calcular deuda en el plan
            double cantidad = 0.0;
            BigDecimal cantidadBD = repartoGastoRepository.sumarGastosPorMiembroYTPlanId(miembro.getId(), planId);
            if (cantidadBD != null) {
                cantidad = cantidadBD.doubleValue();
            }

            miembroDto.setDeudaEnPlan(cantidad);
            miembroDtoList.add(miembroDto);
        }

        return miembroDtoList;
    }

}
