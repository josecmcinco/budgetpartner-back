package com.budgetpartner.APP.mapper;

import com.budgetpartner.APP.dto.plan.PlanDtoResponse;
import com.budgetpartner.APP.dto.tarea.TareaDtoPostRequest;
import com.budgetpartner.APP.dto.tarea.TareaDtoResponse;
import com.budgetpartner.APP.dto.tarea.TareaDtoUpdateRequest;
import com.budgetpartner.APP.entity.*;
import com.budgetpartner.APP.enums.EstadoTarea;
import com.budgetpartner.APP.enums.ModoPlan;
import com.budgetpartner.APP.enums.MonedasDisponibles;
import com.budgetpartner.APP.enums.NombreRol;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TareaMapperTest {

    private Plan crearPlanDummy() {
        Organizacion org = new Organizacion(1L, "Org Test", "Desc", MonedasDisponibles.EUR, null, null);
        return new Plan(
                1L,
                org,
                "Plan Test",
                "Descripcion Plan",
                LocalDateTime.now(),
                LocalDateTime.now().plusDays(5),
                ModoPlan.estructurado,
                10.5,
                20.5,
                LocalDateTime.now(),
                LocalDateTime.now()
        );
    }

    @Test
    void testToDtoResponse_WithAllFields() {
        Plan plan = crearPlanDummy();

        //Crear lista de miembros
        Rol rol = new Rol(NombreRol.ROLE_MEMBER);
        Organizacion org = new Organizacion();

        Miembro miembro1 = new Miembro(org, rol, "Miembro 1");
        Miembro miembro2 = new Miembro(org, rol, "Miembro 2");
        List<Miembro> miembros = new ArrayList<>();
        miembros.add(miembro1);
        miembros.add(miembro2);

        Tarea tarea = new Tarea(
                1L,
                plan,
                "Titulo tarea",
                "Descripcion tarea",
                LocalDateTime.now().plusDays(2),
                EstadoTarea.PENDIENTE,
                LocalDateTime.now(),
                LocalDateTime.now(),
                miembros
        );

        TareaDtoResponse dto = TareaMapper.toDtoResponse(tarea);

        assertNotNull(dto);
        assertEquals(tarea.getId(), dto.getId());
        assertEquals(tarea.getTitulo(), dto.getTitulo());
        assertEquals(tarea.getDescripcion(), dto.getDescripcion());
        assertEquals(tarea.getEstado(), dto.getEstado());
        assertTrue(dto.getPlan() != null);
    }

    @Test
    void testToDtoResponse_NullTarea() {
        assertNull(TareaMapper.toDtoResponse(null));
    }

    @Test
    void testToEntity_WithValidDto() {
        Plan plan = crearPlanDummy();
        LocalDateTime fechaFin = LocalDateTime.now().plusDays(3);
        TareaDtoPostRequest dto = new TareaDtoPostRequest(1L, "Nueva tarea", "Descripcion", fechaFin, EstadoTarea.PENDIENTE , null);

        Tarea tarea = TareaMapper.toEntity(dto, plan);

        assertNotNull(tarea);
        assertEquals("Nueva tarea", tarea.getTitulo());
        assertEquals("Descripcion", tarea.getDescripcion());
        assertEquals(fechaFin, tarea.getFechaFin());
        assertEquals(plan, tarea.getPlan());
    }

    @Test
    void testToEntity_NullDto() {
        assertNull(TareaMapper.toEntity(null, crearPlanDummy()));
    }

    @Test
    void testUpdateEntityFromDtoRes_UpdateFields() {
        Plan plan = crearPlanDummy();
        Tarea tarea = new Tarea(
                plan, "Viejo titulo", "Vieja desc",
                LocalDateTime.now().plusDays(1));

        TareaDtoUpdateRequest dto = new TareaDtoUpdateRequest(
                "Nuevo titulo", "Nueva desc",
                LocalDateTime.now().plusDays(10),
                EstadoTarea.COMPLETADA, null);

        TareaMapper.updateEntityFromDtoRes(dto, tarea);

        assertEquals("Nuevo titulo", tarea.getTitulo());
        assertEquals("Nueva desc", tarea.getDescripcion());
        assertEquals(dto.getFechaFin(), tarea.getFechaFin());
        assertEquals(EstadoTarea.COMPLETADA, tarea.getEstado());
    }

    @Test
    void testUpdateEntityFromDtoRes_PartialUpdate() {
        Plan plan = crearPlanDummy();
        Tarea tarea = new Tarea(plan, "Titulo", "Desc", LocalDateTime.now());

        TareaDtoUpdateRequest dto = new TareaDtoUpdateRequest(null, "Desc nueva", null, null, null);

        TareaMapper.updateEntityFromDtoRes(dto, tarea);

        assertEquals("Titulo", tarea.getTitulo()); // no cambia
        assertEquals("Desc nueva", tarea.getDescripcion()); // cambia
    }

    @Test
    void testToDtoResponseListTarea_NullOrEmpty() {
        assertTrue(TareaMapper.toDtoResponseListTarea(null).isEmpty());
        assertTrue(TareaMapper.toDtoResponseListTarea(Collections.emptyList()).isEmpty());
    }

    @Test
    void testToDtoResponseListTarea_WithElements() {
        Plan plan = crearPlanDummy();
        Tarea t1 = new Tarea(plan, "Tarea1", "Desc1", LocalDateTime.now());
        Tarea t2 = new Tarea(plan, "Tarea2", "Desc2", LocalDateTime.now());

        List<TareaDtoResponse> result = TareaMapper.toDtoResponseListTarea(Arrays.asList(t1, t2));

        assertEquals(2, result.size());
        assertEquals("Tarea1", result.get(0).getTitulo());
        assertEquals("Tarea2", result.get(1).getTitulo());
    }
}

