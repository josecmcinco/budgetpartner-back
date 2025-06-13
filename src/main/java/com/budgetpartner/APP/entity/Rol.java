package com.budgetpartner.APP.entity;

import com.budgetpartner.APP.enums.NombreRol;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table
public class Rol {

    @Id
    @GeneratedValue
    private Long id;

    @Enumerated(EnumType.STRING)
    private NombreRol nombre;

    @Column
    private LocalDateTime creadoEn;

    @Column
    private LocalDateTime actualizadoEn;

    //Constructor vacío para Hibernate
    public Rol(){}

    //Crear Rol desde 0
    public Rol(NombreRol nombre) {
        this.nombre = nombre;

        //Generar automñaticamente
        this.creadoEn = LocalDateTime.now();
        this.actualizadoEn = LocalDateTime.now();
    }

    //Extraer Rol de la DB
    public Rol(Long id, NombreRol nombre, LocalDateTime creadoEn, LocalDateTime actualizadoEn) {
        this.id = id;
        this.nombre = nombre;
        this.creadoEn = creadoEn;
        this.actualizadoEn = actualizadoEn;
    }

    //Getters y setters


    public Long getId() {
        return id;
    }

    public NombreRol getNombre() {
        return nombre;
    }

    public LocalDateTime getCreadoEn() {
        return creadoEn;
    }

    public LocalDateTime getActualizadoEn() {
        return actualizadoEn;
    }

    public void setNombre(NombreRol nombre) {
        this.nombre = nombre;
        this.actualizadoEn = LocalDateTime.now();
    }

}
