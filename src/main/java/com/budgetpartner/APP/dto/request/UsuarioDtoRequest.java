package com.budgetpartner.APP.dto.request;

import com.budgetpartner.APP.entity.Miembro;

import java.util.List;

public class UsuarioDtoRequest {

    /*
    SE PRESCINDE DE LAS SIGUIENTES VARIABLES PARA EL DTO:
    contraseña-creadoEn-actualizadoEn-id
    SE MODIFICA EL TIMPO DE LAS SIGUIENTES VARIABLES:
    miembrosDelUsuario
     */

    private String email;
    private String nombre;
    private String apellido;
    private String contraseña;

    public UsuarioDtoRequest(String email, String nombre, String apellido, String contraseña) {
        this.email = email;
        this.nombre = nombre;
        this.apellido = apellido;
        this.contraseña = contraseña;
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

    public String getContraseña() {
        return contraseña;
    }
    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }
}
