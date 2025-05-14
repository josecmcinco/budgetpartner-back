package com.budgetpartner.APP.domain;
import java.time.LocalDateTime;
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
    @JoinColumn(name = "id_plan")
    private Plan plan;

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
    private LocalDateTime creado_en;

    @Column
    private LocalDateTime actualizado_en;


    @ManyToMany
    @JoinTable(name = "miembro_tarea",
            joinColumns = @JoinColumn(name = "tarea_id"),
            inverseJoinColumns = @JoinColumn(name = "miembro_id")
    )
    private List<Miembro> miembros;

    //Constructor vacío para Hibernate
    public Tarea(){}

    //Creación de Tarea desde 0
    public Tarea(Plan plan, String titulo, String descripcion, LocalDateTime fechaFin, double costeEstimado, String moneda, LocalDateTime creado_en, List<Miembro> miembros) {
        this.plan = plan;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fechaFin = fechaFin;
        this.costeEstimado = costeEstimado;
        this.moneda = moneda;
        this.creado_en = creado_en;
        this.miembros = miembros;

        //Generado automáticamente
        this.actualizado_en = LocalDateTime.now();
        this.creado_en = LocalDateTime.now();
    }

    //Extracción de tarea de la DB
    public Tarea(Long id, Plan plan, String titulo, String descripcion, LocalDateTime fechaFin, EstadoTarea estado, double costeEstimado, String moneda, LocalDateTime creado_en, LocalDateTime actualizado_en, List<Miembro> miembros) {
        this.id = id;
        this.plan = plan;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fechaFin = fechaFin;
        this.estado = estado;
        this.costeEstimado = costeEstimado;
        this.moneda = moneda;
        this.creado_en = creado_en;
        this.actualizado_en = actualizado_en;
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

    public LocalDateTime getCreado_en() {
        return creado_en;
    }

    public LocalDateTime getActualizado_en() {
        return actualizado_en;
    }

    public List<Miembro> getMiembros() {
        return miembros;
    }

    public void setFechaFin(LocalDateTime fechaFin) {
        this.fechaFin = fechaFin;
        this.actualizado_en = LocalDateTime.now();
    }

    public void setMiembros(List<Miembro> miembros) {
        this.miembros = miembros;
        this.actualizado_en = LocalDateTime.now();
    }


    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
        this.actualizado_en = LocalDateTime.now();
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
        this.actualizado_en = LocalDateTime.now();
    }

    public void setEstado(EstadoTarea estado) {
        this.estado = estado;
        this.actualizado_en = LocalDateTime.now();
    }

    public void setCosteEstimado(double costeEstimado) {
        this.costeEstimado = costeEstimado;
        this.actualizado_en = LocalDateTime.now();
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
        this.actualizado_en = LocalDateTime.now();
    }
}
