package com.budgetpartner.APP.domain;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table
public class Organizacion {
    @Id
    private Long id;

    @Column
    private String descripcion;

    @Column
    private LocalDateTime creado_en;

    @Column
    private LocalDateTime actualizado_en;

}
