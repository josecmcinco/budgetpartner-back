package com.budgetpartner.APP.dto.plan;

import com.budgetpartner.APP.dto.gasto.GastoDtoResponse;
import com.budgetpartner.APP.dto.organizacion.OrganizacionDtoResponse;
import com.budgetpartner.APP.dto.tarea.TareaDtoResponse;
import com.budgetpartner.APP.entity.Organizacion;
import com.budgetpartner.APP.entity.Tarea;
import com.budgetpartner.APP.enums.ModoPlan;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PlanDtoResponse {
    /*
        SE PRESCINDE DE LAS SIGUIENTES VARIABLES PARA EL DTO:
        creadoEn-actualizadoEn
    */

    private Long id;
    private OrganizacionDtoResponse organizacion;
    private String nombre;
    private String descripcion;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
    private List<TareaDtoResponse> tareas;
    private List<GastoDtoResponse> gastos;
    private ModoPlan modoPlan;


    public PlanDtoResponse(Long id, OrganizacionDtoResponse organizacion, String nombre, String descripcion, LocalDateTime fechaInicio, LocalDateTime fechaFin,  ModoPlan modoPlan) {
        this.id = id;
        this.organizacion = organizacion;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.modoPlan = modoPlan;
    }

    public Long getId() {
        return id;
    }

    public OrganizacionDtoResponse getOrganizacion() {
        return organizacion;
    }

    public void setOrganizacion(OrganizacionDtoResponse organizacion) {
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

    public List<TareaDtoResponse> getTareas() {
        return tareas;
    }

    public void setTareas(List<TareaDtoResponse> tareas) {
        this.tareas = tareas;
    }

    public List<GastoDtoResponse> getGastos() {
        return gastos;
    }

    public void setGastos(List<GastoDtoResponse> gastos) {
        this.gastos = gastos;
    }

    public ModoPlan getModoPlan() {
        return modoPlan;
    }

    public void setModoPlan(ModoPlan modoPlan) {
        this.modoPlan = modoPlan;
    }
}
