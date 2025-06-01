package com.budgetpartner.APP.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table
public class Rol {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String nombre;

    @Column
    private String permisos;

    @Column
    private LocalDateTime creadoEn;

    @Column
    private LocalDateTime actualizadoEn;

    //Constructor vacío para Hibernate
    public Rol(){}

    //Crear Rol desde 0
    public Rol(String nombre, String permisos) {
        this.nombre = nombre;
        this.permisos = permisos;

        //Generar automñaticamente
        this.creadoEn = LocalDateTime.now();
        this.actualizadoEn = LocalDateTime.now();
    }

    //Extraer Rol de la DB
    public Rol(Long id, String nombre, String permisos, LocalDateTime creadoEn, LocalDateTime actualizadoEn) {
        this.id = id;
        this.nombre = nombre;
        this.permisos = permisos;
        this.creadoEn = creadoEn;
        this.actualizadoEn = actualizadoEn;
    }

    //Getters y setters


    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getPermisos() {
        return permisos;
    }

    public LocalDateTime getCreadoEn() {
        return creadoEn;
    }

    public LocalDateTime getActualizadoEn() {
        return actualizadoEn;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
        this.actualizadoEn = LocalDateTime.now();
    }

    public void setPermisos(String permisos) {
        this.permisos = permisos;
        this.actualizadoEn = LocalDateTime.now();
    }
}
