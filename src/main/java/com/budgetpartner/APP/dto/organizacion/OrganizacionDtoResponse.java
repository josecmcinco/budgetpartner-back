package com.budgetpartner.APP.dto.organizacion;

import com.budgetpartner.APP.dto.miembro.MiembroDtoResponse;
import com.budgetpartner.APP.dto.plan.PlanDtoResponse;
import com.budgetpartner.APP.entity.Miembro;
import com.budgetpartner.APP.entity.Plan;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrganizacionDtoResponse {
    /*
        SE PRESCINDE DE LAS SIGUIENTES VARIABLES PARA EL DTO:
        creadoEn-actualizadoEn
    */

    /*
TODO IMAGEN
 */

    private Long id;
    private String nombre;
    private String descripcion;
    private List<MiembroDtoResponse> miembros;
    private List<PlanDtoResponse> planes;
    private Integer numeroMiembros = null;
    private Long idCreador; //TODO

    public OrganizacionDtoResponse(Long id, String nombre, String descripcion) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public List<MiembroDtoResponse> getMiembros() {
        return miembros;
    }

    public void setMiembros(List<MiembroDtoResponse> miembros) {
        this.miembros = miembros;
    }

    public List<PlanDtoResponse> getPlanes() {
        return planes;
    }

    public void setPlanes(List<PlanDtoResponse> planes) {
        this.planes = planes;
    }

    public Integer getNumeroMiembros() {
        return numeroMiembros;
    }

    public void setNumeroMiembros(int numeroMiembros) {
        this.numeroMiembros = Integer.valueOf(numeroMiembros);
    }

    public void setMiembroCreador(Long idCreador) {
        this.idCreador = idCreador;
    }

}
