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
    public Miembro(Organizacion organizacion, Rol rol, String nick, boolean isActivo) {
        this.organizacion = organizacion;
        this.rol = rol;
        this.nick = nick;
        this.isActivo = isActivo;

        //Generado automáticamente
        this.actualizadoEn = LocalDateTime.now();
        this.creadoEn = LocalDateTime.now();

    }

    //Extracción de Miembro de la DB
    public Miembro(Long id, Usuario usuario, Organizacion organizacion, Rol rol, String nick, LocalDateTime fechaIngreso, boolean isActivo, LocalDateTime creadoEn, LocalDateTime actualizadoEn) {
        this.id = id;
        this.usuario = usuario;
        this.organizacion = organizacion;
        this.rol = rol;
        this.nick = nick;
        this.fechaIngreso = fechaIngreso;
        this.isActivo = isActivo;
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

    public boolean getIsActivo() {
        return isActivo;
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
        this.usuario = usuario;
    }

    public void setFechaIngreso(LocalDateTime fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public boolean isActivo() {
        return isActivo;
    }

    public void setActivo(boolean activo) {
        isActivo = activo;
    }
/*
    public List<Gasto> getGastosPagados() {
        return gastosPagados;
    }

    public void setGastosPagados(List<Gasto> gastosPagados) {
        this.gastosPagados = gastosPagados;
    }*/
}
