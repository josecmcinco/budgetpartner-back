package com.budgetpartner.APP.domain;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table
public class Organizacion {
    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String nombre;

    @Column
    private String descripcion;

    /*
    TODO IMAGEN
     */

    @Column
    private LocalDateTime creado_en;

    @Column
    private LocalDateTime actualizado_en;

    //Constructor para Hibernate
    public Organizacion(){}

    //Creacióon de Organizacion desde 0
    public Organizacion(String nombre, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;

        //Generado automáticamente
        this.creado_en = LocalDateTime.now();
        this.actualizado_en = LocalDateTime.now();
    }

    //Extracción de Organizacion de la DB
    public Organizacion(Long id, String nombre, String descripcion, LocalDateTime creado_en, LocalDateTime actualizado_en) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.creado_en = creado_en;
        this.actualizado_en = actualizado_en;
    }

    //Getters y setters
    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public LocalDateTime getCreado_en() {
        return creado_en;
    }

    public LocalDateTime getActualizado_en() {
        return actualizado_en;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
        this.actualizado_en = LocalDateTime.now();
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
        this.actualizado_en = LocalDateTime.now();
    }
}
