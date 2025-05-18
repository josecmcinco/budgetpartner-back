package com.budgetpartner.APP.mapper;

import com.budgetpartner.APP.entity.Miembro;
import com.budgetpartner.APP.dto.response.MiembroDtoResponse;
import jakarta.persistence.Entity;

public class MiembroMapper {

    // Convierte Miembro en MiembroDtoResponse
    public static MiembroDtoResponse toDto(Miembro miembro) {
        if (miembro == null) return null;

        return new MiembroDtoResponse(
                miembro.getId(),
                miembro.getUsuarioOrigen(),
                miembro.getOrganizacionOrigen(),
                miembro.getRolMiembro(),
                miembro.getNick(),
                miembro.getFechaIngreso(),
                miembro.getIsActivo()
        );
    }

    // Convierte MiembroDto to Miembro (OJO: no incluye fechas ni usuario si no viene en el DTO)
    public static Miembro toEntity(MiembroDtoResponse dto) {
        if (dto == null) return null;

        return new Miembro(
                dto.getOrganizacionOrigen(),
                dto.getRolMiembro(),
                dto.getNick()
        );
    }

    // Actualiza entidad existente con los valores del DTO
    public static void updateEntityFromDtoRes(MiembroDtoResponse dto, Miembro miembro) {
        if (dto == null || miembro == null) return;

        //NO SE PERMITE MODIFICAR DESDE EL DTO:
        //OrganizacionOrigen
        if (dto.getRolMiembro() != null) miembro.setRolMiembro(dto.getRolMiembro());
        if (dto.getNick() != null) miembro.setNick(dto.getNick());

        //TODO para cuando sepa como se invita a un usuario
        //Asociación del usuario de origen
        if(dto.getIsActivo() && !miembro.getIsActivo()){miembro.asociarUsuario(null);}
        else if(!dto.getIsActivo() && miembro.getIsActivo()){miembro.desasociarUsuario();}

    }
}
