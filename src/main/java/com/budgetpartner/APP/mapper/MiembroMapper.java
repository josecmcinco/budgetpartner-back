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

    // Convierte Miembro en MiembroDtoResponse
    public static MiembroDtoResponse toDtoResponse(Miembro miembro) {
        if (miembro == null) return null;

        return new MiembroDtoResponse(
                miembro.getId(),
                miembro.getRol(),
                miembro.getNick(),
                miembro.getFechaIngreso(),
                miembro.isAsociado(),
                miembro.isActivo()
        );
    }

    // Convierte MiembroDtoPostRequest to Miembro
    //No se hacen llamadas al servicio desde aquí
    public static Miembro toEntity(MiembroDtoPostRequest dto, Organizacion organizacion, Rol rol) {
        if (dto == null) return null;

        return new Miembro(
                organizacion,
                rol,
                dto.getNick()
        );
    }


    // Actualiza entidad existente con los valores del DTO
    public static void updateEntityFromDtoRes(MiembroDtoUpdateRequest dto, Miembro miembro, Rol rol) {
        if (dto == null || miembro == null) return;

        //NO SE PERMITE MODIFICAR DESDE EL DTO:
        //OrganizacionOrigen-isActivo-isAsignado
        if (dto.getRolId() != null) miembro.setRol(rol);
        if (dto.getNick() != null) miembro.setNick(dto.getNick());


    }

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
