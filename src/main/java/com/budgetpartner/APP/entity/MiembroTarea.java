package com.budgetpartner.APP.entity;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "miembro_tarea")
public class MiembroTarea implements Serializable {

    @EmbeddedId
    private MiembroTareaId id;

    @ManyToOne(cascade = CascadeType.MERGE, optional = false)
    @MapsId("miembroId")
    @JoinColumn(name = "miembro_id", nullable = false)
    private Miembro miembro;

    @ManyToOne(cascade = CascadeType.MERGE, optional = false)
    @MapsId("tareaId")
    @JoinColumn(name = "tarea_id", nullable = false)
    private Tarea tarea;

    public MiembroTarea() {}

    public MiembroTarea(Miembro miembro, Tarea tarea) {
        this.miembro = miembro;
        this.tarea = tarea;
        this.id = new MiembroTareaId(miembro.getId(), tarea.getId());
    }

    public MiembroTareaId getId() {
        return id;
    }

    public void setId(MiembroTareaId id) {
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
            this.id = new MiembroTareaId();
        }
        this.id.setMiembroId(miembro.getId());
    }

    public void setTarea(Tarea tarea) {
        this.tarea = tarea;
        if (this.id == null) {
            this.id = new MiembroTareaId();
        }
        this.id.setTareaId(tarea.getId());
    }
}
