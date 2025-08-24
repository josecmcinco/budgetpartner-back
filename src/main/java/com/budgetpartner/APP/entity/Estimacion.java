package com.budgetpartner.APP.entity;

import com.budgetpartner.APP.enums.MonedasDisponibles;
import com.budgetpartner.APP.enums.TipoEstimacion;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table
public class Estimacion {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "plan_id")
    private Plan plan;

    @ManyToOne
    @JoinColumn(name= "tarea_id")
    private Tarea tarea;

    @ManyToOne
    @JoinColumn(name= "miembro_creador_id")
    private Miembro creador;

    @Column
    private double cantidad;

    @Enumerated(EnumType.STRING)
    private TipoEstimacion tipoEstimacion;

    @Enumerated(EnumType.STRING)
    private MonedasDisponibles tipoMoneda;

    @Column
    private String descripcion;

    @Column
    private LocalDateTime creadoEn;

    @Column
    private LocalDateTime actualizadoEn;

    @ManyToOne
    @JoinColumn(name= "miembro_pagador_id")
    private Miembro pagador;

    @ManyToOne
    @JoinColumn(name= "gasto_id")
    private Gasto gasto;

    //Constructor vacío para Hibernate
    public Estimacion(){}

    //Creacion de Gasto desde 0


    public Estimacion(Plan plan, Tarea tarea, Miembro creador, double cantidad, TipoEstimacion tipoEstimacion, MonedasDisponibles tipoMoneda, String descripcion, Miembro pagador, Gasto gasto) {
        this.plan = plan;
        this.tarea = tarea;
        this.creador = creador;
        this.cantidad = cantidad;
        this.tipoEstimacion = tipoEstimacion;
        this.tipoMoneda = tipoMoneda;
        this.descripcion = descripcion;
        this.pagador = pagador;
        this.gasto = gasto;

        //Generado automáticamente
        this.creadoEn = LocalDateTime.now();
        this.actualizadoEn = LocalDateTime.now();
    }



    //Extraer Pago de la DB
    public Estimacion(Long id, Plan plan, Tarea tarea, Miembro creador, double cantidad, TipoEstimacion tipoEstimacion, MonedasDisponibles tipoMoneda, String descripcion, LocalDateTime creadoEn, LocalDateTime actualizadoEn, Miembro pagador, Gasto gasto) {
        this.id = id;
        this.plan = plan;
        this.tarea = tarea;
        this.creador = creador;
        this.cantidad = cantidad;
        this.tipoEstimacion = tipoEstimacion;
        this.tipoMoneda = tipoMoneda;
        this.descripcion = descripcion;
        this.creadoEn = creadoEn;
        this.actualizadoEn = actualizadoEn;
        this.pagador = pagador;
        this.gasto = gasto;
    }


    public Long getId() {
        return id;
    }

    public Plan getPlan() {
        return plan;
    }

    public Tarea getTarea() {
        return tarea;
    }

    public Miembro getCreador() {
        return creador;
    }

    public double getCantidad() {
        return cantidad;
    }

    public MonedasDisponibles getTipoMoneda() {
        return tipoMoneda;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public LocalDateTime getCreadoEn() {
        return creadoEn;
    }

    public LocalDateTime getActualizadoEn() {
        return actualizadoEn;
    }

    public Miembro getPagador() {
        return pagador;
    }

    public Gasto getGasto() {
        return gasto;
    }

    public void setPlan(Plan plan) {
        this.plan = plan;
    }

    public void setTarea(Tarea tarea) {
        this.tarea = tarea;
    }

    public void setTipoEstimacion(TipoEstimacion tipoEstimacion) {
        this.tipoEstimacion = tipoEstimacion;
    }

    public void setCreador(Miembro creador) {
        this.creador = creador;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }

    public void setTipoMoneda(MonedasDisponibles tipoMoneda) {
        this.tipoMoneda = tipoMoneda;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setCreadoEn(LocalDateTime creadoEn) {
        this.creadoEn = creadoEn;
    }

    public void setActualizadoEn(LocalDateTime actualizadoEn) {
        this.actualizadoEn = actualizadoEn;
    }

    public void setPagador(Miembro pagador) {
        this.pagador = pagador;
    }

    public void setGasto(Gasto gasto) {
        this.gasto = gasto;
    }

    public TipoEstimacion getTipoEstimacion() {
        return tipoEstimacion;
    }
}
