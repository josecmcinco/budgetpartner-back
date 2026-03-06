package com.budgetpartner.APP.service;
/*
import com.budgetpartner.APP.dto.estimacion.EstimacionDtoResponse;
import com.budgetpartner.APP.dto.gasto.GastoDtoResponse;
import com.budgetpartner.APP.dto.miembro.MiembroDtoResponse;
import com.budgetpartner.APP.dto.plan.PlanDtoPostRequest;
import com.budgetpartner.APP.dto.plan.PlanDtoResponse;
import com.budgetpartner.APP.dto.plan.PlanDtoUpdateRequest;
import com.budgetpartner.APP.dto.tarea.TareaDtoResponse;
import com.budgetpartner.APP.entity.*;
import com.budgetpartner.APP.enums.ModoPlan;
import com.budgetpartner.APP.enums.MonedasDisponibles;
import com.budgetpartner.APP.exceptions.NotFoundException;
import com.budgetpartner.APP.mapper.*;
import com.budgetpartner.APP.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PlanServiceTest {

    @InjectMocks
    private PlanService planService;

    @Mock private PlanRepository planRepository;
    @Mock private OrganizacionRepository organizacionRepository;
    @Mock private MiembroRepository miembroRepository;
    @Mock private GastoRepository gastoRepository;
    @Mock private TareaRepository tareaRepository;
    @Mock private EstimacionRepository estimacionRepository;
    @Mock private RepartoGastoRepository repartoGastoRepository;

    private LocalDateTime date;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        date = LocalDateTime.of(2025, 1, 1, 12, 0);
    }

    @Test
    void testPostPlan_success() {
        Long orgId = 10L;
        Organizacion org = new Organizacion(orgId, "Org1", "desc", MonedasDisponibles.EUR, date, date);
        PlanDtoPostRequest request = new PlanDtoPostRequest(orgId, "Plan1", "Desc plan", date, date, ModoPlan.simple, 0.0, 0.0);
        Plan plan = new Plan(1L, org, "Plan1", "Desc plan", date, date, ModoPlan.simple, 0.0, 0.0, date, date);
        PlanDtoResponse response = new PlanDtoResponse(1L, "Plan1", "Desc plan", date, date, null, null, null, null, null);

        when(organizacionRepository.findById(orgId)).thenReturn(Optional.of(org));
        when(planRepository.save(any(Plan.class))).thenReturn(plan);

        try (MockedStatic<PlanMapper> mapper = mockStatic(PlanMapper.class)) {
            mapper.when(() -> PlanMapper.toEntity(request, org)).thenReturn(plan);
            mapper.when(() -> PlanMapper.toDtoResponse(plan)).thenReturn(response);

            PlanDtoResponse result = planService.postPlan(request);

            assertNotNull(result);
            assertEquals(1L, result.getId());
            assertEquals("Plan1", result.getNombre());

            verify(organizacionRepository).findById(orgId);
            verify(planRepository).save(plan);
        }
    }

    @Test
    void testPostPlan_notFoundOrganizacion() {
        Long orgId = 99L;
        PlanDtoPostRequest request = new PlanDtoPostRequest(orgId, "PlanX", "Desc", date, date, ModoPlan.simple, 0.0, 0.0);

        when(organizacionRepository.findById(orgId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> planService.postPlan(request));
        verify(organizacionRepository).findById(orgId);
        verifyNoInteractions(planRepository);
    }

    @Test
    void testGetPlanByIdAndTransform_success() {
        Long planId = 5L;
        Organizacion org = new Organizacion(10L, "Org1", "desc", MonedasDisponibles.EUR, date, date);
        Plan plan = new Plan(planId, org, "Plan1", "Desc plan", date, date, ModoPlan.simple, 0.0, 0.0, date, date);
        PlanDtoResponse dtoResponse = new PlanDtoResponse(planId, "Plan1", "Desc plan", date, date, null, null, null, null, null);
        dtoResponse.setOrganizacionDtoResponse(OrganizacionMapper.toDtoResponse(org));

        Miembro miembro = new Miembro(1L, null, org, null, "nick", date, true, true, date, date);
        Gasto gasto = new Gasto(1L, "gasto1", 20.0, plan, null, date, date);
        Tarea tarea = new Tarea(1L, "tarea1", "desc", plan, null, date, date);
        Estimacion estimacion = new Estimacion(1L, "estim1", 10.0, plan, null, date, date);

        when(planRepository.findById(planId)).thenReturn(Optional.of(plan));
        when(miembroRepository.obtenerMiembrosPorOrganizacionId(org.getId())).thenReturn(List.of(miembro));
        when(gastoRepository.obtenerGastosPorPlanId(planId)).thenReturn(List.of(gasto));
        when(tareaRepository.obtenerTareasPorPlanId(planId)).thenReturn(List.of(tarea));
        when(estimacionRepository.obtenerEstimacionesPorPlanId(planId)).thenReturn(List.of(estimacion));

        try (MockedStatic<PlanMapper> planMapper = mockStatic(PlanMapper.class);
             MockedStatic<MiembroMapper> miembroMapper = mockStatic(MiembroMapper.class);
             MockedStatic<GastoMapper> gastoMapper = mockStatic(GastoMapper.class);
             MockedStatic<TareaMapper> tareaMapper = mockStatic(TareaMapper.class);
             MockedStatic<EstimacionMapper> estimacionMapper = mockStatic(EstimacionMapper.class)) {

            planMapper.when(() -> PlanMapper.toDtoResponse(plan)).thenReturn(dtoResponse);
            miembroMapper.when(() -> MiembroMapper.toDtoResponseListMiembro(List.of(miembro)))
                    .thenReturn(List.of(new MiembroDtoResponse(1L, 2L, "nick", date, true, true)));
            gastoMapper.when(() -> GastoMapper.toDtoResponseListGasto(List.of(gasto)))
                    .thenReturn(List.of(new GastoDtoResponse()));
            tareaMapper.when(() -> TareaMapper.toDtoResponseListTarea(List.of(tarea)))
                    .thenReturn(List.of(new TareaDtoResponse()));
            estimacionMapper.when(() -> EstimacionMapper.toDtoResponseListEstimacion(List.of(estimacion)))
                    .thenReturn(List.of(new EstimacionDtoResponse()));

            PlanDtoResponse result = planService.getPlanByIdAndTrasnform(planId);

            assertNotNull(result);
            assertEquals(planId, result.getId());
            assertEquals("Plan1", result.getNombre());
            assertEquals(1, result.getOrganizacionDtoResponse().getMiembros().size());
            assertEquals(1, result.getGastos().size());
            assertEquals(1, result.getTareas().size());
            assertEquals(1, result.getEstimaciones().size());
        }
    }

    @Test
    void testGetPlanByIdAndTransform_notFound() {
        Long planId = 5L;
        when(planRepository.findById(planId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> planService.getPlanByIdAndTrasnform(planId));
        verify(planRepository).findById(planId);
    }

    @Test
    void testDeletePlanById_success() {
        Long planId = 7L;
        Plan plan = new Plan(planId, null, "Plan1", "Desc", date, date, ModoPlan.simple, 0.0, 0.0, date, date);
        when(planRepository.findById(planId)).thenReturn(Optional.of(plan));

        Plan result = planService.deletePlanById(planId);

        assertNotNull(result);
        assertEquals(planId, result.getId());
        verify(planRepository).delete(plan);
    }

    @Test
    void testDeletePlanById_notFound() {
        Long planId = 7L;
        when(planRepository.findById(planId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> planService.deletePlanById(planId));
    }



    @Test
    void testPatchPlan_success() {
        Long planId = 3L;
        Plan plan = new Plan(planId, null, "Old", "Old desc", date, date, ModoPlan.estructurado, 0.0, 0.0, date, date);
        PlanDtoUpdateRequest updateReq = new PlanDtoUpdateRequest("New", "New desc",  date, date,  5.0, 5.0);

        when(planRepository.findById(planId)).thenReturn(Optional.of(plan));
        when(planRepository.save(any(Plan.class))).thenReturn(plan);

        try (MockedStatic<PlanMapper> mapper = mockStatic(PlanMapper.class)) {
            mapper.when(() -> PlanMapper.updateEntityFromDtoRes(updateReq, plan)).thenAnswer(invocation -> null);

            Plan result = planService.patchPlan(updateReq, planId);

            assertNotNull(result);
            verify(planRepository).save(plan);
        }
    }

    @Test
    void testPatchPlan_notFound() {
        Long planId = 3L;
        PlanDtoUpdateRequest updateReq = new PlanDtoUpdateRequest("New", "New desc", date, date, 5.0, 5.0);

        when(planRepository.findById(planId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> planService.patchPlan(updateReq, planId));
    }

    @Test
    void testGetAjusteDeudasByOrganizacionId_success() {
        Long planId = 11L;
        Organizacion org = new Organizacion(10L, "Org", "desc", MonedasDisponibles.EUR, date, date);
        Plan plan = new Plan(planId, org, "Plan1", "Desc", date, date, ModoPlan.simple, 0.0, 0.0, date, date);
        Miembro miembro = new Miembro(1L, null, org, null, "nick", date, true, true, date, date);

        when(planRepository.findById(planId)).thenReturn(Optional.of(plan));
        when(miembroRepository.obtenerMiembrosPorOrganizacionId(org.getId())).thenReturn(List.of(miembro));
        when(repartoGastoRepository.sumarGastosPorMiembroYTPlanId(1L, planId)).thenReturn(BigDecimal.valueOf(123.45));

        try (MockedStatic<MiembroMapper> mapper = mockStatic(MiembroMapper.class)) {
            MiembroDtoResponse miembroDto = new MiembroDtoResponse(1L, 2L, "nick", date, true, true);
            mapper.when(() -> MiembroMapper.toDtoResponse(miembro)).thenReturn(miembroDto);

            List<MiembroDtoResponse> result = planService.getAjusteDeudasByOrganizacionId(planId);

            assertNotNull(result);
            assertEquals(1, result.size());
            assertEquals(123.45, result.get(0).getDeudaEnPlan());
        }
    }

    @Test
    void testGetAjusteDeudasByOrganizacionId_planNotFound() {
        Long planId = 11L;
        when(planRepository.findById(planId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> planService.getAjusteDeudasByOrganizacionId(planId));
    }

    @Test
    void testGetAjusteDeudasByOrganizacionId_nullDeuda() {
        Long planId = 11L;
        Organizacion org = new Organizacion(10L, "Org", "desc", MonedasDisponibles.EUR, date, date);
        Plan plan = new Plan(planId, org, "Plan1", "Desc", date, date, ModoPlan.simple, 0.0, 0.0, date, date);
        Miembro miembro = new Miembro(1L, null, org, null, "nick", date, true, true, date, date);

        when(planRepository.findById(planId)).thenReturn(Optional.of(plan));
        when(miembroRepository.obtenerMiembrosPorOrganizacionId(org.getId())).thenReturn(List.of(miembro));
        when(repartoGastoRepository.sumarGastosPorMiembroYTPlanId(1L, planId)).thenReturn(null);

        try (MockedStatic<MiembroMapper> mapper = mockStatic(MiembroMapper.class)) {
            MiembroDtoResponse miembroDto = new MiembroDtoResponse(1L, 2L, "nick", date, true, true);
            mapper.when(() -> MiembroMapper.toDtoResponse(miembro)).thenReturn(miembroDto);

            List<MiembroDtoResponse> result = planService.getAjusteDeudasByOrganizacionId(planId);

            assertEquals(0.0, result.get(0).getDeudaEnPlan());
        }
    }
}


 */