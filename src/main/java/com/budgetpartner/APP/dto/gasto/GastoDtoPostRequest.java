package com.budgetpartner.APP.dto.gasto;

import com.budgetpartner.APP.entity.Plan;
import com.budgetpartner.APP.entity.Tarea;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public class GastoDtoPostRequest {
        /*
        SE PRESCINDE DE LAS SIGUIENTES VARIABLES PARA EL DTO:
        id-creadoEn-actualizadoEn-id
        SE MODIFICA EL TIMPO DE LAS SIGUIENTES VARIABLES:
        miembrosDelUsuario
         */

    @NotNull(message = "El ID del plan no puede ser nulo")
    private Long planId;

    private Long tareaId;

    @NotNull(message = "La cantidad no puede ser nula")
    private Double cantidad;

    @NotBlank(message = "El nombre no puede estar vacío")
    private String nombre;

    @NotNull(message = "El ID del pagador no puede ser nulo")
    private Long pagadorId;

    @NotBlank(message = "La descripción no puede estar vacía")
    private String descripcion;

    @NotEmpty(message = "La lista de miembros endeudados no puede estar vacía")
    private List<@NotNull(message = "El ID de cada miembro endeudado no puede ser nulo") Long> listaMiembrosEndeudados;

    public GastoDtoPostRequest(Long tareaId, Long planId, Double cantidad, String nombre, Long pagadorId, String descripcion, List<Long> listaMiembrosEndeudados) {
        this.tareaId = tareaId;
        this.planId = planId;
        this.cantidad = cantidad;
        this.nombre = nombre;
        this.pagadorId = pagadorId;
        this.descripcion = descripcion;
        this.listaMiembrosEndeudados = listaMiembrosEndeudados;
    }

    public Long getPlanId() {
        return planId;
    }

    //public void setPlanId(Long planId) {this.planId = planId;}
    public Long getTareaId() {
        return tareaId;
    }

    public Double getCantidad() {
        return cantidad;
    }

    public void setCantidad(Double cantidad) {
        this.cantidad = cantidad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Long getPagadorId() {
        return pagadorId;
    }

    //public void setPagadorId(Long pagadorId) {this.pagadorId = pagadorId;}

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public List<Long> getListaMiembrosEndeudados() {
        return listaMiembrosEndeudados;
    }

    public void setListaMiembrosEndeudados(List<Long> listaMiembrosEndeudados) {
        this.listaMiembrosEndeudados = listaMiembrosEndeudados;
    }
}
