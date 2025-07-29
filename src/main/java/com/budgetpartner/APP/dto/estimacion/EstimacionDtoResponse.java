package com.budgetpartner.APP.dto.estimacion;

import com.budgetpartner.APP.enums.MonedasDisponibles;
import com.budgetpartner.APP.enums.TipoEstimacion;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class EstimacionDtoResponse {

    /*
    SE PRESCINDE DE LAS SIGUIENTES VARIABLES PARA EL DTO:
    creadoEn-actualizadoEn
    SE MODIFICA EL TIPO DE LAS SIGUIENTES VARIABLES:
    plan, tarea, creador, pagador y gasto ahora llevan un Long en vez del Entity
     */
    private Long id;
    private Long planId;
    private Long tareaId;
    private Long creadorId;
    private Double cantidad;
    private TipoEstimacion tipoEstimacion;
    private MonedasDisponibles tipoMoneda;
    private String descripcion;
    private Long pagadorId;
    private Long gastoId;

    public EstimacionDtoResponse(Long id, Long planId, Long tareaId, Long creadorId, Double cantidad, TipoEstimacion tipoEstimacion, MonedasDisponibles tipoMoneda, String descripcion, Long pagadorId, Long gastoId) {
        this.id = id;
        this.planId = planId;
        this.tareaId = tareaId;
        this.creadorId = creadorId;
        this.cantidad = cantidad;
        this.tipoEstimacion = tipoEstimacion;
        this.tipoMoneda = tipoMoneda;
        this.descripcion = descripcion;
        this.pagadorId = pagadorId;
        this.gastoId = gastoId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPlanId() {
        return planId;
    }

    public Long getTareaId() {
        return tareaId;
    }

    public void setTareaId(Long tareaId) {
        this.tareaId = tareaId;
    }

    public Long getCreadorId() {
        return creadorId;
    }

    public void setCreadorId(Long creadorId) {
        this.creadorId = creadorId;
    }

    public Double getCantidad() {
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
