package com.budgetpartner.APP.mapper;

import com.budgetpartner.APP.dto.plan.PlanDtoPostRequest;
import com.budgetpartner.APP.dto.plan.PlanDtoResponse;
import com.budgetpartner.APP.dto.plan.PlanDtoUpdateRequest;
import com.budgetpartner.APP.entity.Organizacion;
import com.budgetpartner.APP.entity.Plan;
import com.budgetpartner.APP.enums.ModoPlan;
import com.budgetpartner.APP.enums.MonedasDisponibles;
import com.budgetpartner.APP.mapper.PlanMapper;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PlanMapperTest {

    @Test
    void testToDtoResponse_NullPlan() {
        assertNull(PlanMapper.toDtoResponse(null));
    }

    @Test
    void testToDtoResponse_WithAllFields() {
        LocalDateTime inicio = LocalDateTime.of(2025, 1, 1, 10, 0);
        LocalDateTime fin = LocalDateTime.of(2025, 1, 10, 18, 0);
        LocalDateTime ahora = LocalDateTime.now();

        Organizacion org = new Organizacion(1L, "OrgPrueba", "DescripcionPrueba", MonedasDisponibles.EUR, ahora, ahora);
        Plan plan = new Plan(2L, org, "PlanPrueba", "DescripcionPlan", inicio, fin, ModoPlan.simple, 40.123, -3.456, ahora, ahora);

        PlanDtoResponse dto = PlanMapper.toDtoResponse(plan);

        assertNotNull(dto);
        assertEquals(2L, dto.getId());
        assertEquals("PlanPrueba", dto.getNombre());
        assertEquals("DescripcionPlan", dto.getDescripcion());
        assertEquals(inicio, dto.getFechaInicio());
        assertEquals(fin, dto.getFechaFin());
        assertEquals(ModoPlan.simple, dto.getModoPlan());
        assertEquals(40.123, dto.getLatitud());
        assertEquals(-3.456, dto.getLongitud());

    }

    @Test
    void testToEntity_NullDto() {
        assertNull(PlanMapper.toEntity(null, null));
    }

    @Test
    void testToEntity_WithValidDto() {
        LocalDateTime inicio = LocalDateTime.of(2025, 2, 1, 9, 0);
        LocalDateTime fin = LocalDateTime.of(2025, 2, 5, 20, 0);

        Organizacion org = new Organizacion();
        PlanDtoPostRequest dto = new PlanDtoPostRequest(1L, "PlanNuevo", "DescNuevo", inicio, fin, ModoPlan.estructurado, 12.34, 56.78);

        Plan plan = PlanMapper.toEntity(dto, org);

        assertNotNull(plan);
        assertEquals("PlanNuevo", plan.getNombre());
        assertEquals("DescNuevo", plan.getDescripcion());
        assertEquals(inicio, plan.getFechaInicio());
        assertEquals(fin, plan.getFechaFin());
        assertEquals(ModoPlan.estructurado, plan.getModoPlan());
        assertEquals(12.34, plan.getLatitud());
        assertEquals(56.78, plan.getLongitud());
        assertEquals(org, plan.getOrganizacion());
    }

    @Test
    void testUpdateEntityFromDtoRes_NullDtoOrPlan() {
        PlanMapper.updateEntityFromDtoRes(null, null);
        // No lanza excepción
    }

    @Test
    void testUpdateEntityFromDtoRes_UpdateFields() {
        LocalDateTime inicioViejo = LocalDateTime.of(2025, 3, 1, 9, 0);
        LocalDateTime finViejo = LocalDateTime.of(2025, 3, 10, 20, 0);
        Plan plan = new Plan();
        plan.setNombre("Viejo");
        plan.setDescripcion("Vieja");
        plan.setFechaInicio(inicioViejo);
        plan.setFechaFin(finViejo);
        plan.setLatitud(10.0);
        plan.setLongitud(20.0);

        LocalDateTime inicioNuevo = LocalDateTime.of(2025, 4, 1, 9, 0);
        LocalDateTime finNuevo = LocalDateTime.of(2025, 4, 10, 20, 0);
        PlanDtoUpdateRequest dto = new PlanDtoUpdateRequest("Nuevo", "Nueva", inicioNuevo, finNuevo, 99.9, 88.8);

        PlanMapper.updateEntityFromDtoRes(dto, plan);

        assertEquals("Nuevo", plan.getNombre());
        assertEquals("Nueva", plan.getDescripcion());
        assertEquals(inicioNuevo, plan.getFechaInicio());
        assertEquals(finNuevo, plan.getFechaFin());
        assertEquals(99.9, plan.getLatitud());
        assertEquals(88.8, plan.getLongitud());
    }

    @Test
    void testUpdateEntityFromDtoRes_PartialUpdate() {
        Plan plan = new Plan();
        plan.setNombre("ViejoNombre");
        plan.setDescripcion("ViejaDesc");

        // Solo cambiamos nombre
        PlanDtoUpdateRequest dto = new PlanDtoUpdateRequest("NuevoNombre", null, null, null, null, null);

        PlanMapper.updateEntityFromDtoRes(dto, plan);

        assertEquals("NuevoNombre", plan.getNombre());
        assertEquals("ViejaDesc", plan.getDescripcion());
    }

    @Test
    void testToDtoResponseListPlan_NullOrEmptyList() {
        assertTrue(PlanMapper.toDtoResponseListPlan(null).isEmpty());
        assertTrue(PlanMapper.toDtoResponseListPlan(Collections.emptyList()).isEmpty());
    }

    @Test
    void testToDtoResponseListPlan_WithElements() {
        LocalDateTime inicio = LocalDateTime.of(2025, 5, 1, 10, 0);
        LocalDateTime fin = LocalDateTime.of(2025, 5, 15, 18, 0);
        LocalDateTime ahora = LocalDateTime.now();

        Organizacion org = new Organizacion(10L, "OrgX", "DescX", MonedasDisponibles.USD, ahora, ahora);
        Plan plan1 = new Plan(100L, org, "Plan1", "Desc1", inicio, fin, ModoPlan.simple, 1.1, 2.2, ahora, ahora);
        Plan plan2 = new Plan(200L, org, "Plan2", "Desc2", inicio, fin, ModoPlan.estructurado, 3.3, 4.4, ahora, ahora);

        List<PlanDtoResponse> result = PlanMapper.toDtoResponseListPlan(Arrays.asList(plan1, plan2));

        assertEquals(2, result.size());
        assertEquals("Plan1", result.get(0).getNombre());
        assertEquals("Plan2", result.get(1).getNombre());
        assertEquals(100L, result.get(0).getId());
        assertEquals(200L, result.get(1).getId());
    }
}
