package com.budgetpartner.APP.dto.plan;

import com.budgetpartner.APP.enums.ModoPlan;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public class PlanDtoPostRequest {
    /*
        SE PRESCINDE DE LAS SIGUIENTES VARIABLES PARA EL DTO:
        id-creadoEn-actualizadoEn-id
    */

    @NotNull(message = "El ID de la organización no puede ser nulo")
    private Long organizacionId;

    @NotBlank(message = "El nombre no puede estar vacío")
    private String nombre;

    private String descripcion;

    private LocalDateTime fechaInicio;

    private LocalDateTime fechaFin;

    @NotNull(message = "El modo del plan no puede ser nulo")
    private ModoPlan modoPlan;

    private Double latitud;
    private Double longitud;

    public PlanDtoPostRequest(Long organizacionId, String nombre, String descripcion, LocalDateTime fechaInicio, LocalDateTime fechaFin, ModoPlan modoPlan, Double latitud, Double longitud) {
        this.organizacionId = organizacionId;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.modoPlan = modoPlan;
        this.latitud = latitud;
        this.longitud = longitud;
    }


    public Long getOrganizacionId() {
        return organizacionId;
    }

    public void setOrganizacionId(Long organizacionId) {
        this.organizacionId = organizacionId;
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

    public Double getLatitud() {
        return latitud;
    }

    public void setLatitud(Double latitud) {
        this.latitud = latitud;
    }

    public Double getLongitud() {
        return longitud;
    }

    public void setLongitud(Double longitud) {
        this.longitud = longitud;
    }


}
