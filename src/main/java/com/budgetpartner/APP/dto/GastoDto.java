package com.budgetpartner.APP.dto;

import com.budgetpartner.APP.entity.Miembro;
import com.budgetpartner.APP.entity.Plan;
import com.budgetpartner.APP.entity.Tarea;

public class GastoDto {
        /*
        SE PRESCINDE DE LAS SIGUIENTES VARIABLES PARA EL DTO:
        creadoEn-actualizadoEn
        SE MODIFICA EL TIMPO DE LAS SIGUIENTES VARIABLES:
        miembrosDelUsuario
         */

        private Long id;
        private Tarea tarea;
        private Plan plan;
        private double cantidad;
        private String nombre;
        private Miembro pagador;
        private String descripcion;

    public GastoDto(Long id, Tarea tarea, Plan plan, double cantidad, String nombre, Miembro pagador, String descripcion) {
        this.id = id;
        this.tarea = tarea;
        this.plan = plan;
        this.cantidad = cantidad;
        this.nombre = nombre;
        this.pagador = pagador;
        this.descripcion = descripcion;
    }

    public Tarea getTarea() {
        return tarea;
    }

    public void setTarea(Tarea tarea) {
        this.tarea = tarea;
    }

    public Plan getPlan() {
        return plan;
    }

    public void setPlan(Plan plan) {
        this.plan = plan;
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

    public Miembro getPagador() {
        return pagador;
    }

    public void setPagador(Miembro pagador) {
        this.pagador = pagador;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
