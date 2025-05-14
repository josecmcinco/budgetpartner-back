package com.budgetpartner.APP.domain;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table
public class Rol {

    @Id
    @GeneratedValue
    private int id;

    @Column
    private String nombre;

    @Column
    private String permisos;

    @Column
    private LocalDateTime creado_en;

    @Column
    private LocalDateTime actualizado_en;

    //Constructor vacío para Hibernate
    public Rol(){}

    //Crear Rol desde 0
    public Rol(String nombre, String permisos) {
        this.nombre = nombre;
        this.permisos = permisos;

        //Generar automñaticamente
        this.creado_en = LocalDateTime.now();
        this.actualizado_en = LocalDateTime.now();
    }

    //Extraer Rol de la DB
    public Rol(int id, String nombre, String permisos, LocalDateTime creado_en, LocalDateTime actualizado_en) {
        this.id = id;
        this.nombre = nombre;
        this.permisos = permisos;
        this.creado_en = creado_en;
        this.actualizado_en = actualizado_en;
    }

    //Getters y setters


    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getPermisos() {
        return permisos;
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

    public void setPermisos(String permisos) {
        this.permisos = permisos;
        this.actualizado_en = LocalDateTime.now();
    }
}
