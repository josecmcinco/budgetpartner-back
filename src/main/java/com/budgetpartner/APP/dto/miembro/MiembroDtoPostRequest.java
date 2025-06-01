package com.budgetpartner.APP.dto.miembro;

import com.budgetpartner.APP.entity.Rol;

public class MiembroDtoPostRequest {
    /*
        SE PRESCINDE DE LAS SIGUIENTES VARIABLES PARA EL DTO:
        id-creadoEn-actualizadoEn-usuarioOrigen
    */

    private Long organizacionOrigenId;
    private Rol rolMiembro;
    private String nick;
    private boolean isActivo;

    public MiembroDtoPostRequest(Long usuarioOrigenId, Long organizacionOrigenId, Rol rolMiembro, String nick, boolean isActivo) {
        this.organizacionOrigenId = organizacionOrigenId;
        this.rolMiembro = rolMiembro;
        this.nick = nick;
        this.isActivo = isActivo;
    }

    public Long getOrganizacionOrigenId() {
        return organizacionOrigenId;
    }

    public void setOrganizacionOrigenId(Long organizacionOrigenId) {
        this.organizacionOrigenId = organizacionOrigenId;}

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
