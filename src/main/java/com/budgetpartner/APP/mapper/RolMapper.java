package com.budgetpartner.APP.mapper;

import com.budgetpartner.APP.entity.Rol;
import com.budgetpartner.APP.dto.RolDto;

public class RolMapper {

    // Convierte Rol → RolDto
    public static RolDto toDto(Rol rol) {
        if (rol == null) return null;

        return new RolDto(
                rol.getId(),
                rol.getNombre(),
                rol.getPermisos()
        );
    }

    // Convierte RolDto → Rol (sin fechas)
    public static Rol toEntity(RolDto dto) {
        if (dto == null) return null;

        Rol rol = new Rol(
                dto.getNombre(),
                dto.getPermisos()
        );

        return rol;
    }

    // Actualiza Rol con los valores del DTO sin tocar fechas
    public static void updateEntityFromDto(RolDto dto, Rol rol) {
        if (dto == null || rol == null) return;

        if (dto.getNombre() != null) rol.setNombre(dto.getNombre());
        if (dto.getPermisos() != null) rol.setPermisos(dto.getPermisos());
    }
}
