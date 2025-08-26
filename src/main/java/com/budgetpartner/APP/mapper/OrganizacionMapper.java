package com.budgetpartner.APP.mapper;

import com.budgetpartner.APP.dto.organizacion.OrganizacionDtoPostRequest;
import com.budgetpartner.APP.dto.organizacion.OrganizacionDtoUpdateRequest;
import com.budgetpartner.APP.entity.Organizacion;
import com.budgetpartner.APP.dto.organizacion.OrganizacionDtoResponse;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class OrganizacionMapper {

    /**
     * Convierte una entidad Organizacion a su DTO de respuesta.
     */
    public static OrganizacionDtoResponse toDtoResponse(Organizacion organizacion) {
        if (organizacion == null) return null;

        return new OrganizacionDtoResponse(
                organizacion.getId(),
                organizacion.getNombre(),
                organizacion.getDescripcion(),
                organizacion.getMoneda()
        );
    }

    /**
     * Convierte un DTO de creación a una entidad Organizacion.
     */
    public static Organizacion toEntity(OrganizacionDtoPostRequest dto) {
        if (dto == null) return null;

        return new Organizacion(
                dto.getNombreOrganizacion(),
                dto.getDescripcionOrganizacion(),
                dto.getMoneda()
        );
    }

    /**
     * Actualiza una entidad Organizacion existente con los valores del DTO de actualización.
     */
    public static void updateEntityFromDtoRes(OrganizacionDtoUpdateRequest dto, Organizacion organizacion) {
        if (dto == null || organizacion == null) return;

        if (dto.getNombre() != null) organizacion.setNombre(dto.getNombre());
        if (dto.getDescripcion() != null) organizacion.setDescripcion(dto.getDescripcion());
        if (dto.getMoneda() != null) organizacion.setMoneda(dto.getMoneda());
    }

    /**
     * Convierte una lista de entidades Organizacion a una lista de DTOs de respuesta.
     */
    public static List<OrganizacionDtoResponse> toDtoResponseListOrganizacion(List<Organizacion> organizaciones) {
        if (organizaciones == null || organizaciones.isEmpty()) return Collections.emptyList();

        List<OrganizacionDtoResponse> lista = new ArrayList<>();
        for (Organizacion organizacion : organizaciones) {
            lista.add(toDtoResponse(organizacion));
        }
        return lista;
    }
}