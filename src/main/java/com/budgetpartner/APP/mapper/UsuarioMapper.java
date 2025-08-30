package com.budgetpartner.APP.mapper;

import com.budgetpartner.APP.dto.usuario.UsuarioDtoPostRequest;
import com.budgetpartner.APP.dto.usuario.UsuarioDtoUpdateRequest;
import com.budgetpartner.APP.dto.usuario.UsuarioDtoResponse;
import com.budgetpartner.APP.entity.Usuario;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UsuarioMapper {

    /**
     * Convierte una entidad Usuario en un DTO de respuesta.
     */
    public static UsuarioDtoResponse toDtoResponse(Usuario usuario) {
        if (usuario == null) return null;

        return new UsuarioDtoResponse(
                usuario.getId(),
                usuario.getEmail(),
                usuario.getNombre(),
                usuario.getApellido(),
                usuario.getMiembrosDelUsuario()
        );
    }

    /**
     * Convierte un DTO de creación en una entidad Usuario.
     */
    public static Usuario toEntity(UsuarioDtoPostRequest dto) {
        if (dto == null) return null;

        return new Usuario(
                dto.getEmail(),
                dto.getNombre(),
                dto.getApellido(),
                dto.getContraseña()
        );
    }

    /**
     * Actualiza una entidad Usuario existente con los valores del DTO de actualización.
     */
    public static void updateEntityFromDtoRes(UsuarioDtoUpdateRequest dto, Usuario usuario) {
        if (dto == null || usuario == null) return;

        if (dto.getEmail() != null) usuario.setEmail(dto.getEmail());
        if (dto.getNombre() != null) usuario.setNombre(dto.getNombre());
        if (dto.getApellido() != null) usuario.setApellido(dto.getApellido());
    }

    /**
     * Convierte una lista de entidades Usuario en una lista de DTOs de respuesta.
     */
    public static List<UsuarioDtoResponse> toDtoResponseListUsuario(List<Usuario> usuarios) {
        if (usuarios == null || usuarios.isEmpty()) return Collections.emptyList();

        List<UsuarioDtoResponse> lista = new ArrayList<>();
        for (Usuario usuario : usuarios) {
            lista.add(toDtoResponse(usuario));
        }
        return lista;
    }
}
