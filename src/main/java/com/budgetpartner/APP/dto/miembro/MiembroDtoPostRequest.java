package com.budgetpartner.APP.dto.miembro;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class MiembroDtoPostRequest {
    /*
        SE PRESCINDE DE LAS SIGUIENTES VARIABLES PARA EL DTO:
        id-creadoEn-actualizadoEn-usuarioOrigen
    */

    @NotNull(message = "El id de la organización no puede ser nulo")
    private Long organizacionId;
    @NotNull(message = "El rol del usuario no puede ser nulo")
    private Long rolId;
    @NotBlank(message = "El nick del miembro no puede ser nulo")
    private String nick;

    public MiembroDtoPostRequest(Long organizacionId, Long rolId, String nick) {
        this.organizacionId = organizacionId;
        this.rolId = rolId;
        this.nick = nick;
    }

    public Long getOrganizacionId() {
        return organizacionId;
    }

    public void setOrganizacionId(Long organizacionId) {
        this.organizacionId = organizacionId;}

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
