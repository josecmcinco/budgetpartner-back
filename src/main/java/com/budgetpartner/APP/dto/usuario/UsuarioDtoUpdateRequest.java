package com.budgetpartner.APP.dto.usuario;

public class UsuarioDtoUpdateRequest {

    /*
    SE PRESCINDE DE LAS SIGUIENTES VARIABLES PARA EL DTO:
    contraseña-creadoEn-actualizadoEn-id
    SE MODIFICA EL TIMPO DE LAS SIGUIENTES VARIABLES:
    miembrosDelUsuario
     */

    private Long id;
    private String email;
    private String nombre;
    private String apellido;
    private String contraseña;

    public UsuarioDtoUpdateRequest(Long id, String email, String nombre, String apellido, String contraseña) {
        this.id = id;
        this.email = email;
        this.nombre = nombre;
        this.apellido = apellido;
        this.contraseña = contraseña;
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

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }
}
