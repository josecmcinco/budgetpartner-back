package com.budgetpartner.APP.dto.dashborard;

public class DashboardDtoResponse {

    private Integer numeroOrganizaciones;
    private Integer numeroPlanes;
    private Integer numTareas;

    public DashboardDtoResponse(int numeroOrganizaciones, int numeroPlanes, int numTareas) {
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
}
