package com.budgetpartner.APP.dto.organizacion;

import com.budgetpartner.APP.enums.MonedasDisponibles;

public class OrganizacionDtoUpdateRequest {
    /*
        SE PRESCINDE DE LAS SIGUIENTES VARIABLES PARA EL DTO:
        id-creadoEn-actualizadoEn
    */
    private String nombre;
    private String descripcion;
    private MonedasDisponibles moneda;

    public OrganizacionDtoUpdateRequest(String nombre, String descripcion, MonedasDisponibles moneda) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.moneda = moneda;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public MonedasDisponibles getMoneda() {
        return moneda;
    }

    public void setMoneda(MonedasDisponibles moneda) {
        this.moneda = moneda;
    }
}
