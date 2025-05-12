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
    private List<Miembro> miembros_que_es_usuario;

}
