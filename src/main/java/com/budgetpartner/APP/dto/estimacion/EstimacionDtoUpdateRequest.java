package com.budgetpartner.APP.dto.estimacion;

import com.budgetpartner.APP.enums.MonedasDisponibles;

public class EstimacionDtoUpdateRequest {

    /*
    SE PRESCINDE DE LAS SIGUIENTES VARIABLES PARA EL DTO:
    creadoEn-actualizadoEn- plan- tarea -creador
    SE MODIFICA EL TIPO DE LAS SIGUIENTES VARIABLES:
    pagador y gasto ahora llevan un Long en vez del Entity
     */


    private Double cantidad;
    private MonedasDisponibles tipoMoneda;
    private String descripcion;
    private Long pagadorId;
    private Long gastoId;

    public EstimacionDtoUpdateRequest(Double cantidad, MonedasDisponibles tipoMoneda, String descripcion, Long pagadorId, Long gastoId) {
        this.cantidad = cantidad;
        this.tipoMoneda = tipoMoneda;
        this.descripcion = descripcion;
        this.pagadorId = pagadorId;
        this.gastoId = gastoId;
    }


    public Double getCantidad() {
        return cantidad;
    }

    public void setCantidad(Double cantidad) {
        this.cantidad = cantidad;
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
