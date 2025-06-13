package com.budgetpartner.APP.mapper;

import com.budgetpartner.APP.dto.miembro.MiembroDtoPostRequest;
import com.budgetpartner.APP.dto.miembro.MiembroDtoResponse;
import com.budgetpartner.APP.dto.miembro.MiembroDtoUpdateRequest;
import com.budgetpartner.APP.entity.Miembro;
import com.budgetpartner.APP.entity.Organizacion;
import com.budgetpartner.APP.entity.Rol;
import com.budgetpartner.APP.entity.Usuario;

import java.time.LocalDateTime;
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
                miembro.getIsActivo()
        );
    }

    // Convierte MiembroDtoPostRequest to Miembro
    //No se hacen llamadas al servicio desde aquí
    public static Miembro toEntity(MiembroDtoPostRequest dto, Organizacion organizacion, Rol rol) {
        if (dto == null) return null;

        return new Miembro(
                organizacion,
                rol,
                dto.getNick(),
                dto.getIsActivo()
        );
    }

    // Convierte MiembroDtoUpdateRequest to Miembro
    //No se hacen llamadas al servicio desde aquí
    public static Miembro toEntity(MiembroDtoUpdateRequest dto, Usuario usuario, Organizacion organizacion, Rol rol) {
        if (dto == null) return null;

        return new Miembro(
                dto.getId(),
                usuario,
                organizacion,
                rol,
                dto.getNick(),
                LocalDateTime.now(),
                dto.getIsActivo(),
                LocalDateTime.now(),
                LocalDateTime.now()
        );
    }

    // Actualiza entidad existente con los valores del DTO
    public static void updateEntityFromDtoRes(MiembroDtoUpdateRequest dto, Miembro miembro) {
        if (dto == null || miembro == null) return;

        //NO SE PERMITE MODIFICAR DESDE EL DTO:
        //OrganizacionOrigen
        if (dto.getRolMiembro() != null) miembro.setRol(new Rol()); //TODO
        if (dto.getNick() != null) miembro.setNick(dto.getNick());

        //TODO para cuando sepa como se invita a un usuario
        //Asociación del usuario de origen
        if(dto.getIsActivo() && !miembro.getIsActivo()){miembro.asociarUsuario(null);}
        else if(!dto.getIsActivo() && miembro.getIsActivo()){miembro.desasociarUsuario();}

    }

    public static List<MiembroDtoResponse> toDtoResponseListMiembro(List<Miembro> miembros) {
        ArrayList<MiembroDtoResponse> listaMiembrosDtoResp = new ArrayList<MiembroDtoResponse>();
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
