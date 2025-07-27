package com.budgetpartner.APP.entity;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "reparto_tarea")
public class RepartoTarea implements Serializable {

    @EmbeddedId
    private RepartoTareaId id;

    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    @MapsId("miembroId")
    @JoinColumn(name = "miembro_id", nullable = false)
    private Miembro miembro;

    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    @MapsId("tareaId")
    @JoinColumn(name = "tarea_id", nullable = false)
    private Tarea tarea;

    public RepartoTarea() {}

    public RepartoTarea(Miembro miembro, Tarea tarea) {
        this.miembro = miembro;
        this.tarea = tarea;
        this.id = new RepartoTareaId(miembro.getId(), tarea.getId());
    }

    public RepartoTareaId getId() {
        return id;
    }

    public void setId(RepartoTareaId id) {
        this.id = id;
    }

    public Miembro getMiembro() {
        return miembro;
    }

    public Tarea getTarea() {
        return tarea;
    }

    public void setMiembro(Miembro miembro) {
        this.miembro = miembro;
        if (this.id == null) {
            this.id = new RepartoTareaId();
        }
        this.id.setMiembroId(miembro.getId());
    }

    public void setTarea(Tarea tarea) {
        this.tarea = tarea;
        if (this.id == null) {
            this.id = new RepartoTareaId();
        }
        this.id.setTareaId(tarea.getId());
    }
}
