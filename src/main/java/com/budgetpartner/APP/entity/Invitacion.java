package com.budgetpartner.APP.entity;

import com.budgetpartner.APP.service.InvitacionService;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table
public class Invitacion {

    @Id
    private String token;

    @ManyToOne
    private Organizacion organizacion;

    @Column
    private LocalDateTime fechaCreacion;

    @Column
    private boolean isActiva;

    //Constructor vacío para Hibernate
    public Invitacion(){}

    public Invitacion(String token, Organizacion organizacion) {
        this.token = token;
        this.organizacion = organizacion;

        //Generado automáticamente
        this.fechaCreacion = LocalDateTime.now();
        this.isActiva = true;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Organizacion getOrganizacion() {
        return organizacion;
    }

    public void setOrganizacion(Organizacion organizacion) {
        this.organizacion = organizacion;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public boolean isActiva() {
        return isActiva;
    }

    public void setActiva(boolean activa) {
        isActiva = activa;
    }
}
