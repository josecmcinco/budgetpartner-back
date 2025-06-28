package com.budgetpartner.APP.dto.tarea;

import com.budgetpartner.APP.enums.EstadoTarea;
import com.budgetpartner.APP.enums.MonedasDisponibles;

import java.time.LocalDateTime;

public class TareaDtoUpdateRequest {

    /*
        SE PRESCINDE DE LAS SIGUIENTES VARIABLES PARA EL DTO:
        id-creadoEn-actualizadoEn
    */
    private Long id;
    private String titulo;
    private String descripcion;
    private LocalDateTime fechaFin;
    private EstadoTarea estado;
    private double costeEstimado;
    private MonedasDisponibles moneda;

    public TareaDtoUpdateRequest(String titulo, String descripcion, LocalDateTime fechaFin, EstadoTarea estado, double costeEstimado, MonedasDisponibles moneda) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fechaFin = fechaFin;
        this.estado = estado;
        this.costeEstimado = costeEstimado;
        this.moneda = moneda;
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

    public MonedasDisponibles getMoneda() {
        return moneda;
    }

    public void setMoneda(MonedasDisponibles moneda) {
        this.moneda = moneda;
    }
}
