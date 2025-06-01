package com.budgetpartner.APP.dto.organizacion;

import com.budgetpartner.APP.entity.Miembro;
import com.budgetpartner.APP.entity.Plan;

import java.util.List;

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
    private List<Miembro> miembros;
    private List<Plan> planes;

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

    public List<Miembro> getMiembros() {
        return miembros;
    }

    public void setMiembros(List<Miembro> miembros) {
        this.miembros = miembros;
    }

    public List<Plan> getPlanes() {
        return planes;
    }

    public void setPlanes(List<Plan> planes) {
        this.planes = planes;
    }
}
