package com.budgetpartner.APP.entity;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
public class Miembro {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "organizacion_id")
    private Organizacion organizacion;

    @ManyToOne
    @JoinColumn(name = "rol_id")
    private Rol rol;

    @Column
    private String nick;

    @Column
    private LocalDateTime fechaIngreso;

    @Column
    private boolean isActivo;

    @Column
    private boolean isAsociado;

    @Column
    private LocalDateTime creadoEn;

    @Column
    private LocalDateTime actualizadoEn;

    @OneToMany(mappedBy = "miembro", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RepartoGasto> repartos = new ArrayList<>();

    @OneToMany(mappedBy = "miembro", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RepartoTarea> miembroTareas = new ArrayList<>();


    //Constructor vacío para Hibernate
    public Miembro(){}

    //Creación de Miembro desde 0
    //POR DEFECTO NO TIENE USUARIO ADJUNTO
    public Miembro(Organizacion organizacion, Rol rol, String nick) {
        this.organizacion = organizacion;
        this.rol = rol;
        this.nick = nick;
        this.isActivo = true;

        //Generado automáticamente
        this.actualizadoEn = LocalDateTime.now();
        this.creadoEn = LocalDateTime.now();

    }

    //Extracción de Miembro de la DB
    public Miembro(Long id, Usuario usuario, Organizacion organizacion, Rol rol, String nick, LocalDateTime fechaIngreso, boolean isActivo, boolean isAsociado, LocalDateTime creadoEn, LocalDateTime actualizadoEn) {
        this.id = id;
        this.usuario = usuario;
        this.organizacion = organizacion;
        this.rol = rol;
        this.nick = nick;
        this.fechaIngreso = fechaIngreso;
        this.isActivo = isActivo;
        this.isAsociado = isAsociado;
        this.creadoEn = creadoEn;
        this.actualizadoEn = actualizadoEn;
    }

    //Getters y Setters
    public Long getId() {
        return id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public Organizacion getOrganizacion() {
        return organizacion;
    }

    public Rol getRol() {
        return rol;
    }

    public String getNick() {
        return nick;
    }

    public LocalDateTime getFechaIngreso() {
        return fechaIngreso;
    }

    public LocalDateTime getCreadoEn() {
        return creadoEn;
    }

    public LocalDateTime getActualizadoEn() {
        return actualizadoEn;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
        this.actualizadoEn = LocalDateTime.now();
    }

    public void setNick(String nick) {
        this.nick = nick;
        this.actualizadoEn = LocalDateTime.now();
    }

    public void setUsuario(Usuario usuario) {
        this.actualizadoEn = LocalDateTime.now();
        this.usuario = usuario;
    }

    public void setFechaIngreso(LocalDateTime fechaIngreso) {

        this.actualizadoEn = LocalDateTime.now();
        this.fechaIngreso = fechaIngreso;
    }

    public boolean isActivo() {
        return isActivo;
    }

    public void setActivo(boolean activo) {

        this.actualizadoEn = LocalDateTime.now();
        isActivo = activo;
    }

    public boolean isAsociado() {
        return isAsociado;
    }

    public void setAsociado(boolean asociado) {
        isAsociado = asociado;
    }
}
