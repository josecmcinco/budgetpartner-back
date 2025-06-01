package com.budgetpartner.APP.dto.rol;

public class RolDtoUpdateRequest {
    /*
        SE PRESCINDE DE LAS SIGUIENTES VARIABLES PARA EL DTO:
        id-creadoEn-actualizadoEn
    */

    private Long id;
    private String nombre;
    private String permisos;

    public RolDtoUpdateRequest(Long id, String nombre, String permisos) {
        this.id = id;
        this.nombre = nombre;
        this.permisos = permisos;
    }

    public Long getId() {
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
