package com.budgetpartner.APP.mapper;

import com.budgetpartner.APP.dto.organizacion.OrganizacionDtoPostRequest;
import com.budgetpartner.APP.dto.organizacion.OrganizacionDtoUpdateRequest;
import com.budgetpartner.APP.entity.Organizacion;
import com.budgetpartner.APP.dto.organizacion.OrganizacionDtoResponse;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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


    //Obtener Entity desde OrganizacionDtoPostRequest
    //No se hacen llamadas al servicio desde aquí
    public static Organizacion toEntity(OrganizacionDtoPostRequest dto) {
        if (dto == null) return null;

        return new Organizacion(
                dto.getNombreOrganizacion(),
                dto.getDescripcionOrganizacion()
        );
    }


    // Actualiza entidad existente con los valores del DTO
    public static void updateEntityFromDtoRes(OrganizacionDtoUpdateRequest dto, Organizacion organizacion) {
        if (dto == null || organizacion == null) return;

        if (dto.getNombre() != null) organizacion.setNombre(dto.getNombre());
        if (dto.getDescripcion() != null) organizacion.setDescripcion(dto.getDescripcion());
    }

    public static List<OrganizacionDtoResponse> toDtoResponseListOrganizacion(List<Organizacion> organizaciones) {
        List<OrganizacionDtoResponse> listaOrganizacionesDtoResp = new ArrayList<>();
        if (organizaciones == null || organizaciones.isEmpty()) {
            return Collections.emptyList();
        } else {
            for (Organizacion organizacion : organizaciones) {
                OrganizacionDtoResponse organizacionDtoResp = OrganizacionMapper.toDtoResponse(organizacion);
                listaOrganizacionesDtoResp.add(organizacionDtoResp);
            }
            return listaOrganizacionesDtoResp;
        }
    }


}
