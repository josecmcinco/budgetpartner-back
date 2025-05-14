package com.budgetpartner.APP.domain;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table
public class Miembro {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario_origen;

    @ManyToOne
    @JoinColumn(name = "id_organizacion")
    private Organizacion organizacion_origen;

    @ManyToOne
    @JoinColumn(name = "id_rol")
    private Rol rol_miembro;

    @Column
    private String nick;

    @Column
    private LocalDateTime fecha_ingreso;

    @Column
    private boolean isActivo;

    @Column
    private LocalDateTime creado_en;

    @Column
    private LocalDateTime actualizado_en;

    //Constructor vacío para Hibernate
    public Miembro(){}

    //Creación de Miembro desde 0
    //POR DEFECTO NO TIENE USUARIO ADJUNTO
    public Miembro(Organizacion organizacion_origen, Rol rol_miembro, String nick) {
        this.organizacion_origen = organizacion_origen;
        this.rol_miembro = rol_miembro;
        this.nick = nick;
        this.fecha_ingreso = fecha_ingreso;

        //Generado automáticamente
        this.actualizado_en = LocalDateTime.now();
        this.creado_en = LocalDateTime.now();
        this.isActivo=false;
    }

    //Extracción de Miembro de la DB
    public Miembro(Long id, Usuario usuario_origen, Organizacion organizacion_origen, Rol rol_miembro, String nick, LocalDateTime fecha_ingreso, boolean esta_activo, LocalDateTime creado_en, LocalDateTime actualizado_en) {
        this.id = id;
        this.usuario_origen = usuario_origen;
        this.organizacion_origen = organizacion_origen;
        this.rol_miembro = rol_miembro;
        this.nick = nick;
        this.fecha_ingreso = fecha_ingreso;
        this.isActivo = esta_activo;
        this.creado_en = creado_en;
        this.actualizado_en = actualizado_en;
    }

    //Asociación de un miembro a un Usuario de la DB
    //TODO SOLO si es usuario está vacio
    public void asociarUsuario(Usuario usuario_origen) {
        this.usuario_origen = usuario_origen;
        this.isActivo = true;
        this.fecha_ingreso = LocalDateTime.now();
    }

    //TODO eliminar usuario del miembro si no tiene miembro

    //Getters y Setters
    public Long getId() {
        return id;
    }

    public Usuario getUsuario_origen() {
        return usuario_origen;
    }

    public Organizacion getOrganizacion_origen() {
        return organizacion_origen;
    }

    public Rol getRol_miembro() {
        return rol_miembro;
    }

    public String getNick() {
        return nick;
    }

    public LocalDateTime getFecha_ingreso() {
        return fecha_ingreso;
    }

    public boolean getisActivo() {
        return isActivo;
    }

    public LocalDateTime getCreado_en() {
        return creado_en;
    }

    public LocalDateTime getActualizado_en() {
        return actualizado_en;
    }

    public void setRol_miembro(Rol rol_miembro) {
        this.rol_miembro = rol_miembro;
        this.actualizado_en = LocalDateTime.now();
    }

    public void setNick(String nick) {
        this.nick = nick;
        this.actualizado_en = LocalDateTime.now();
    }
}
