package com.budgetpartner.APP.dto.estimacion;

import com.budgetpartner.APP.enums.MonedasDisponibles;
import com.budgetpartner.APP.enums.TipoEstimacion;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class EstimacionDtoPostRequest {
    /*
    SE PRESCINDE DE LAS SIGUIENTES VARIABLES PARA EL DTO:
    id-creadoEn-actualizadoEn
    SE MODIFICA EL TIPO DE LAS SIGUIENTES VARIABLES:
    plan, tarea, creador, pagador y gasto ahora llevan un Long en vez del Entity
     */


    @NotNull(message = "El ID del plan no puede ser nulo")
    private Long planId;

    @NotNull(message = "El ID de la tarea no puede ser nulo")
    private Long tareaId;

    @NotNull(message = "El ID del creador no puede ser nulo")
    private Long creadorId;

    @NotNull(message = "La cantidad no puede ser nula")
    private Double cantidad;

    @NotNull(message = "El tipo de estimación no puede ser nulo")
    private TipoEstimacion tipoEstimacion;

    @NotNull(message = "El tipo de moneda no puede ser nulo")
    private MonedasDisponibles tipoMoneda;

    @NotBlank(message = "La descripción no puede estar vacía")
    private String descripcion;

    @NotNull(message = "El ID del pagador no puede ser nulo")
    private Long pagadorId;

    @NotNull(message = "El ID del gasto no puede ser nulo")
    private Long gastoId;

    public EstimacionDtoPostRequest(Long planId, Long tareaId, Long creador, Double cantidad, TipoEstimacion tipoEstimacion, MonedasDisponibles tipoMoneda, String descripcion, Long pagadorId, Long gastoId) {
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

    public Double getCantidad() {
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
