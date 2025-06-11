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
    @JoinColumn(name = "usuario_id")
    private Usuario usuarioOrigen;

    @ManyToOne
    @JoinColumn(name = "organizacion_id")
    private Organizacion organizacionOrigen;

    @ManyToOne
    @JoinColumn(name = "rol_id")
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
    public Miembro(Organizacion organizacionOrigen, Rol rolMiembro, String nick, boolean isActivo) {
        this.organizacionOrigen = organizacionOrigen;
        this.rolMiembro = rolMiembro;
        this.nick = nick;
        this.isActivo = isActivo;

        //Generado automáticamente
        this.actualizadoEn = LocalDateTime.now();
        this.creadoEn = LocalDateTime.now();

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
    //TODO SOLO si la variable usuario está vacía
    public void asociarUsuario(Usuario usuarioOrigen) {
        this.usuarioOrigen = usuarioOrigen;
        this.isActivo = true;
        this.fechaIngreso = LocalDateTime.now();
        this.actualizadoEn = LocalDateTime.now();
    }

    public void desasociarUsuario() {
        this.usuarioOrigen = null;
        this.isActivo = false;
        this.fechaIngreso = null;
        this.actualizadoEn = LocalDateTime.now();
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

    public boolean getIsActivo() {
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
