package com.budgetpartner.APP.dto.response;

public class RolDtoResponse {
    /*
        SE PRESCINDE DE LAS SIGUIENTES VARIABLES PARA EL DTO:
        creadoEn-actualizadoEn
    */

    private int id;
    private String nombre;
    private String permisos;

    public RolDtoResponse(int id, String nombre, String permisos) {
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
