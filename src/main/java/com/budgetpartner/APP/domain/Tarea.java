package com.budgetpartner.APP.domain;
import java.time.LocalDateTime;
import java.util.List;

import com.budgetpartner.APP.enums.EstadoTarea;
import jakarta.persistence.*;

@Entity
@Table
public class Tarea {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_plan")
    private Plan plan;

    @Column
    private String titulo;

    @Column
    private String descripcion;

    @Column
    private LocalDateTime fechaFin;

    @Enumerated
    private EstadoTarea estado;

    @Column
    private double costeEstimado;

    @Column
    private String moneda;

    @Column
    private LocalDateTime creado_en;

    @Column
    private LocalDateTime actualizado_en;


    @ManyToMany
    @JoinTable(name = "miembro_tarea",
            joinColumns = @JoinColumn(name = "tarea_id"),
            inverseJoinColumns = @JoinColumn(name = "miembro_id")
    )
    private List<Miembro> miembros;
}
