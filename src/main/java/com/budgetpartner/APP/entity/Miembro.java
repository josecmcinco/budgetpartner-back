package com.budgetpartner.APP.entity;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table
public class Miembro {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuarioOrigen;

    @ManyToOne
    @JoinColumn(name = "id_organizacion")
    private Organizacion organizacionOrigen;

    @ManyToOne
    @JoinColumn(name = "id_rol")
    private Rol rolMiembro;

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

    //Constructor vacío para Hibernate
    public Miembro(){}

    //Creación de Miembro desde 0
    //POR DEFECTO NO TIENE USUARIO ADJUNTO
    public Miembro(Organizacion organizacionOrigen, Rol rolMiembro, String nick) {
        this.organizacionOrigen = organizacionOrigen;
        this.rolMiembro = rolMiembro;
        this.nick = nick;
        this.fechaIngreso = fechaIngreso;

        //Generado automáticamente
        this.actualizadoEn = LocalDateTime.now();
        this.creadoEn = LocalDateTime.now();
        this.isActivo=false;
    }

    //Extracción de Miembro de la DB
    public Miembro(Long id, Usuario usuarioOrigen, Organizacion organizacionOrigen, Rol rolMiembro, String nick, LocalDateTime fechaIngreso, boolean isActivo, LocalDateTime creadoEn, LocalDateTime actualizadoEn) {
        this.id = id;
        this.usuarioOrigen = usuarioOrigen;
        this.organizacionOrigen = organizacionOrigen;
        this.rolMiembro = rolMiembro;
        this.nick = nick;
        this.fechaIngreso = fechaIngreso;
        this.isActivo = isActivo;
        this.creadoEn = creadoEn;
        this.actualizadoEn = actualizadoEn;
    }

    //Asociación de un miembro a un Usuario de la DB
    //TODO SOLO si es usuario está vacio
    public void asociarUsuario(Usuario usuarioOrigen) {
        this.usuarioOrigen = usuarioOrigen;
        this.isActivo = true;
        this.fechaIngreso = LocalDateTime.now();
    }

    //TODO eliminar usuario del miembro si no tiene miembro

    //Getters y Setters
    public Long getId() {
        return id;
    }

    public Usuario getUsuarioOrigen() {
        return usuarioOrigen;
    }

    public Organizacion getOrganizacionOrigen() {
        return organizacionOrigen;
    }

    public Rol getRolMiembro() {
        return rolMiembro;
    }

    public String getNick() {
        return nick;
    }

    public LocalDateTime getFechaIngreso() {
        return fechaIngreso;
    }

    public boolean getisActivo() {
        return isActivo;
    }

    public LocalDateTime getCreadoEn() {
        return creadoEn;
    }

    public LocalDateTime getActualizadoEn() {
        return actualizadoEn;
    }

    public void setRolMiembro(Rol rolMiembro) {
        this.rolMiembro = rolMiembro;
        this.actualizadoEn = LocalDateTime.now();
    }

    public void setNick(String nick) {
        this.nick = nick;
        this.actualizadoEn = LocalDateTime.now();
    }
}
