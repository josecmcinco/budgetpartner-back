package com.budgetpartner.APP.dto.estimacion;

import com.budgetpartner.APP.enums.MonedasDisponibles;
import com.budgetpartner.APP.enums.TipoEstimacion;

public class EstimacionDtoPostRequest {
    /*
    SE PRESCINDE DE LAS SIGUIENTES VARIABLES PARA EL DTO:
    id-creadoEn-actualizadoEn
    SE MODIFICA EL TIPO DE LAS SIGUIENTES VARIABLES:
    plan, tarea, creador, pagador y gasto ahora llevan un Long en vez del Entity
     */


    private Long planId;
    private Long tareaId;
    private Long creadorId;
    private double cantidad;
    private TipoEstimacion tipoEstimacion;
    private MonedasDisponibles tipoMoneda;
    private String descripcion;
    private Long pagadorId;
    private Long gastoId;

    public EstimacionDtoPostRequest(Long planId, Long tareaId, Long creador, double cantidad, TipoEstimacion tipoEstimacion, MonedasDisponibles tipoMoneda, String descripcion, Long pagadorId, Long gastoId) {
        this.planId = planId;
        this.tareaId = tareaId;
        this.creadorId = creador;
        this.cantidad = cantidad;
        this.tipoEstimacion = tipoEstimacion;
        this.tipoMoneda = tipoMoneda;
        this.descripcion = descripcion;
        this.pagadorId = pagadorId;
        this.gastoId = gastoId;
    }

    public Long getPlanId() {
        return planId;
    }

    public Long getTareaId() {
        return tareaId;
    }

    public Long getCreadorId() {
        return creadorId;
    }

    public double getCantidad() {
        return cantidad;
    }

    public TipoEstimacion getTipoEstimacion() {
        return tipoEstimacion;
    }

    public MonedasDisponibles getTipoMoneda() {
        return tipoMoneda;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Long getPagadorId() {
        return pagadorId;
    }

    public Long getGastoId() {
        return gastoId;
    }
}
