package com.budgetpartner.APP.mapper;

import com.budgetpartner.APP.dto.miembro.MiembroDtoPostRequest;
import com.budgetpartner.APP.dto.miembro.MiembroDtoResponse;
import com.budgetpartner.APP.dto.miembro.MiembroDtoUpdateRequest;
import com.budgetpartner.APP.entity.Miembro;
import com.budgetpartner.APP.entity.Organizacion;
import com.budgetpartner.APP.entity.Rol;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MiembroMapper {

    /**
     * Convierte una entidad Miembro a su DTO de respuesta.
     */
    public static MiembroDtoResponse toDtoResponse(Miembro miembro) {
        if (miembro == null) return null;

        return new MiembroDtoResponse(
                miembro.getId(),
                miembro.getRol().getId(),
                miembro.getNick(),
                miembro.getFechaIngreso(),
                miembro.isAsociado(),
                miembro.isActivo()
        );
    }


    /**
     * Convierte un DTO de creación a una entidad Miembro.
     */
    public static Miembro toEntity(MiembroDtoPostRequest dto, Organizacion organizacion, Rol rol) {
        if (dto == null) return null;

        return new Miembro(
                organizacion,
                rol,
                dto.getNick()
        );
    }


    /**
     * Actualiza una entidad Miembro existente con los valores del DTO de actualización.
     * No se permite modificar ciertos campos como: organizacion, isActivo, isAsociado desde el DTO.
     */
    public static void updateEntityFromDtoRes(MiembroDtoUpdateRequest dto, Miembro miembro, Rol rol) {
        if (dto == null || miembro == null) return;

        if (dto.getRolId() != null) miembro.setRol(rol);
        if (dto.getNick() != null) miembro.setNick(dto.getNick());


    }


    /**
     * Convierte una lista de entidades Miembro a una lista de DTOs de respuesta.
     */
    public static List<MiembroDtoResponse> toDtoResponseListMiembro(List<Miembro> miembros) {
        ArrayList<MiembroDtoResponse> listaMiembrosDtoResp = new ArrayList<>();
        if (miembros.isEmpty()) {
            return Collections.emptyList();
        } else {
            for (Miembro miembro : miembros) {
                MiembroDtoResponse miembroDtoResp = MiembroMapper.toDtoResponse(miembro);
                listaMiembrosDtoResp.add(miembroDtoResp);
            }
            return listaMiembrosDtoResp;}
    }
}
