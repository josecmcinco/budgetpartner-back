package com.budgetpartner.APP.dto.organizacion;

public class OrganizacionDtoPostRequest {
    /*
        SE PRESCINDE DE LAS SIGUIENTES VARIABLES PARA EL DTO:
        id-creadoEn-actualizadoEn
    */
    private String nombreOrganizacion;
    private String descripcionOrganizacion;
    private String nickMiembroCreador;


    public OrganizacionDtoPostRequest(String nombreOrganizacion, String descripcionOrganizacion, String nickMiembroCreador) {
        this.nombreOrganizacion = nombreOrganizacion;
        this.descripcionOrganizacion = descripcionOrganizacion;
        this.nickMiembroCreador = nickMiembroCreador;
    }

    public String getNombreOrganizacion() {
        return nombreOrganizacion;
    }

    public void setNombreOrganizacion(String nombreOrganizacion) {
        this.nombreOrganizacion = nombreOrganizacion;
    }

    public String getDescripcionOrganizacion() {
        return descripcionOrganizacion;
    }

    public void setDescripcionOrganizacion(String descripcionOrganizacion) {
        this.descripcionOrganizacion = descripcionOrganizacion;
    }

    public String getNickMiembroCreador() {
        return nickMiembroCreador;
    }

}
