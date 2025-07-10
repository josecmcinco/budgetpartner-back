package com.budgetpartner.APP.dto.miembro;

import com.budgetpartner.APP.dto.organizacion.OrganizacionDtoResponse;
import com.budgetpartner.APP.entity.Rol;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class MiembroDtoResponse {
    /*
        SE PRESCINDE DE LAS SIGUIENTES VARIABLES PARA EL DTO:
        creadoEn-actualizadoEn
    */

    private Long id;
    private OrganizacionDtoResponse organizacion;
    private Rol rol;
    private String nick;
    private LocalDateTime fechaIngreso;
    private boolean isAsociado;
    private boolean isActivo;

    public MiembroDtoResponse(Long id, Rol rol, String nick, LocalDateTime fechaIngreso, boolean isAsociado, boolean isActivo) {
        this.id = id;
        this.rol = rol;
        this.nick = nick;
        this.fechaIngreso = fechaIngreso;
        this.isAsociado = isAsociado;
        this.isActivo = isActivo;
    }


    public Long getId() {
        return id;
    }

    public OrganizacionDtoResponse getOrganizacion() {
        return organizacion;
    }

    public void setOrganizacion(OrganizacionDtoResponse organizacion) {this.organizacion = organizacion;}

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public LocalDateTime getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(LocalDateTime fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public boolean getIsAsociado() {
        return isAsociado;
    }

    public void setIsAsociado(boolean asociado) {
        isAsociado = asociado;
    }

    public boolean getisActivo() {
        return isActivo;
    }

    public void setisActivo(boolean isActivo) {
        this.isActivo = isActivo;
    }
}
