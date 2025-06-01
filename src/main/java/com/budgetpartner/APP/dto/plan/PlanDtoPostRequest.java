package com.budgetpartner.APP.dto.plan;

import java.time.LocalDateTime;

public class PlanDtoPostRequest {
    /*
        SE PRESCINDE DE LAS SIGUIENTES VARIABLES PARA EL DTO:
        id-creadoEn-actualizadoEn-id
    */

    private Long OrganizacionId;
    private String nombre;
    private String descripcion;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;

    public PlanDtoPostRequest(Long OrganizacionId, String nombre, String descripcion, LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        this.OrganizacionId = OrganizacionId;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }


    public Long getOrganizacionId() {
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
