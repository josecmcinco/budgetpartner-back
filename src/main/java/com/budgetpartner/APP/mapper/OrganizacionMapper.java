package com.budgetpartner.APP.mapper;

import com.budgetpartner.APP.dto.request.OrganizacionDtoRequest;
import com.budgetpartner.APP.entity.Organizacion;
import com.budgetpartner.APP.dto.response.OrganizacionDtoResponse;

public class OrganizacionMapper {

    // Convierte Organizacion en OrganizacionDtoResponse
    public static OrganizacionDtoResponse toDtoResponse(Organizacion organizacion) {
        if (organizacion == null) return null;

        return new OrganizacionDtoResponse(
                organizacion.getId(),
                organizacion.getNombre(),
                organizacion.getDescripcion()
        );
    }

    // Convierte OrganizacionDtoRequest en Organizacion
    public static Organizacion toEntity(OrganizacionDtoRequest dto) {
        if (dto == null) return null;

        return new Organizacion(
                dto.getNombre(),
                dto.getDescripcion()
        );
    }

    // Actualiza entidad existente con los valores del DTO
    public static void updateEntityFromDtoRes(OrganizacionDtoRequest dto, Organizacion organizacion) {
        if (dto == null || organizacion == null) return;

        if (dto.getNombre() != null) organizacion.setNombre(dto.getNombre());
        if (dto.getDescripcion() != null) organizacion.setDescripcion(dto.getDescripcion());
    }
}
