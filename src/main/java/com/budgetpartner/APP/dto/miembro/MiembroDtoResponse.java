package com.budgetpartner.APP.dto.miembro;

import com.budgetpartner.APP.dto.organizacion.OrganizacionDtoResponse;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class MiembroDtoResponse {
    /*
        SE PRESCINDE DE LAS SIGUIENTES VARIABLES PARA EL DTO:
        creadoEn-actualizadoEn
    */

    private Long id;
    private OrganizacionDtoResponse organizacion;
    private Long rolId;
    private String nick;
    private LocalDateTime fechaIngreso;
    private Boolean isAsociado;
    private Boolean isActivo;
    private Double deudaEnPlan;

    public MiembroDtoResponse(Long id, Long rolId, String nick, LocalDateTime fechaIngreso, Boolean isAsociado, Boolean isActivo) {
        this.id = id;
        this.rolId = rolId;
        this.nick = nick;
        this.fechaIngreso = fechaIngreso;
        this.isAsociado = isAsociado;
        this.isActivo = isActivo;
    }


    public Long getId() {
        return id;
    }

    public OrganizacionDtoResponse getOrganizacion() {
        return organizacion;
    }

    public void setOrganizacion(OrganizacionDtoResponse organizacion) {this.organizacion = organizacion;}

    public Long getRolId() {
        return rolId;
    }

    public void setRolId(Long rolId) {
        this.rolId = rolId;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public LocalDateTime getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(LocalDateTime fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public Boolean getIsAsociado() {
        return isAsociado;
    }

    public void setIsAsociado(Boolean asociado) {
        isAsociado = asociado;
    }

    public Boolean getisActivo() {
        return isActivo;
    }

    public void setisActivo(Boolean isActivo) {
        this.isActivo = isActivo;
    }

    public Double getDeudaEnPlan() {
        return deudaEnPlan;
    }

    public void setDeudaEnPlan(Double deudaEnPlan) {
        this.deudaEnPlan = deudaEnPlan;
    }
}
