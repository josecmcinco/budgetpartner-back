package com.budgetpartner.APP.entity;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
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

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Miembro> miembros = new ArrayList<>();

    @Column
    private LocalDateTime creadoEn;

    @Column
    private LocalDateTime actualizadoEn;

    @Transient
    private List<Miembro> miembrosDelUsuario;

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
        this.actualizadoEn = LocalDateTime.now();
        this.creadoEn = LocalDateTime.now();

    }

    //Extracción Usuario ya creado de la DB
    public Usuario(Long id, String email, String nombre, String apellido, String contraseña, LocalDateTime creadoEn, LocalDateTime actualizadoEn) {
        this.id = id;
        this.email = email;
        this.nombre = nombre;
        this.apellido = apellido;
        this.contraseña = contraseña;
        this.creadoEn = creadoEn;
        this.actualizadoEn = actualizadoEn;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
        this.actualizadoEn = LocalDateTime.now();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
        this.actualizadoEn = LocalDateTime.now();
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
        this.actualizadoEn = LocalDateTime.now();
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
        this.actualizadoEn = LocalDateTime.now();
    }

    public LocalDateTime getCreadoEn() {
        return creadoEn;
    }

    public LocalDateTime getActualizadoEn() {
        return actualizadoEn;
    }

    public List<Miembro> getMiembrosDelUsuario() {
        return miembrosDelUsuario;
    }

    public void steMiembros_del_usuario(Miembro miembro_del_usuario) {
        this.miembrosDelUsuario.add(miembro_del_usuario);
        this.actualizadoEn = LocalDateTime.now();
    }

    public void eliminarMiembros_del_usuario(Miembro miembros_del_usuario) {
        this.miembrosDelUsuario.add(miembros_del_usuario);
        this.actualizadoEn = LocalDateTime.now();
    }
}
