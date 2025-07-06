package com.budgetpartner.APP.entity;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class RepartoTareaId implements Serializable {

    private Long miembroId;
    private Long tareaId;

    public RepartoTareaId() {}

    public RepartoTareaId(Long miembroId, Long tareaId) {
        this.miembroId = miembroId;
        this.tareaId = tareaId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RepartoTareaId)) return false;
        RepartoTareaId that = (RepartoTareaId) o;
        return Objects.equals(miembroId, that.miembroId) &&
                Objects.equals(tareaId, that.tareaId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(miembroId, tareaId);
    }

    public Long getMiembroId() {
        return miembroId;
    }

    public void setMiembroId(Long miembroId) {
        this.miembroId = miembroId;
    }

    public Long getTareaId() {
        return tareaId;
    }

    public void setTareaId(Long tareaId) {
        this.tareaId = tareaId;
    }
}
