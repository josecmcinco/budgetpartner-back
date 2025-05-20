package com.budgetpartner.APP.dto.request;

public class RolDtoRequest {
    /*
        SE PRESCINDE DE LAS SIGUIENTES VARIABLES PARA EL DTO:
        id-creadoEn-actualizadoEn
    */

    private String nombre;
    private String permisos;

    public RolDtoRequest(String nombre, String permisos) {
        this.nombre = nombre;
        this.permisos = permisos;
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
