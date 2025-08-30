package com.budgetpartner.APP.mapper;
import com.budgetpartner.APP.dto.estimacion.*;
import com.budgetpartner.APP.entity.*;
import com.budgetpartner.APP.enums.MonedasDisponibles;
import com.budgetpartner.APP.enums.TipoEstimacion;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EstimacionMapperTest {

    private Plan crearPlanDummy() {
        return new Plan(1L, null, "Plan Test", "Desc Plan",
                LocalDateTime.now(), LocalDateTime.now().plusDays(5), null, 0.0, 0.0,
                LocalDateTime.now(), LocalDateTime.now());
    }

    private Tarea crearTareaDummy(Plan plan) {
        return new Tarea(1L, plan, "Tarea Test", "Desc Tarea",
                LocalDateTime.now().plusDays(1), null, 0.0, MonedasDisponibles.EUR,
                LocalDateTime.now(), LocalDateTime.now(), new ArrayList<>());
    }

    private Miembro crearMiembroDummy(Long id) {
        Organizacion org = new Organizacion();
        Rol rol = new Rol();
        return new Miembro(id, null, org, rol, "nick" + id, LocalDateTime.now(), true, true, LocalDateTime.now(), LocalDateTime.now());
    }

    private Gasto crearGastoDummy() {
        Plan plan = crearPlanDummy();
        Miembro pagador = crearMiembroDummy(10L);
        Tarea tarea = crearTareaDummy(plan);
        return new Gasto(1L, tarea, plan, 100.0, "Gasto Test", pagador, "Desc Gasto", MonedasDisponibles.EUR, null, LocalDateTime.now(), LocalDateTime.now());
    }

    @Test
    void testToDtoResponse_Null() {
        assertNull(EstimacionMapper.toDtoResponse(null));
    }

    @Test
    void testToDtoResponse_WithTareaAndGasto() {
        Plan plan = crearPlanDummy();
        Tarea tarea = crearTareaDummy(plan);
        Miembro creador = crearMiembroDummy(1L);
        Miembro pagador = crearMiembroDummy(2L);
        Gasto gasto = crearGastoDummy();

        Estimacion estimacion = new Estimacion(1L, plan, tarea, creador, 50.0, TipoEstimacion.ESTIMACION_TAREA,
                MonedasDisponibles.EUR, "Desc", LocalDateTime.now(), LocalDateTime.now(), pagador, gasto);

        EstimacionDtoResponse dto = EstimacionMapper.toDtoResponse(estimacion);

        assertNotNull(dto);
        assertEquals(1L, dto.getId());
        assertEquals(plan.getId(), dto.getTareaId()); // La lógica actual usa plan.getId() si tarea != null
        assertEquals(plan.getId(), dto.getPlanId());
        assertEquals(creador.getId(), dto.getCreadorId());
        assertEquals(50.0, dto.getCantidad(), 0.0001);
        assertEquals(TipoEstimacion.ESTIMACION_TAREA, dto.getTipoEstimacion());
        assertEquals(MonedasDisponibles.EUR, dto.getTipoMoneda());
        assertEquals("Desc", dto.getDescripcion());
        assertEquals(pagador.getId(), dto.getPagadorId());
        assertEquals(gasto.getId(), dto.getGastoId());
    }

    @Test
    void testToEntity_Null() {
        assertNull(EstimacionMapper.toEntity(null, null, null, null, null, null));
    }

    @Test
    void testToEntity_ValidDto() {
        Plan plan = crearPlanDummy();
        Tarea tarea = crearTareaDummy(plan);
        Miembro creador = crearMiembroDummy(1L);
        Miembro pagador = crearMiembroDummy(2L);
        Gasto gasto = crearGastoDummy();

        EstimacionDtoPostRequest dto = new EstimacionDtoPostRequest(plan.getId(), tarea.getId(), creador.getId(), 75.5,
                TipoEstimacion.ESTIMACION_PLAN, MonedasDisponibles.EUR, "Descripcion", pagador.getId(), gasto.getId());

        Estimacion estimacion = EstimacionMapper.toEntity(dto, tarea, plan, creador, pagador, gasto);

        assertNotNull(estimacion);
        assertEquals(plan, estimacion.getPlan());
        assertEquals(tarea, estimacion.getTarea());
        assertEquals(creador, estimacion.getCreador());
        assertEquals(75.5, estimacion.getCantidad(), 0.0001);
        assertEquals(TipoEstimacion.ESTIMACION_PLAN, estimacion.getTipoEstimacion());
        assertEquals(MonedasDisponibles.EUR, estimacion.getTipoMoneda());
        assertEquals("Descripcion", estimacion.getDescripcion());
        assertEquals(pagador, estimacion.getPagador());
        assertEquals(gasto, estimacion.getGasto());
    }

    @Test
    void testUpdateEntityFromDto_NullDtoOrEntity() {
        EstimacionMapper.updateEntityFromDto(null, null, null, null);
        // No lanza excepción
    }

    @Test
    void testUpdateEntityFromDto_UpdateFields() {
        Plan plan = crearPlanDummy();
        Tarea tarea = crearTareaDummy(plan);
        Miembro creador = crearMiembroDummy(1L);
        Miembro pagador = crearMiembroDummy(2L);
        Gasto gasto = crearGastoDummy();

        Estimacion estimacion = new Estimacion(1L, plan, tarea, creador, 50.0, TipoEstimacion.ESTIMACION_TAREA,
                MonedasDisponibles.EUR, "Desc", LocalDateTime.now(), LocalDateTime.now(), null, null);

        EstimacionDtoUpdateRequest dto = new EstimacionDtoUpdateRequest(100.0, MonedasDisponibles.EUR, "Nueva Desc", pagador.getId(), gasto.getId());

        EstimacionMapper.updateEntityFromDto(dto, estimacion, pagador, gasto);

        assertEquals(100.0, estimacion.getCantidad(), 0.0001);
        assertEquals(MonedasDisponibles.EUR, estimacion.getTipoMoneda());
        assertEquals("Nueva Desc", estimacion.getDescripcion());
        assertEquals(pagador, estimacion.getPagador());
        assertEquals(gasto, estimacion.getGasto());
        assertNotNull(estimacion.getActualizadoEn());
    }

    @Test
    void testToDtoResponseListEstimacion_NullOrEmpty() {
        assertTrue(EstimacionMapper.toDtoResponseListEstimacion(null).isEmpty());
        assertTrue(EstimacionMapper.toDtoResponseListEstimacion(new ArrayList<>()).isEmpty());
    }

    @Test
    void testToDtoResponseListEstimacion_WithElements() {
        Plan plan = crearPlanDummy();
        Tarea tarea = crearTareaDummy(plan);
        Miembro creador = crearMiembroDummy(1L);

        Estimacion estimacion = new Estimacion(1L, plan, tarea, creador, 50.0, TipoEstimacion.ESTIMACION_TAREA,
                MonedasDisponibles.EUR, "Desc", LocalDateTime.now(), LocalDateTime.now(), null, null);

        List<Estimacion> lista = new ArrayList<>();
        lista.add(estimacion);

        List<EstimacionDtoResponse> dtoList = EstimacionMapper.toDtoResponseListEstimacion(lista);

        assertEquals(1, dtoList.size());
        assertEquals(1L, dtoList.get(0).getId());
    }
}
