package com.budgetpartner.APP.mapper;

import com.budgetpartner.APP.dto.gasto.GastoDtoPostRequest;
import com.budgetpartner.APP.dto.gasto.GastoDtoResponse;
import com.budgetpartner.APP.dto.gasto.GastoDtoUpdateRequest;
import com.budgetpartner.APP.entity.*;
import com.budgetpartner.APP.enums.EstadoTarea;
import com.budgetpartner.APP.enums.ModoPlan;
import com.budgetpartner.APP.enums.MonedasDisponibles;
import com.budgetpartner.APP.enums.NombreRol;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GastoMapperTest {

    private Plan crearPlanDummy() {
        return new Plan(1L, null, "Plan Test", "Desc Plan", LocalDateTime.now(),
                LocalDateTime.now().plusDays(5), ModoPlan.estructurado, 40.5, -3.7,
                LocalDateTime.now(), LocalDateTime.now());
    }

    private Miembro crearMiembroDummy(Long id) {
        Rol rol = new Rol(NombreRol.ROLE_MEMBER);
        Organizacion org = new Organizacion();

        return new Miembro(org, rol, "Miembro 1");
    }

    private List<Miembro> crearEndeudadosDummy(){
        Rol rol = new Rol(NombreRol.ROLE_MEMBER);
        Organizacion org = new Organizacion();

        Miembro miembro1 = new Miembro(org, rol, "Miembro 1");
        Miembro miembro2 = new Miembro(org, rol, "Miembro 2");
        List<Miembro> miembros = new ArrayList<>();
        miembros.add(miembro1);
        miembros.add(miembro2);

        return  miembros;

    }

    private Tarea crearTareaDummy(Plan plan) {
        return new Tarea(10L, plan, "Tarea Test", "Desc tarea",
                LocalDateTime.now().plusDays(3), EstadoTarea.PENDIENTE,
                LocalDateTime.now(), LocalDateTime.now(), new ArrayList<>());
    }

    private Gasto crearGastoDummy() {
        Plan plan = crearPlanDummy();
        Miembro pagador = crearMiembroDummy(100L);
        Tarea tarea = crearTareaDummy(plan);
        List<Miembro> endeudados = new ArrayList<>();
        endeudados.add(crearMiembroDummy(200L));
        endeudados.add(crearMiembroDummy(201L));

        //

        return new Gasto(
                tarea,
                plan,
                75.5,
                "Gasto Test",
                pagador,
                "Desc Gasto",
                MonedasDisponibles.EUR
        );
    }

    @Test
    void testToDtoResponse_WithAllFields() {
        Gasto gasto = crearGastoDummy();

        GastoDtoResponse dto = GastoMapper.toDtoResponse(gasto);

        assertNotNull(dto);
        assertEquals(gasto.getId(), dto.getId());
        assertEquals(gasto.getTarea().getId(), dto.getTareaId());
        assertEquals(gasto.getPlan().getId(), dto.getPlanId());
        assertEquals(gasto.getCantidad(), dto.getCantidad());
        assertEquals(gasto.getNombre(), dto.getNombre());
        assertEquals(gasto.getDescripcion(), dto.getDescripcion());
        assertEquals(gasto.getMoneda(), dto.getMoneda());
        assertNotNull(dto.getPagador());
    }

    @Test
    void testToDtoResponse_WithNull() {
        assertNull(GastoMapper.toDtoResponse(null));
    }

    @Test
    void testToEntity_FromPostRequest() {
        Plan plan = crearPlanDummy();
        Tarea tarea = crearTareaDummy(plan);
        Miembro pagador = crearMiembroDummy(123L);

        GastoDtoPostRequest dto = new GastoDtoPostRequest(
                1L,
                2L,
                99.9,
                "Gasto Nuevo",
                4L,
                "Descripción Nueva",
                Arrays.asList(1L, 2L),
                MonedasDisponibles.EUR
        );

        Gasto gasto = GastoMapper.toEntity(dto, tarea, plan, pagador);

        assertNotNull(gasto);
        assertEquals(99.9, gasto.getCantidad(), 0.00001);
        assertEquals(dto.getNombre(), gasto.getNombre());
        assertEquals(dto.getDescripcion(), gasto.getDescripcion());
        assertEquals(dto.getMoneda(), gasto.getMoneda());
        assertEquals(pagador, gasto.getPagador());
        assertEquals(plan, gasto.getPlan());
        assertEquals(tarea, gasto.getTarea());
    }

    @Test
    void testToEntity_WithNull() {
        assertNull(GastoMapper.toEntity(null, null, null, null));
    }

    @Test
    void testUpdateEntityFromDtoRes() {
        Gasto gasto = crearGastoDummy();

        GastoDtoUpdateRequest dto = new GastoDtoUpdateRequest(
                200.0,
                "Nombre Actualizado",
                4L,
                "Descripcion Actualizada",
                Arrays.asList(1L, 2L),
                MonedasDisponibles.EUR
        );

        GastoMapper.updateEntityFromDtoRes(dto, gasto);

        assertEquals(200.0, gasto.getCantidad());
        assertEquals("Nombre Actualizado", gasto.getNombre());
        assertEquals("Descripcion Actualizada", gasto.getDescripcion());
    }

    @Test
    void testUpdateEntityFromDtoRes_WithNulls() {
        Gasto gasto = crearGastoDummy();
        GastoMapper.updateEntityFromDtoRes(null, gasto);
        GastoMapper.updateEntityFromDtoRes(new GastoDtoUpdateRequest(0, null, null, null, null, null), gasto);

        // No cambia nada porque dto no aporta valores válidos
        assertEquals("Gasto Test", gasto.getNombre());
    }

    @Test
    void testToDtoResponseListGasto() {
        List<Gasto> lista = new ArrayList<>();
        lista.add(crearGastoDummy());

        List<GastoDtoResponse> dtoList = GastoMapper.toDtoResponseListGasto(lista);

        assertNotNull(dtoList);
        assertEquals(1, dtoList.size());
    }

    @Test
    void testToDtoResponseListGasto_WithEmptyList() {
        List<GastoDtoResponse> dtoList = GastoMapper.toDtoResponseListGasto(new ArrayList<>());

        assertNotNull(dtoList);
        assertTrue(dtoList.isEmpty());
    }

    @Test
    void testToDtoResponseListGasto_WithNull() {
        List<GastoDtoResponse> dtoList = GastoMapper.toDtoResponseListGasto(null);

        assertNotNull(dtoList);
        assertTrue(dtoList.isEmpty());
    }
}

