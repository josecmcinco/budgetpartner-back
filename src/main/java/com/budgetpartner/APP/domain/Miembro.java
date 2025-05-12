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
    private boolean esta_activo;

    @Column
    private LocalDateTime creado_en;

    @Column
    private LocalDateTime actualizado_en;


}
