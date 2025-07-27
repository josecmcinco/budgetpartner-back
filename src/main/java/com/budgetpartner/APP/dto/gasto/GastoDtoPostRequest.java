package com.budgetpartner.APP.dto.gasto;

import com.budgetpartner.APP.entity.Plan;
import com.budgetpartner.APP.entity.Tarea;

import java.util.List;

public class GastoDtoPostRequest {
        /*
        SE PRESCINDE DE LAS SIGUIENTES VARIABLES PARA EL DTO:
        id-creadoEn-actualizadoEn-id
        SE MODIFICA EL TIMPO DE LAS SIGUIENTES VARIABLES:
        miembrosDelUsuario
         */

        private Long planId;
        private Long tareaId;
        private double cantidad;
        private String nombre;
        private Long pagadorId;
        private String descripcion;
        private List<Long> listaMiembrosEndeudados;

    public GastoDtoPostRequest(Long tareaId, Long planId, double cantidad, String nombre, Long pagadorId, String descripcion, List<Long> listaMiembrosEndeudados) {
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

    public double getCantidad() {
        return cantidad;
    }

    public void setCantidad(double cantidad) {
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
