package com.budgetpartner.APP.entity;

import com.budgetpartner.APP.enums.ModoPlan;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
public class Plan {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "organizacion_id")
    private Organizacion organizacion;

    @OneToMany(mappedBy = "plan", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Tarea> tarea = new ArrayList<>();

    @OneToMany(mappedBy = "plan", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Gasto> gastos = new ArrayList<>();

    @OneToMany(mappedBy = "plan", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Estimacion> estimaciones = new ArrayList<>();

    @Column
    private String nombre;

    @Column
    private String descripcion;

    @Column
    private LocalDateTime fechaInicio;

    @Column
    private LocalDateTime fechaFin;

    @Column
    private LocalDateTime creadoEn;

    @Column
    private LocalDateTime actualizadoEn;

    @Enumerated
    private ModoPlan modoPlan;

    @Column
    private Double longitud;

    @Column
    private Double latitud;

    //Constructor vacío para Hibernate
    public Plan(){}

    //Creacion de Plan desde 0
    public Plan(Organizacion organizacion, String nombre, String descripcion, LocalDateTime fechaInicio, LocalDateTime fechaFin, ModoPlan modoPlan, Double latitud, Double longitud) {
        this.organizacion = organizacion;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.modoPlan = modoPlan;
        this.latitud = latitud;
        this.longitud = longitud;

        //Generado automáticamente
        this.creadoEn = LocalDateTime.now();
        this.actualizadoEn = LocalDateTime.now();
    }

    //Extraer Plan de la DB
    public Plan(Long id, Organizacion organizacion, String nombre, String descripcion, LocalDateTime fechaInicio, LocalDateTime fechaFin, ModoPlan modoPlan, Double latitud, Double longitud, LocalDateTime creadoEn, LocalDateTime actualizadoEn) {
        this.id = id;
        this.organizacion = organizacion;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.modoPlan = modoPlan;
        this.creadoEn = creadoEn;
        this.actualizadoEn = actualizadoEn;
        this.latitud = latitud;
        this.longitud = longitud;
    }

    public Long getId() {
        return id;
    }

    public Organizacion getOrganizacion() {
        return organizacion;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public LocalDateTime getFechaInicio() {
        return fechaInicio;
    }

    public LocalDateTime getFechaFin() {
        return fechaFin;
    }

    public LocalDateTime getCreadoEn() {
        return creadoEn;
    }

    public LocalDateTime getActualizadoEn() {
        return actualizadoEn;
    }

    public ModoPlan getModoPlan() {
        return modoPlan;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
        this.actualizadoEn = LocalDateTime.now();
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
        this.actualizadoEn = LocalDateTime.now();
    }

    public void setFechaInicio(LocalDateTime fechaInicio) {
        this.fechaInicio = fechaInicio;
        this.actualizadoEn = LocalDateTime.now();
    }

    public void setFechaFin(LocalDateTime fechaFin) {
        this.fechaFin = fechaFin;
        this.actualizadoEn = LocalDateTime.now();
    }

    public void setModoPlan(ModoPlan modoPlan) {
        this.modoPlan = modoPlan;
        this.actualizadoEn = LocalDateTime.now();
    }

    public Double getLatitud() {
        return latitud;
    }

    public void setLatitud(Double latitud) {
        this.latitud = latitud;
    }

    public Double getLongitud() {
        return longitud;
    }

    public void setLongitud(Double longitud) {
        this.longitud = longitud;
    }
}
