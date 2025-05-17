package com.budgetpartner.APP.mapper;

import com.budgetpartner.APP.entity.Usuario;
import com.budgetpartner.APP.dto.UsuarioDto;

public class UsuarioMapper {
    // Convierte Usuario → UsuarioDto
    public static UsuarioDto toDto(Usuario usuario) {
        if (usuario == null) return null;

        /*TODO ELIMINAR SI ES NECESARIO
        List<String> nombresMiembros = null;
        if (usuario.getMiembrosDelUsuario() != null) {
            nombresMiembros = usuario.getMiembrosDelUsuario().stream()
                    .map(Miembro::getNombre) // o cualquier campo identificativo
                    .collect(Collectors.toList());
        }*/

        return new UsuarioDto(
                usuario.getId(),
                usuario.getEmail(),
                usuario.getNombre(),
                usuario.getApellido(),
                usuario.getMiembrosDelUsuario()
        );
    }

    // Convierte UsuarioDto → Usuario (OJO: no incluye contraseña ni fechas)
    public static Usuario toEntity(UsuarioDto dto) {
        if (dto == null) return null;

        Usuario usuario = new Usuario(
            dto.getEmail(),
            dto.getNombre(),
            dto.getApellido(),
            null
        );
        return usuario;
    }

    //Combinación de los campos actualizados de Dto con los restantes de Usuario
    public static void updateEntityFromDto(UsuarioDto dto, Usuario usuario) {

        //setActualizadoEn gestionado automáticamente

        if (dto.getEmail() != null) usuario.setEmail(dto.getEmail());
        if (dto.getNombre() != null) usuario.setNombre(dto.getNombre());
        if (dto.getApellido() != null) usuario.setApellido(dto.getApellido());
    }
}
