package com.budgetpartner.APP.dto.miembro;

public class MiembroDtoUpdateRequest {
    /*
        SE PRESCINDE DE LAS SIGUIENTES VARIABLES PARA EL DTO:
        id-creadoEn-actualizadoEn
    */
    private Long usuarioId;
    private Long rolId;
    private String nick;
    private boolean isActivo;

    public MiembroDtoUpdateRequest(Long usuarioId, Long rolId, String nick, boolean isActivo) {
        this.usuarioId = usuarioId;
        this.rolId = rolId;
        this.nick = nick;
        this.isActivo = isActivo;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public Long getRolId() {
        return rolId;
    }

    public void setRolId(Long rolId) {
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
