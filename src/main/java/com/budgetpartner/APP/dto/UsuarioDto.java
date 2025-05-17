package com.budgetpartner.APP.dto;

import com.budgetpartner.APP.domain.Miembro;

import java.lang.reflect.Array;
import java.time.LocalDateTime;
import java.util.List;

public class UsuarioDto {

    /*SE PRESCINDE DE LAS SIGUIENTES VARIABLES PARA EL DTO:
    id-contraseña-creadoEn-actualizadoEn
    SE MODIFICA EL TIMPO DE LAS SIGUIENTES VARIABLES
    miembrosDelUsuario
     */

    private String email;
    private String nombre;
    private String apellido;
    private List<String> miembrosDelUsuario;

    public UsuarioDto(String email, String nombre, String apellido, List<String> miembrosDelUsuario) {
        this.email = email;
        this.nombre = nombre;
        this.apellido = apellido;
        this.miembrosDelUsuario = miembrosDelUsuario;
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

    public List<String> getMiembrosDelUsuario() {
        return miembrosDelUsuario;
    }

    public void setMiembrosDelUsuario(List<String> miembrosDelUsuario) {
        this.miembrosDelUsuario = miembrosDelUsuario;
    }
}
