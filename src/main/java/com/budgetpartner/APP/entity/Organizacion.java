package com.budgetpartner.APP.entity;

import com.budgetpartner.APP.enums.MonedasDisponibles;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    @Column
    private MonedasDisponibles moneda;


    @OneToMany(mappedBy = "organizacion", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Plan> planes = new ArrayList<>();

    @OneToMany(mappedBy = "organizacion", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Miembro> miembros = new ArrayList<>();

    @OneToMany(mappedBy = "organizacion", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Invitacion> invitaciones = new ArrayList<>();

    @Column
    private LocalDateTime creadoEn;

    @Column
    private LocalDateTime actualizadoEn;

    //Constructor para Hibernate
    public Organizacion(){}

    //Creacióon de Organizacion desde 0
    public Organizacion(String nombre, String descripcion, MonedasDisponibles moneda) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.moneda = moneda;

        //Generado automáticamente
        this.creadoEn = LocalDateTime.now();
        this.actualizadoEn = LocalDateTime.now();
    }

    //Extracción de Organizacion de la DB
    public Organizacion(Long id, String nombre, String descripcion, MonedasDisponibles moneda, LocalDateTime creadoEn, LocalDateTime actualizadoEn) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.moneda = moneda;
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

    public String getDescripcion() {
        return descripcion;
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

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
        this.actualizadoEn = LocalDateTime.now();
    }

    public List<Invitacion> getInvitaciones() {
        return invitaciones;
    }

    public void setInvitaciones(List<Invitacion> invitaciones) {
        this.invitaciones = invitaciones;
    }

    public MonedasDisponibles getMoneda() {
        return moneda;
    }

    public void setMoneda(MonedasDisponibles moneda) {
        this.moneda = moneda;
    }
}
