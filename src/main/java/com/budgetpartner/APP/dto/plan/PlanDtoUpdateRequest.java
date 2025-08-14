package com.budgetpartner.APP.dto.plan;

import com.budgetpartner.APP.enums.ModoPlan;

import java.time.LocalDateTime;

public class PlanDtoUpdateRequest {
    /*
        SE PRESCINDE DE LAS SIGUIENTES VARIABLES PARA EL DTO:
        id-creadoEn-actualizadoEn-Organizacion
    */

    private String nombre;
    private String descripcion;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
    private Double latitud;
    private Double longitud;

    public PlanDtoUpdateRequest(String nombre, String descripcion, LocalDateTime fechaInicio, LocalDateTime fechaFin, Double latitud, Double longitud) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.latitud = latitud;
        this.longitud = longitud;
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

    public Double getLongitud() {
        return longitud;
    }

    public void setLongitud(Double longitud) {
        this.longitud = longitud;
    }

    public Double getLatitud() {
        return latitud;
    }

    public void setLatitud(Double latitud) {
        this.latitud = latitud;
    }
}
