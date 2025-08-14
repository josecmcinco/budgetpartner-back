package com.budgetpartner.APP.dto.usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class UsuarioDtoPostRequest {

    /*
    SE PRESCINDE DE LAS SIGUIENTES VARIABLES PARA EL DTO:
    contraseña-creadoEn-actualizadoEn-id
    SE MODIFICA EL TIMPO DE LAS SIGUIENTES VARIABLES:
    miembrosDelUsuario
     */

    @NotBlank(message = "El correo electrónico no puede estar vacío")
    @Email(message = "El correo electrónico no es válido")
    private String email;

    @NotBlank(message = "El nombre no puede estar vacío")
    private String nombre;

    @NotBlank(message = "El apellido no puede estar vacío")
    private String apellido;

    @NotBlank(message = "La contraseña no puede estar vacío")
    private String contraseña;

    public UsuarioDtoPostRequest(String email, String nombre, String apellido, String contraseña) {
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
