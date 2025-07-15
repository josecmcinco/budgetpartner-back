package com.budgetpartner.APP.dto.gasto;

import com.budgetpartner.APP.dto.miembro.MiembroDtoResponse;
import com.budgetpartner.APP.dto.plan.PlanDtoResponse;
import com.budgetpartner.APP.dto.tarea.TareaDtoResponse;
import com.budgetpartner.APP.entity.Miembro;
import com.budgetpartner.APP.entity.Plan;
import com.budgetpartner.APP.entity.Tarea;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class GastoDtoResponse {
        /*
        SE PRESCINDE DE LAS SIGUIENTES VARIABLES PARA EL DTO:
        creadoEn-actualizadoEn
        SE MODIFICA EL TIMPO DE LAS SIGUIENTES VARIABLES:
        miembrosDelUsuario
         */

        private Long id;
        private Long tareaId;
        private Long planId;
        private double cantidad;
        private String nombre;
        private MiembroDtoResponse pagador;
        private String descripcion;
        private List<MiembroDtoResponse> miembrosEndeudadosDtoRes;
        private LocalDateTime creadoEn;
        private LocalDateTime actualizadoEn;

    public GastoDtoResponse(Long id, Long tareaId, Long planId, double cantidad, String nombre, String descripcion,MiembroDtoResponse pagador,
                            List<MiembroDtoResponse> miembrosEndeudadosDtoRes, LocalDateTime creadoEn, LocalDateTime actualizadoEn) {
        this.id = id;
        this.tareaId = tareaId;
        this.planId = planId;
        this.cantidad = cantidad;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.pagador = pagador;
        this.miembrosEndeudadosDtoRes = miembrosEndeudadosDtoRes;
        this.creadoEn = creadoEn;
        this.actualizadoEn = actualizadoEn;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public void setPlanId(Long planId) {
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

    public MiembroDtoResponse getPagador() {
        return pagador;
    }

    public void setPagador(MiembroDtoResponse pagador) {
        this.pagador = pagador;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public List<MiembroDtoResponse> getMiembrosEndeudadosDtoRes() {
        return miembrosEndeudadosDtoRes;
    }

    public void setMiembrosEndeudadosDtoRes(List<MiembroDtoResponse> miembrosEndeudadosDtoRes) {
        this.miembrosEndeudadosDtoRes = miembrosEndeudadosDtoRes;
    }

    public LocalDateTime getCreadoEn() {
        return creadoEn;
    }

    public void setCreadoEn(LocalDateTime creadoEn) {
        this.creadoEn = creadoEn;
    }

    public LocalDateTime getActualizadoEn() {
        return actualizadoEn;
    }

    public void setActualizadoEn(LocalDateTime actualizadoEn) {
        this.actualizadoEn = actualizadoEn;
    }
}
