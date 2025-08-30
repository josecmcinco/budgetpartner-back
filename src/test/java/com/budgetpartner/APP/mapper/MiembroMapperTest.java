package com.budgetpartner.APP.mapper;

import com.budgetpartner.APP.dto.miembro.MiembroDtoPostRequest;
import com.budgetpartner.APP.dto.miembro.MiembroDtoResponse;
import com.budgetpartner.APP.dto.miembro.MiembroDtoUpdateRequest;
import com.budgetpartner.APP.entity.Miembro;
import com.budgetpartner.APP.entity.Organizacion;
import com.budgetpartner.APP.entity.Rol;
import com.budgetpartner.APP.entity.Usuario;
import com.budgetpartner.APP.enums.MonedasDisponibles;
import com.budgetpartner.APP.enums.NombreRol;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class MiembroMapperTest {

    @Test
    void testToDtoResponse_NullMiembro() {
        assertNull(MiembroMapper.toDtoResponse(null));
    }

    @Test
    void testToDtoResponse_WithAllFields() {
        LocalDateTime date = LocalDateTime.of(2025, 1, 1, 12, 0);

        Rol rol = new Rol(1L, NombreRol.ROLE_ADMIN, date, date);
        Organizacion organizacion = new Organizacion(4L, "Organizacion1", "Descripción 1", MonedasDisponibles.EUR, date, date);
        Miembro miembro = new Miembro(5L, null, organizacion, rol, "nickPrueba", date, true, false, date, date);

        MiembroDtoResponse dto = MiembroMapper.toDtoResponse(miembro);

        assertNotNull(dto);
        assertEquals(5L, dto.getId());
        assertEquals(rol.getId(), dto.getRolId());
        assertEquals("nickPrueba", dto.getNick());
        assertEquals(date, dto.getFechaIngreso());
        assertFalse(dto.getIsAsociado());
        assertTrue(dto.getisActivo());
    }

    @Test
    void testToEntity_NullDto() {
        assertNull(MiembroMapper.toEntity(null, null, null));
    }

    @Test
    void testToEntity_WithValidDto() {
        LocalDateTime date = LocalDateTime.of(2025, 1, 1, 12, 0);

        Organizacion org = new Organizacion();
        Rol rol = new Rol(1L, NombreRol.ROLE_ADMIN, date, date);
        MiembroDtoPostRequest dto = new MiembroDtoPostRequest(1L,1L, "nickNuevo");

        Miembro miembro = MiembroMapper.toEntity(dto, org, rol);

        assertNotNull(miembro);
        assertEquals(org, miembro.getOrganizacion());
        assertEquals(rol, miembro.getRol());
        assertEquals("nickNuevo", miembro.getNick());
    }

    @Test
    void testUpdateEntityFromDtoRes_NullDtoOrMiembro() {
        MiembroMapper.updateEntityFromDtoRes(null, null, null);
        // No lanza excepción
    }

    @Test
    void testUpdateEntityFromDtoRes_UpdateFields() {
        Rol rolNuevo = new Rol(2L, NombreRol.ROLE_MEMBER, LocalDateTime.now(), LocalDateTime.now());
        Miembro miembro = new Miembro();
        miembro.setNick("nickViejo");
        miembro.setRol(new Rol(1L, NombreRol.ROLE_ADMIN, LocalDateTime.now(), LocalDateTime.now()));

        MiembroDtoUpdateRequest dto = new MiembroDtoUpdateRequest(null,rolNuevo.getId(), "nickActualizado");

        //Ejecución prueba
        MiembroMapper.updateEntityFromDtoRes(dto, miembro, rolNuevo);

        assertEquals("nickActualizado", miembro.getNick());
        assertEquals(rolNuevo, miembro.getRol());
    }

    @Test
    void testToDtoResponseListMiembro_NullOrEmptyList() {
        assertTrue(MiembroMapper.toDtoResponseListMiembro(Collections.emptyList()).isEmpty());
    }

    @Test
    void testToDtoResponseListMiembro_WithElements() {
        LocalDateTime date = LocalDateTime.of(2025, 1, 1, 12, 0);
        Rol rol = new Rol(1L, NombreRol.ROLE_ADMIN, date, date);
        Miembro miembro1 = new Miembro(1L, null, null, rol, "nick1", date, true, false, date, date);
        Miembro miembro2 = new Miembro(2L, null, null, rol, "nick2", date, true, false,date, date);

        List<MiembroDtoResponse> result = MiembroMapper.toDtoResponseListMiembro(Arrays.asList(miembro1, miembro2));

        assertEquals(2, result.size());
        assertEquals("nick1", result.get(0).getNick());
        assertEquals("nick2", result.get(1).getNick());
        assertEquals(1L, result.get(0).getId());
        assertEquals(2L, result.get(1).getId());
    }
}