package com.budgetpartner.APP.dto.plan;

import java.time.LocalDateTime;

public class PlanDtoUpdateRequest {
    /*
        SE PRESCINDE DE LAS SIGUIENTES VARIABLES PARA EL DTO:
        id-creadoEn-actualizadoEn-Organizacion
    */

    private Long id;
    private String nombre;
    private String descripcion;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;

    public PlanDtoUpdateRequest(Long id, String nombre, String descripcion, LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }

    //Métodos relacionados con el patron builder
    private PlanDtoUpdateRequest(Builder builder) {
        this.id = builder.id;
        this.nombre = builder.nombre;
        this.descripcion = builder.descripcion;
        this.fechaInicio = builder.fechaInicio;
        this.fechaFin = builder.fechaFin;
    }

    public static class Builder {
        private Long id;
        private String nombre;
        private String descripcion;
        private LocalDateTime fechaInicio;
        private LocalDateTime fechaFin;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder nombre(String nombre) {
            this.nombre = nombre;
            return this;
        }

        public Builder descripcion(String descripcion) {
            this.descripcion = descripcion;
            return this;
        }

        public Builder fechaInicio(LocalDateTime fechaInicio) {
            this.fechaInicio = fechaInicio;
            return this;
        }

        public Builder fechaFin(LocalDateTime fechaFin) {
            this.fechaFin = fechaFin;
            return this;
        }

        public PlanDtoUpdateRequest build() {
            return new PlanDtoUpdateRequest(this);
        }
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
