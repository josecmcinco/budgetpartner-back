package com.budgetpartner.APP.dto.tarea;

import com.budgetpartner.APP.enums.EstadoTarea;
import com.budgetpartner.APP.enums.MonedasDisponibles;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.List;

public class TareaDtoPostRequest {

    /*
        SE PRESCINDE DE LAS SIGUIENTES VARIABLES PARA EL DTO:
        id-creadoEn-actualizadoEn
    */
    @NotNull(message = "El ID del plan no puede ser nulo")
    private Long planId;

    @NotBlank(message = "El título no puede estar vacío")
    private String titulo;

    private String descripcion;

    @NotNull(message = "La fecha de fin no puede ser nula")
    private LocalDateTime fechaFin;

    private EstadoTarea estado;


    @NotEmpty(message = "La lista de atareados no puede estar vacía")
    private List<@NotNull(message = "Cada ID en la lista de atareados debe ser válido") Long> listaAtareados;


    public TareaDtoPostRequest(Long planId, String titulo, String descripcion, LocalDateTime fechaFin, EstadoTarea estado, List<Long> listaAtareados) {
        this.planId = planId;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fechaFin = fechaFin;
        this.estado = estado;
        this.listaAtareados = listaAtareados;
    }


    public Long getPlanId() {
        return planId;
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
