package com.budgetpartner.APP.dto.response;

import com.budgetpartner.APP.entity.Organizacion;
import com.budgetpartner.APP.entity.Rol;
import com.budgetpartner.APP.entity.Usuario;

import java.time.LocalDateTime;

public class MiembroDtoResponse {
    /*
        SE PRESCINDE DE LAS SIGUIENTES VARIABLES PARA EL DTO:
        creadoEn-actualizadoEn
    */

    private Long id;
    private Usuario usuarioOrigen;
    private Organizacion organizacionOrigen;
    private Rol rolMiembro;
    private String nick;
    private LocalDateTime fechaIngreso;
    private boolean isActivo;

    public MiembroDtoResponse(Long id, Usuario usuarioOrigen, Organizacion organizacionOrigen, Rol rolMiembro, String nick, LocalDateTime fechaIngreso, boolean isActivo) {
        this.id = id;
        this.usuarioOrigen = usuarioOrigen;
        this.organizacionOrigen = organizacionOrigen;
        this.rolMiembro = rolMiembro;
        this.nick = nick;
        this.fechaIngreso = fechaIngreso;
        this.isActivo = isActivo;
    }

    public Long getId() {
        return id;
    }

    public Usuario getUsuarioOrigen() {
        return usuarioOrigen;
    }

    public void setUsuarioOrigen(Usuario usuarioOrigen) {
        this.usuarioOrigen = usuarioOrigen;
    }

    public Organizacion getOrganizacionOrigen() {
        return organizacionOrigen;
    }

    public void setOrganizacionOrigen(Organizacion organizacionOrigen) {
        this.organizacionOrigen = organizacionOrigen;
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

    public LocalDateTime getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(LocalDateTime fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public boolean getIsActivo() {
        return isActivo;
    }

    public void setIsActivo(boolean activo) {
        isActivo = activo;
    }
}
