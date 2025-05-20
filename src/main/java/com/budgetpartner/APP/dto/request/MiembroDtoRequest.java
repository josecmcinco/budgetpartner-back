package com.budgetpartner.APP.dto.request;

import com.budgetpartner.APP.entity.Organizacion;
import com.budgetpartner.APP.entity.Rol;
import com.budgetpartner.APP.entity.Usuario;

import java.time.LocalDateTime;

public class MiembroDtoRequest {
    /*
        SE PRESCINDE DE LAS SIGUIENTES VARIABLES PARA EL DTO:
        id-creadoEn-actualizadoEn
    */

    //TODO ELIMINAR ISACTIVO???
    private Long usuarioOrigenId;
    private Long organizacionOrigenId;
    private Rol rolMiembro;
    private String nick;
    private LocalDateTime fechaIngreso;
    private boolean isActivo;

    public MiembroDtoRequest(Long usuarioOrigenId, Long organizacionOrigenId, Rol rolMiembro, String nick, boolean isActivo) {
        this.usuarioOrigenId = usuarioOrigenId;
        this.organizacionOrigenId = organizacionOrigenId;
        this.rolMiembro = rolMiembro;
        this.nick = nick;
        this.isActivo = isActivo;
    }


    public Long getUsuarioOrigenId() {
        return usuarioOrigenId;
    }

    public void setUsuarioOrigen(Long usuarioOrigenId) {
        this.usuarioOrigenId = usuarioOrigenId;
    }

    public Long getOrganizacionOrigenId() {
        return organizacionOrigenId;
    }

    public void setOrganizacionOrigenId(Long organizacionOrigenId) {
        this.organizacionOrigenId = organizacionOrigenId;
    }

    public Rol getRolMiembro() {
        return rolMiembro;
    }

    public void setRolMiembro(Rol rolMiembro) {
        this.rolMiembro = rolMiembro;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public boolean getIsActivo() {
        return isActivo;
    }

    public void setIsActivo(boolean activo) {
        isActivo = activo;
    }
}
