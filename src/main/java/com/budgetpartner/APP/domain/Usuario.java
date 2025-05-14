package com.budgetpartner.APP.domain;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;


@Entity
@Table
public class Usuario {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String email;

    @Column
    private String nombre;

    @Column
    private String apellido;

    @Column
    private String contraseña;

    @Column
    private LocalDateTime creado_en;

    @Column
    private LocalDateTime actualizado_en;

    @Transient
    private List<Miembro> miembros_del_usuario;

    //Constructor Usuario para Hibernate
    public Usuario(){}

    //Creación Usuario desde cero
    public Usuario(String email, String nombre, String apellido, String contraseña) {
        this.id = null;
        this.email = email;
        this.nombre = nombre;
        this.apellido = apellido;
        this.contraseña = contraseña;

        //Generado automáticamente
        this.actualizado_en = LocalDateTime.now();
        this.creado_en = LocalDateTime.now();

    }

    //Extracción Usuario ya creado de la DB
    public Usuario(Long id, String email, String nombre, String apellido, String contraseña, LocalDateTime creado_en, LocalDateTime actualizado_en, List<Miembro> miembros_del_usuario) {
        this.id = id;
        this.email = email;
        this.nombre = nombre;
        this.apellido = apellido;
        this.contraseña = contraseña;
        this.creado_en = creado_en;
        this.actualizado_en = actualizado_en;
        this.miembros_del_usuario = miembros_del_usuario;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
        this.actualizado_en = LocalDateTime.now();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
        this.actualizado_en = LocalDateTime.now();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
        this.actualizado_en = LocalDateTime.now();
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
        this.actualizado_en = LocalDateTime.now();
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
        this.actualizado_en = LocalDateTime.now();
    }

    public LocalDateTime getCreado_en() {
        return creado_en;
    }

    public LocalDateTime getActualizado_en() {
        return actualizado_en;
    }

    public List<Miembro> getMiembros_del_usuario() {
        return miembros_del_usuario;
    }

    public void steMiembros_del_usuario(Miembro miembro_del_usuario) {
        this.miembros_del_usuario.add(miembro_del_usuario);
        this.actualizado_en = LocalDateTime.now();
    }

    public void eliminarMiembros_del_usuario(Miembro miembros_del_usuario) {
        this.miembros_del_usuario.add(miembros_del_usuario);
        this.actualizado_en = LocalDateTime.now();
    }
}
