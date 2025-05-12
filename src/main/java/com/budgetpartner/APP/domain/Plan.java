package com.budgetpartner.APP.domain;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table
public class Plan {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_organizacion")
    private Organizacion organizacion;

    @Column
    private String nombre;

    @Column
    private String descripcion;

    @Column
    private LocalDateTime fecha_inicio;

    @Column
    private LocalDateTime fecha_fin;

    @Column
    private LocalDateTime creado_en;

    @Column
    private LocalDateTime actualizado_en;

}
