package com.budgetpartner.APP.dto.dashborard;

import com.budgetpartner.APP.dto.usuario.UsuarioDtoResponse;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class DashboardDtoResponse {

    private Integer numeroOrganizaciones;
    private Integer numeroPlanes;
    private Integer numTareas;
    UsuarioDtoResponse usuarioDtoResponse;

    public DashboardDtoResponse(UsuarioDtoResponse usuarioDtoResponse, int numeroOrganizaciones, int numeroPlanes, int numTareas) {
        this.usuarioDtoResponse = usuarioDtoResponse;
        this.numeroOrganizaciones = numeroOrganizaciones;
        this.numeroPlanes = numeroPlanes;
        this.numTareas = numTareas;
    }

    public Integer getNumeroOrganizaciones() {
        return numeroOrganizaciones;
    }

    public void setNumeroOrganizaciones(Integer numeroOrganizaciones) {
        this.numeroOrganizaciones = numeroOrganizaciones;
    }

    public Integer getNumeroPlanes() {
        return numeroPlanes;
    }

    public void setNumeroPlanes(Integer numeroPlanes) {
        this.numeroPlanes = numeroPlanes;
    }

    public Integer getNumTareas() {
        return numTareas;
    }

    public void setNumTareas(Integer numTareas) {
        this.numTareas = numTareas;
    }

    public UsuarioDtoResponse getUsuarioDtoResponse() {
        return usuarioDtoResponse;
    }

    public void setUsuarioDtoResponse(UsuarioDtoResponse usuarioDtoResponse) {
        this.usuarioDtoResponse = usuarioDtoResponse;
    }
}
