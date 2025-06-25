package com.budgetpartner.APP.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "reparto_gastos")
public class RepartoGasto {

    @EmbeddedId
    private RepartoGastoId id;

    @ManyToOne
    @MapsId("gastoId")  // Mapea gastoId del ID compuesto
    @JoinColumn(name = "gasto_id")
    private Gasto gasto;

    @ManyToOne
    @MapsId("miembroId") // Mapea miembroId del ID compuesto
    @JoinColumn(name = "miembro_id")
    private Miembro miembro;

    private double cantidad;

    //Constructor vacío para Hibernate
    public RepartoGasto() {}

    //Crear partición gasto desde 0
    public RepartoGasto(Gasto gasto, Miembro miembro, double cantidad) {
        this.gasto = gasto;
        this.miembro = miembro;
        this.cantidad = cantidad;
        this.id = new RepartoGastoId(gasto.getId(), miembro.getId());
        
    }

    public RepartoGastoId getId() {
        return id;
    }

    public void setId(RepartoGastoId id) {
        this.id = id;
    }

    public Gasto getGasto() {
        return gasto;
    }

    public void setGasto(Gasto gasto) {
        this.gasto = gasto;
    }

    public Miembro getMiembro() {
        return miembro;
    }

    public void setMiembro(Miembro miembro) {
        this.miembro = miembro;
    }

    public double getCantidad() {
        return cantidad;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }
}