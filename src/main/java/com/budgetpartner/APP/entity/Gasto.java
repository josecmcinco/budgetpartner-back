package com.budgetpartner.APP.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
public class Gasto {
    @Id
    @GeneratedValue
    public Long id;

    @ManyToOne
    @JoinColumn(name= "tarea_id")
    private Tarea tarea;

    @ManyToOne
    @JoinColumn(name = "plan_id")
    private Plan plan;

    @OneToMany(mappedBy = "gasto", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Estimacion> estimaciones = new ArrayList<>();

    @Column
    private double cantidad;

    @Column
    private String nombre;

    @ManyToOne
    @JoinColumn(name= "miembro_pagador_id")
    private Miembro pagador;

    @Column
    private String descripcion;

    @Column
    private LocalDateTime creadoEn;

    @Column
    private LocalDateTime actualizadoEn;

    //Constructor vacío para Hibernate
    public Gasto(){}

    //Creacion de Gasto desde 0

    public Gasto(Tarea tarea, Plan plan, double cantidad, String nombre, Miembro pagador, String descripcion) {
        this.tarea = tarea;
        this.plan = plan;
        this.cantidad = cantidad;
        this.nombre = nombre;
        this.pagador = pagador;
        this.descripcion = descripcion;

        //Generado automáticamente
        this.creadoEn = LocalDateTime.now();
        this.actualizadoEn = LocalDateTime.now();
    }

    //Extraer Pago de la DB

    public Gasto(Long id, Tarea tarea, Plan plan, double cantidad, String nombre, Miembro pagador, String descripcion, LocalDateTime creadoEn, LocalDateTime actualizadoEn) {
        this.id = id;
        this.tarea = tarea;
        this.plan = plan;
        this.cantidad = cantidad;
        this.nombre = nombre;
        this.pagador = pagador;
        this.descripcion = descripcion;
        this.creadoEn = creadoEn;
        this.actualizadoEn = actualizadoEn;
    }

    //Getters y setters
    public Long getId() {
        return id;
    }

    public Tarea getTarea() {
        return tarea;
    }

    public Plan getPlan() {
        return plan;
    }

    public double getCantidad() {
        return cantidad;
    }

    public String getNombre() {
        return nombre;
    }

    public Miembro getPagador() {
        return pagador;
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

    public void setCantidad(double cantidad) {
        this.actualizadoEn = LocalDateTime.now();
        this.cantidad = cantidad;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
        this.actualizadoEn = LocalDateTime.now();
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
        this.actualizadoEn = LocalDateTime.now();
    }
}
