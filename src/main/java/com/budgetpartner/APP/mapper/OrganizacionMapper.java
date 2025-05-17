package com.budgetpartner.APP.mapper;

import com.budgetpartner.APP.entity.Organizacion;
import com.budgetpartner.APP.dto.OrganizacionDto;

public class OrganizacionMapper {

    // Convierte Organizacion → OrganizacionDto
    public static OrganizacionDto toDto(Organizacion organizacion) {
        if (organizacion == null) return null;

        return new OrganizacionDto(
                organizacion.getId(),
                organizacion.getNombre(),
                organizacion.getDescripcion()
        );
    }

    // Convierte OrganizacionDto → Organizacion (OJO: no incluye fechas)
    public static Organizacion toEntity(OrganizacionDto dto) {
        if (dto == null) return null;

        Organizacion organizacion = new Organizacion(
                dto.getNombre(),
                dto.getDescripcion()
        );

        return organizacion;
    }

    // Actualiza una entidad Organizacion desde su DTO (sin tocar creadoEn, actualizadoEn)
    public static void updateEntityFromDto(OrganizacionDto dto, Organizacion organizacion) {
        if (dto == null || organizacion == null) return;

        if (dto.getNombre() != null) organizacion.setNombre(dto.getNombre());
        if (dto.getDescripcion() != null) organizacion.setDescripcion(dto.getDescripcion());
    }
}
