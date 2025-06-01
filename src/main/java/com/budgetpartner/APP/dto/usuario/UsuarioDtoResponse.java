package com.budgetpartner.APP.dto.usuario;

import com.budgetpartner.APP.entity.Miembro;

import java.util.List;

public class UsuarioDtoResponse {

    /*
    SE PRESCINDE DE LAS SIGUIENTES VARIABLES PARA EL DTO:
    contraseña-creadoEn-actualizadoEn
    SE MODIFICA EL TIMPO DE LAS SIGUIENTES VARIABLES:
    miembrosDelUsuario
     */

    private Long id;
    private String email;
    private String nombre;
    private String apellido;
    private List<Miembro> miembrosDelUsuario;

    public UsuarioDtoResponse(Long id, String email, String nombre, String apellido, List<Miembro> miembrosDelUsuario) {
        this.id = id;
        this.email = email;
        this.nombre = nombre;
        this.apellido = apellido;
        this.miembrosDelUsuario = miembrosDelUsuario;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public List<Miembro> getMiembrosDelUsuario() {
        return miembrosDelUsuario;
    }

    public void setMiembrosDelUsuario(List<Miembro> miembrosDelUsuario) {
        this.miembrosDelUsuario = miembrosDelUsuario;
    }
}
