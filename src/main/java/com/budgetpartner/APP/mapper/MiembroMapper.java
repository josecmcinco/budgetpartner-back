package com.budgetpartner.APP.mapper;

import com.budgetpartner.APP.entity.Miembro;
import com.budgetpartner.APP.dto.MiembroDto;

public class MiembroMapper {

    // Convierte Miembro → MiembroDto
    public static MiembroDto toDto(Miembro miembro) {
        if (miembro == null) return null;

        return new MiembroDto(
                miembro.getId(),
                miembro.getUsuarioOrigen(),
                miembro.getOrganizacionOrigen(),
                miembro.getRolMiembro(),
                miembro.getNick(),
                miembro.getFechaIngreso(),
                miembro.getIsActivo()
        );
    }

    // Convierte MiembroDto → Miembro (OJO: no incluye fechas ni usuario si no viene en el DTO)
    public static Miembro toEntity(MiembroDto dto) {
        if (dto == null) return null;

        Miembro miembro = new Miembro(
                dto.getOrganizacionOrigen(),
                dto.getRolMiembro(),
                dto.getNick()
        );

        //miembro.setId(dto.getId());
        //miembro.setUsuarioOrigen(dto.getUsuarioOrigen());
        //miembro.setFechaIngreso(dto.getFechaIngreso());
        //miembro.setActivo(dto.isActivo());

        return miembro;
    }

    // Actualiza un Miembro desde su DTO (no toca fechas automáticas)
    public static void updateEntityFromDto(MiembroDto dto, Miembro miembro) {
        if (dto == null || miembro == null) return;

        //TODO ERROR POR CAMBIOS RAROS (VER COMENTADOS)
        //if (dto.getUsuarioOrigen() != null) miembro.setUsuarioOrigen(dto.getUsuarioOrigen());
        //if (dto.getOrganizacionOrigen() != null) miembro.setOrganizacionOrigen(dto.getOrganizacionOrigen());
        if (dto.getRolMiembro() != null) miembro.setRolMiembro(dto.getRolMiembro());
        if (dto.getNick() != null) miembro.setNick(dto.getNick());

        //TODO para cuando sepa como se invita a un usuario
        if(dto.getIsActivo() && !miembro.getIsActivo()){miembro.asociarUsuario(null);}

        if(!dto.getIsActivo() && miembro.getIsActivo()){miembro.desasociarUsuario();}

    }
}
