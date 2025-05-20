package com.budgetpartner.APP.dto.request;

import com.budgetpartner.APP.entity.Miembro;
import com.budgetpartner.APP.entity.Plan;
import com.budgetpartner.APP.entity.Tarea;

public class GastoDtoRequest {
        /*
        SE PRESCINDE DE LAS SIGUIENTES VARIABLES PARA EL DTO:
        id-creadoEn-actualizadoEn
        SE MODIFICA EL TIMPO DE LAS SIGUIENTES VARIABLES:
        miembrosDelUsuario
         */

        private Long tareaId;
        private Long planId;
        private double cantidad;
        private String nombre;
        private Long pagadorId;
        private String descripcion;

    public GastoDtoRequest(Long tareaId, Long planId, double cantidad, String nombre, Long pagadorId, String descripcion) {
        this.tareaId = tareaId;
        this.planId = planId;
        this.cantidad = cantidad;
        this.nombre = nombre;
        this.pagadorId = pagadorId;
        this.descripcion = descripcion;
    }

    public Long getTareaId() {
        return tareaId;
    }

    public void setTareaId(Long tareaId) {
        this.tareaId = tareaId;
    }

    public Long getPlanId() {
        return planId;
    }

    public void setPlan(Long planId) {
        this.planId = planId;
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

    public void setPagadorId(Long pagadorId) {
        this.pagadorId = pagadorId;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
