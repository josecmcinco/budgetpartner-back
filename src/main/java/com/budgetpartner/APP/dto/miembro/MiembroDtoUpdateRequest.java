package com.budgetpartner.APP.dto.miembro;

public class MiembroDtoUpdateRequest {
    /*
        SE PRESCINDE DE LAS SIGUIENTES VARIABLES PARA EL DTO:
        id-creadoEn-actualizadoEn
    */
    private Long usuarioId;
    private Long rolId;
    private String nick;

    public MiembroDtoUpdateRequest(Long usuarioId, Long rolId, String nick) {
        this.usuarioId = usuarioId;
        this.rolId = rolId;
        this.nick = nick;
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


}
