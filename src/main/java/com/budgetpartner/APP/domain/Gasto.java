package com.budgetpartner.APP.domain;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table
public class Gasto {
    @Id
    @GeneratedValue
    public Long id;

    @ManyToOne
    @JoinColumn(name= "id_tarea")
    private Tarea tarea;

    @ManyToOne
    @JoinColumn(name = "id_plan")
    private Plan plan;

    @Column
    private double cantidad;

    @Column
    private String nombre;

    @ManyToOne
    @JoinColumn(name= "id_miembro_pagador")
    private Miembro pagador;

    @Column
    private String descripcion;

    @Column
    private LocalDateTime creado_en;

    @Column
    private LocalDateTime actualizado_en;

}
