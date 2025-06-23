package com.budgetpartner.APP.entity;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.budgetpartner.APP.enums.EstadoTarea;
import jakarta.persistence.*;

@Entity
@Table
public class Tarea {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "plan_id")
    private Plan plan;

    @OneToMany(mappedBy = "tarea", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Gasto> gastos = new ArrayList<>();

    @OneToMany(mappedBy = "tarea", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Estimacion> estimaciones = new ArrayList<>();

    @Column
    private String titulo;

    @Column
    private String descripcion;

    @Column
    private LocalDateTime fechaFin;

    @Enumerated
    private EstadoTarea estado;

    @Column
    private double costeEstimado;

    @Column
    private String moneda;

    @Column
    private LocalDateTime creadoEn;

    @Column
    private LocalDateTime actualizadoEn;


    @ManyToMany
    @JoinTable(name = "miembro_tarea",
            joinColumns = @JoinColumn(name = "tarea_id"),
            inverseJoinColumns = @JoinColumn(name = "miembro_id")
    )
    private List<Miembro> miembros;

    //Constructor vacío para Hibernate
    public Tarea(){}

    //Creación de Tarea desde 0
    public Tarea(Plan plan, String titulo, String descripcion, LocalDateTime fechaFin, double costeEstimado, String moneda, List<Miembro> miembros) {
        this.plan = plan;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fechaFin = fechaFin;
        this.costeEstimado = costeEstimado;
        this.moneda = moneda;
        this.miembros = miembros;

        //Generado automáticamente
        this.actualizadoEn = LocalDateTime.now();
        this.creadoEn = LocalDateTime.now();
        this.estado = EstadoTarea.PENDIENTE;
    }

    //Extracción de tarea de la DB
    public Tarea(Long id, Plan plan, String titulo, String descripcion, LocalDateTime fechaFin, EstadoTarea estado, double costeEstimado, String moneda, LocalDateTime creadoEn, LocalDateTime actualizadoEn, List<Miembro> miembros) {
        this.id = id;
        this.plan = plan;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fechaFin = fechaFin;
        this.estado = estado;
        this.costeEstimado = costeEstimado;
        this.moneda = moneda;
        this.creadoEn = creadoEn;
        this.actualizadoEn = actualizadoEn;
        this.miembros = miembros;
    }

    public Long getId() {
        return id;
    }

    public Plan getPlan() {
        return plan;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public LocalDateTime getFechaFin() {
        return fechaFin;
    }

    public EstadoTarea getEstado() {
        return estado;
    }

    public double getCosteEstimado() {
        return costeEstimado;
    }

    public String getMoneda() {
        return moneda;
    }

    public LocalDateTime getCreadoEn() {
        return creadoEn;
    }

    public LocalDateTime getActualizadoEn() {
        return actualizadoEn;
    }

    public List<Miembro> getMiembros() {
        return miembros;
    }

    public void setFechaFin(LocalDateTime fechaFin) {
        this.fechaFin = fechaFin;
        this.actualizadoEn = LocalDateTime.now();
    }

    public void setMiembros(List<Miembro> miembros) {
        this.miembros = miembros;
        this.actualizadoEn = LocalDateTime.now();
    }


    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
        this.actualizadoEn = LocalDateTime.now();
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
        this.actualizadoEn = LocalDateTime.now();
    }

    public void setEstado(EstadoTarea estado) {
        this.estado = estado;
        this.actualizadoEn = LocalDateTime.now();
    }

    public void setCosteEstimado(double costeEstimado) {
        this.costeEstimado = costeEstimado;
        this.actualizadoEn = LocalDateTime.now();
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
        this.actualizadoEn = LocalDateTime.now();
    }
}
