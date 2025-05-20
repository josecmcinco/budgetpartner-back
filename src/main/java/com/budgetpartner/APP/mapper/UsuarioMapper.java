package com.budgetpartner.APP.mapper;

import com.budgetpartner.APP.dto.request.UsuarioDtoRequest;
import com.budgetpartner.APP.entity.Usuario;
import com.budgetpartner.APP.dto.response.UsuarioDtoResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UsuarioMapper {

    // Convierte Usuario en UsuarioDtoResponse
    public static UsuarioDtoResponse toDtoResponse(Usuario usuario) {

        return new UsuarioDtoResponse(
                usuario.getId(),
                usuario.getEmail(),
                usuario.getNombre(),
                usuario.getApellido(),
                usuario.getMiembrosDelUsuario()
        );
    }

    // Convierte UsuarioDtoRequest en Usuario
    public static Usuario toEntity(UsuarioDtoRequest dto) {
        if (dto == null) return null;

        return new Usuario(
            dto.getEmail(),
            dto.getNombre(),
            dto.getApellido(),
            //TODO QUE HAGO CON LA CONTRASEÑA????
            null
        );
    }

    //Actualiza entidad existente con los valores del DTO
    public static void updateEntityFromDtoRes(UsuarioDtoRequest dto, Usuario usuario) {
        if (dto == null || usuario == null) return;

        if (dto.getEmail() != null) usuario.setEmail(dto.getEmail());
        if (dto.getNombre() != null) usuario.setNombre(dto.getNombre());
        if (dto.getApellido() != null) usuario.setApellido(dto.getApellido());
    }
    public static List<UsuarioDtoResponse> toDtoResponseListUsuario(List<Usuario> usuarios) {
        List<UsuarioDtoResponse> listaUsuariosDtoResp = new ArrayList<>();
        if (usuarios == null || usuarios.isEmpty()) {
            return Collections.emptyList();
        } else {
            for (Usuario usuario : usuarios) {
                UsuarioDtoResponse usuarioDtoResp = UsuarioMapper.toDtoResponse(usuario);
                listaUsuariosDtoResp.add(usuarioDtoResp);
            }
            return listaUsuariosDtoResp;
        }
    }
}
