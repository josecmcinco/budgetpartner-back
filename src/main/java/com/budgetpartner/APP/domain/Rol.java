package com.budgetpartner.APP.domain;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table
public class Rol {

    @Id
    @GeneratedValue
    private int id;

    @Column
    private String nombre;

    @Column
    private String permisos;

    @Column
    private LocalDateTime creado_en;

    @Column
    private LocalDateTime actualizado_en;


}
