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
    private ModoPlan modoPlan;

    public PlanDtoUpdateRequest(String nombre, String descripcion, LocalDateTime fechaInicio, LocalDateTime fechaFin, ModoPlan modoPlan) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.modoPlan = modoPlan;
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

    public ModoPlan getModoPlan() {
        return modoPlan;
    }

    public void setModoPlan(ModoPlan modoPlan) {
        this.modoPlan = modoPlan;
    }
}
