package com.budgetpartner.APP.dto.tarea;

import com.budgetpartner.APP.enums.EstadoTarea;
import com.budgetpartner.APP.enums.MonedasDisponibles;

import java.time.LocalDateTime;
import java.util.List;

public class TareaDtoUpdateRequest {

    /*
        SE PRESCINDE DE LAS SIGUIENTES VARIABLES PARA EL DTO:
        id-creadoEn-actualizadoEn
    */
    private Long id;
    private String titulo;
    private String descripcion;
    private LocalDateTime fechaFin;
    private EstadoTarea estado;
    private List<Long> listaAtareados;

    public TareaDtoUpdateRequest(String titulo, String descripcion, LocalDateTime fechaFin, EstadoTarea estado, List<Long> listaAtareados) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fechaFin = fechaFin;
        this.estado = estado;
        this.listaAtareados = listaAtareados;

    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public LocalDateTime getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDateTime fechaFin) {
        this.fechaFin = fechaFin;
    }

    public EstadoTarea getEstado() {
        return estado;
    }

    public void setEstado(EstadoTarea estado) {
        this.estado = estado;
    }


    public List<Long> getListaAtareados() {
        return listaAtareados;
    }

    public void setListaAtareados(List<Long> listaAtareados) {
        this.listaAtareados = listaAtareados;
    }
}
