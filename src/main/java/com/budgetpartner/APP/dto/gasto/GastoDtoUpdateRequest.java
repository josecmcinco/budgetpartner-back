package com.budgetpartner.APP.dto.gasto;

import com.budgetpartner.APP.entity.Plan;

import java.util.List;

public class GastoDtoUpdateRequest {
    /*
    SE PRESCINDE DE LAS SIGUIENTES VARIABLES PARA EL DTO:
    id-creadoEn-actualizadoEn
    SE MODIFICA EL TIMPO DE LAS SIGUIENTES VARIABLES:
    miembrosDelUsuario
     */
//TODO solo parte como endeudados(lista) o siempre todos???
    private double cantidad;
    private String nombre;
    private Long pagadorId;
    private String descripcion;
    List<Long> listaMiembrosEndeudados;

    public GastoDtoUpdateRequest(double cantidad, String nombre, Long pagadorId, String descripcion, List<Long> listaMiembrosEndeudados) {
        this.cantidad = cantidad;
        this.nombre = nombre;
        this.pagadorId = pagadorId;
        this.descripcion = descripcion;
        this.listaMiembrosEndeudados = listaMiembrosEndeudados;
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

    public Long getPagadorId() {
        return pagadorId;
    }

    //public void setPagadorId(Long pagadorId) {this.pagadorId = pagadorId;}

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    public List<Long> getListaMiembrosEndeudados() {
        return listaMiembrosEndeudados;
    }

    public void setListaMiembrosEndeudados(List<Long> listaMiembrosEndeudados) {
        this.listaMiembrosEndeudados = listaMiembrosEndeudados;
    }
}
