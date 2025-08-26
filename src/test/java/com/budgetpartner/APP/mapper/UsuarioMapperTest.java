package com.budgetpartner.APP.mapper;

import com.budgetpartner.APP.dto.usuario.UsuarioDtoPostRequest;
import com.budgetpartner.APP.dto.usuario.UsuarioDtoResponse;
import com.budgetpartner.APP.dto.usuario.UsuarioDtoUpdateRequest;
import com.budgetpartner.APP.entity.Usuario;
import com.budgetpartner.APP.entity.Miembro;
import com.budgetpartner.APP.mapper.UsuarioMapper;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UsuarioMapperTest {

    @Test
    void testToDtoResponse_WithAllFields() {
        Usuario usuario = new Usuario(1L, "correo@test.com", "Juan", "Pérez", "hash123",  null, null);

        UsuarioDtoResponse dto = UsuarioMapper.toDtoResponse(usuario);

        assertNotNull(dto);
        assertEquals(1L, dto.getId());
        assertEquals("correo@test.com", dto.getEmail());
        assertEquals("Juan", dto.getNombre());
        assertEquals("Pérez", dto.getApellido());
    }

    @Test
    void testToEntity_NullDto() {
        assertNull(UsuarioMapper.toEntity(null));
    }

    @Test
    void testToEntity_WithValidDto() {
        UsuarioDtoPostRequest dto = new UsuarioDtoPostRequest("nuevo@test.com", "Ana", "López", "clave123");

        Usuario usuario = UsuarioMapper.toEntity(dto);

        assertNotNull(usuario);
        assertEquals("nuevo@test.com", usuario.getEmail());
        assertEquals("Ana", usuario.getNombre());
        assertEquals("López", usuario.getApellido());
        assertEquals("clave123", usuario.getContraseña());
    }

    @Test
    void testUpdateEntityFromDtoRes_NullDtoOrUsuario() {
        UsuarioMapper.updateEntityFromDtoRes(null, null);
        // no debe lanzar excepción
    }

    @Test
    void testUpdateEntityFromDtoRes_UpdateFields() {
        Usuario usuario = new Usuario();
        usuario.setEmail("viejo@test.com");
        usuario.setNombre("Viejo");
        usuario.setApellido("ApellidoViejo");

        UsuarioDtoUpdateRequest dto = new UsuarioDtoUpdateRequest("nuevo@test.com", "NuevoNombre", "NuevoApellido", "nuevaContraseña");

        UsuarioMapper.updateEntityFromDtoRes(dto, usuario);

        assertEquals("nuevo@test.com", usuario.getEmail());
        assertEquals("NuevoNombre", usuario.getNombre());
        assertEquals("NuevoApellido", usuario.getApellido());
    }

    @Test
    void testUpdateEntityFromDtoRes_PartialUpdate() {
        Usuario usuario = new Usuario();
        usuario.setEmail("viejo@test.com");
        usuario.setNombre("Viejo");
        usuario.setApellido("ViejoApellido");

        UsuarioDtoUpdateRequest dto = new UsuarioDtoUpdateRequest(null, "NuevoNombre", null, null);

        UsuarioMapper.updateEntityFromDtoRes(dto, usuario);

        assertEquals("viejo@test.com", usuario.getEmail());
        assertEquals("NuevoNombre", usuario.getNombre());
        assertEquals("ViejoApellido", usuario.getApellido());
    }

    @Test
    void testToDtoResponseListUsuario_NullOrEmptyList() {
        assertTrue(UsuarioMapper.toDtoResponseListUsuario(null).isEmpty());
        assertTrue(UsuarioMapper.toDtoResponseListUsuario(Collections.emptyList()).isEmpty());
    }

    @Test
    void testToDtoResponseListUsuario_WithElements() {
        Usuario u1 = new Usuario(1L, "a@test.com", "A", "Uno", "pass", null, null);
        Usuario u2 = new Usuario(2L, "b@test.com", "B", "Dos", "pass", null, null);

        List<UsuarioDtoResponse> result = UsuarioMapper.toDtoResponseListUsuario(Arrays.asList(u1, u2));

        assertEquals(2, result.size());
        assertEquals("a@test.com", result.get(0).getEmail());
        assertEquals("b@test.com", result.get(1).getEmail());
    }
}
