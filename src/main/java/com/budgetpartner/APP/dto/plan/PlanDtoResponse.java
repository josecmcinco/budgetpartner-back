package com.budgetpartner.APP.dto.plan;

import com.budgetpartner.APP.entity.Organizacion;
import com.budgetpartner.APP.entity.Tarea;

import java.time.LocalDateTime;
import java.util.List;

public class PlanDtoResponse {
    /*
        SE PRESCINDE DE LAS SIGUIENTES VARIABLES PARA EL DTO:
        creadoEn-actualizadoEn
    */

    private Long id;
    private Organizacion organizacion;
    private String nombre;
    private String descripcion;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
    private List<Tarea> tareas;
    private List<Tarea> gastos;


    public PlanDtoResponse(Long id, Organizacion organizacion, String nombre, String descripcion, LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        this.id = id;
        this.organizacion = organizacion;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }

    public Long getId() {
        return id;
    }

    public Organizacion getOrganizacion() {
        return organizacion;
    }

    public void setOrganizacion(Organizacion organizacion) {
        this.organizacion = organizacion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public LocalDateTime getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDateTime fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDateTime getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDateTime fechaFin) {
        this.fechaFin = fechaFin;
    }

    public List<Tarea> getTareas() {
        return tareas;
    }

    public void setTareas(List<Tarea> tareas) {
        this.tareas = tareas;
    }

    public List<Tarea> getGastos() {
        return gastos;
    }

    public void setGastos(List<Tarea> gastos) {
        this.gastos = gastos;
    }
}
