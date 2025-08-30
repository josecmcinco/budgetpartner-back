package com.budgetpartner.APP.dto.tarea;

import com.budgetpartner.APP.dto.plan.PlanDtoResponse;
import com.budgetpartner.APP.enums.EstadoTarea;
import com.budgetpartner.APP.enums.MonedasDisponibles;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class TareaDtoResponse {

    /*
        SE PRESCINDE DE LAS SIGUIENTES VARIABLES PARA EL DTO:
        creadoEn-actualizadoEn
    */
    private Long id;
    private PlanDtoResponse plan;
    private String titulo;
    private String descripcion;
    private LocalDateTime fechaFin;
    private EstadoTarea estado;

    public TareaDtoResponse(Long id, PlanDtoResponse plan, String titulo, String descripcion, LocalDateTime fechaFin, EstadoTarea estado) {
        this.id = id;
        this.plan = plan;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fechaFin = fechaFin;
        this.estado = estado;
    }

    public Long getId() {
        return id;
    }

    public PlanDtoResponse getPlan() {
        return plan;
    }

    public void setPlan(PlanDtoResponse plan) {
        this.plan = plan;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public LocalDateTime getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDateTime fechaFin) {
        this.fechaFin = fechaFin;
    }

    public EstadoTarea getEstado() {
        return estado;
    }

    public void setEstado(EstadoTarea estado) {
        this.estado = estado;
    }

}
