package com.budgetpartner.APP.mapper;

import com.budgetpartner.APP.entity.Rol;
import com.budgetpartner.APP.dto.response.RolDtoResponse;

public class RolMapper {

    // Convierte Rol en RolDto
    public static RolDtoResponse toDto(Rol rol) {
        if (rol == null) return null;

        return new RolDtoResponse(
                rol.getId(),
                rol.getNombre(),
                rol.getPermisos()
        );
    }

    // Convierte RolDto en Rol
    public static Rol toEntity(RolDtoResponse dto) {
        if (dto == null) return null;

        return new Rol(
                dto.getNombre(),
                dto.getPermisos()
        );
    }

    // Actualiza entidad existente con los valores del DTO
    public static void updateEntityFromDtoRes(RolDtoResponse dto, Rol rol) {
        if (dto == null || rol == null) return;

        if (dto.getNombre() != null) rol.setNombre(dto.getNombre());
        if (dto.getPermisos() != null) rol.setPermisos(dto.getPermisos());
    }
}
