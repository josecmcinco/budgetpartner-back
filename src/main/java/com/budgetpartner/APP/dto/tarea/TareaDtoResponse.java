package com.budgetpartner.APP.dto.tarea;

import com.budgetpartner.APP.dto.plan.PlanDtoResponse;
import com.budgetpartner.APP.entity.Plan;
import com.budgetpartner.APP.enums.EstadoTarea;
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
    private double costeEstimado;
    private String moneda;

    public TareaDtoResponse(Long id, PlanDtoResponse plan, String titulo, String descripcion, LocalDateTime fechaFin, EstadoTarea estado, double costeEstimado, String moneda) {
        this.id = id;
        this.plan = plan;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fechaFin = fechaFin;
        this.estado = estado;
        this.costeEstimado = costeEstimado;
        this.moneda = moneda;
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

    public double getCosteEstimado() {
        return costeEstimado;
    }

    public void setCosteEstimado(double costeEstimado) {
        this.costeEstimado = costeEstimado;
    }

    public String getMoneda() {
        return moneda;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }
}
