package com.budgetpartner.APP.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class RepartoGastoId implements Serializable {

    @Column(name = "gasto_id")
    private Long gastoId;

    @Column(name = "miembro_id")
    private Long miembroId;

    // Constructor vacío obligatorio
    public RepartoGastoId() {}

    public RepartoGastoId(Long gastoId, Long miembroId) {
        this.gastoId = gastoId;
        this.miembroId = miembroId;
    }

    public Long getGastoId() {
        return gastoId;
    }

    public void setGastoId(Long gastoId) {
        this.gastoId = gastoId;
    }

    public Long getMiembroId() {
        return miembroId;
    }

    public void setMiembroId(Long miembroId) {
        this.miembroId = miembroId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RepartoGastoId that = (RepartoGastoId) o;
        return Objects.equals(gastoId, that.gastoId) &&
                Objects.equals(miembroId, that.miembroId);
    }


    @Override
    public int hashCode() {
        return Objects.hash(gastoId, miembroId);
    }
}