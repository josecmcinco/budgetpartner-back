package com.budgetpartner.APP.dto.request;

import com.budgetpartner.APP.entity.Organizacion;

import java.time.LocalDateTime;

public class PlanDtoRequest {
    /*
        SE PRESCINDE DE LAS SIGUIENTES VARIABLES PARA EL DTO:
        id-creadoEn-actualizadoEn
    */

    private Long OrganizacionId;
    private String nombre;
    private String descripcion;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;

    public PlanDtoRequest(Long OrganizacionId, String nombre, String descripcion, LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        this.OrganizacionId = OrganizacionId;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }


    public Long getOrganizacion() {
        return OrganizacionId;
    }

    public void setOrganizacionId(Long OrganizacionId) {
        this.OrganizacionId = OrganizacionId;
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

    public LocalDateTime getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDateTime fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDateTime getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDateTime fechaFin) {
        this.fechaFin = fechaFin;
    }
}
