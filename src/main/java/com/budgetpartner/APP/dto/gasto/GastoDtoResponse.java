package com.budgetpartner.APP.dto.gasto;

import com.budgetpartner.APP.dto.plan.PlanDtoResponse;
import com.budgetpartner.APP.dto.tarea.TareaDtoResponse;
import com.budgetpartner.APP.entity.Miembro;
import com.budgetpartner.APP.entity.Plan;
import com.budgetpartner.APP.entity.Tarea;

public class GastoDtoResponse {
        /*
        SE PRESCINDE DE LAS SIGUIENTES VARIABLES PARA EL DTO:
        creadoEn-actualizadoEn
        SE MODIFICA EL TIMPO DE LAS SIGUIENTES VARIABLES:
        miembrosDelUsuario
         */

        private Long id;
        private TareaDtoResponse tarea;
        private PlanDtoResponse plan;
        private double cantidad;
        private String nombre;
        private Miembro pagador;
        private String descripcion;

    public GastoDtoResponse(Long id, TareaDtoResponse tarea, PlanDtoResponse plan, double cantidad, String nombre, String descripcion) {
        this.id = id;
        this.tarea = tarea;
        this.plan = plan;
        this.cantidad = cantidad;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TareaDtoResponse getTarea() {
        return tarea;
    }

    public void setTarea(TareaDtoResponse tarea) {
        this.tarea = tarea;
    }

    public PlanDtoResponse getPlan() {
        return plan;
    }

    public void setPlan(PlanDtoResponse plan) {
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
