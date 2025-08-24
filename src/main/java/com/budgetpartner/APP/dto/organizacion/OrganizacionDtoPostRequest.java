package com.budgetpartner.APP.dto.organizacion;

import com.budgetpartner.APP.enums.MonedasDisponibles;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class OrganizacionDtoPostRequest {
    /*
        SE PRESCINDE DE LAS SIGUIENTES VARIABLES PARA EL DTO:
        id-creadoEn-actualizadoEn
    */
    @NotBlank(message = "El nombre de la organización no puede estar vacío")
    private String nombreOrganizacion;

    @NotBlank(message = "La descripción de la organización no puede estar vacía")
    private String descripcionOrganizacion;

    @NotBlank(message = "El nick del miembro creador no puede estar vacío")
    private String nickMiembroCreador;

    @NotNull(message = "El nick del miembro creador no puede estar vacío")
    private MonedasDisponibles moneda;


    public OrganizacionDtoPostRequest(String nombreOrganizacion, String descripcionOrganizacion, String nickMiembroCreador, MonedasDisponibles moneda) {
        this.nombreOrganizacion = nombreOrganizacion;
        this.descripcionOrganizacion = descripcionOrganizacion;
        this.nickMiembroCreador = nickMiembroCreador;
        this.moneda = moneda;
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

    public MonedasDisponibles getMoneda() {
        return moneda;
    }

    public void setMoneda(MonedasDisponibles moneda) {
        this.moneda = moneda;
    }
}
