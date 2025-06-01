package com.budgetpartner.APP.dto.miembro;

import com.budgetpartner.APP.entity.Rol;

public class MiembroDtoUpdateRequest {
    /*
        SE PRESCINDE DE LAS SIGUIENTES VARIABLES PARA EL DTO:
        id-creadoEn-actualizadoEn
    */
    private Long id;
    private Long usuarioOrigenId;
    private Long rolId;
    private String nick;
    private boolean isActivo;

    public MiembroDtoUpdateRequest(Long id, Long usuarioOrigenId, Long rolId, String nick, boolean isActivo) {
        this.id = id;
        this.usuarioOrigenId = usuarioOrigenId;
        this.rolId = rolId;
        this.nick = nick;
        this.isActivo = isActivo;
    }

    public Long getId() {
        return id;
    }

    public Long getUsuarioOrigenId() {
        return usuarioOrigenId;
    }

    public void setUsuarioOrigenId(Long usuarioOrigenId) {
        this.usuarioOrigenId = usuarioOrigenId;
    }

    public Long getRolMiembro() {
        return rolId;
    }

    public void setRolMiembro(Long rolId) {
        this.rolId = rolId;
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
