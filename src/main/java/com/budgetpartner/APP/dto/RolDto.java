package com.budgetpartner.APP.dto;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

public class RolDto {
    /*
        SE PRESCINDE DE LAS SIGUIENTES VARIABLES PARA EL DTO:
        creadoEn-actualizadoEn
    */

    private int id;
    private String nombre;
    private String permisos;

    public RolDto(int id, String nombre, String permisos) {
        this.id = id;
        this.nombre = nombre;
        this.permisos = permisos;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPermisos() {
        return permisos;
    }

    public void setPermisos(String permisos) {
        this.permisos = permisos;
    }
}
