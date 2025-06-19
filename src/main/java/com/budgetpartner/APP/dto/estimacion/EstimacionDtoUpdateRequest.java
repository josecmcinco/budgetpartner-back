package com.budgetpartner.APP.dto.estimacion;

import com.budgetpartner.APP.enums.MonedasDisponibles;
import com.budgetpartner.APP.enums.TipoEstimacion;

public class EstimacionDtoUpdateRequest {

    /*
    SE PRESCINDE DE LAS SIGUIENTES VARIABLES PARA EL DTO:
    creadoEn-actualizadoEn- plan- tarea -creador
    SE MODIFICA EL TIPO DE LAS SIGUIENTES VARIABLES:
    pagador y gasto ahora llevan un Long en vez del Entity
     */


    private double cantidad;
    private TipoEstimacion tipoEstimacion;
    private MonedasDisponibles tipoMoneda;
    private String descripcion;
    private Long pagadorId;
    private Long gastoId;

    public EstimacionDtoUpdateRequest(double cantidad, TipoEstimacion tipoEstimacion, MonedasDisponibles tipoMoneda, String descripcion, Long pagadorId, Long gastoId) {
        this.cantidad = cantidad;
        this.tipoEstimacion = tipoEstimacion;
        this.tipoMoneda = tipoMoneda;
        this.descripcion = descripcion;
        this.pagadorId = pagadorId;
        this.gastoId = gastoId;
    }


    public double getCantidad() {
        return cantidad;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }

    public TipoEstimacion getTipoEstimacion() {
        return tipoEstimacion;
    }

    public void setTipoEstimacion(TipoEstimacion tipoEstimacion) {
        this.tipoEstimacion = tipoEstimacion;
    }

    public MonedasDisponibles getTipoMoneda() {
        return tipoMoneda;
    }

    public void setTipoMoneda(MonedasDisponibles tipoMoneda) {
        this.tipoMoneda = tipoMoneda;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Long getPagadorId() {
        return pagadorId;
    }

    public void setPagadorId(Long pagadorId) {
        this.pagadorId = pagadorId;
    }

    public Long getGastoId() {
        return gastoId;
    }

    public void setGastoId(Long gastoId) {
        this.gastoId = gastoId;
    }
}
