package com.budgetpartner.APP.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table
public class AjusteFinanciero {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name= "organizador_id")
    private Organizacion organizacion;

    @ManyToOne
    @JoinColumn(name= "pagador_id")
    private Miembro pagador;

    @ManyToOne
    @JoinColumn(name= "benficiario_id")
    private Miembro beneficiario;

    @Column
    private BigDecimal cantidad;

    @Column
    private LocalDateTime creadoEn;

    @Column
    private LocalDateTime actualizadoEn;

    public AjusteFinanciero() {
    }

    public AjusteFinanciero(Organizacion organizacion, Miembro pagador, Miembro beneficiario, BigDecimal cantidad) {
        this.organizacion = organizacion;
        this.pagador = pagador;
        this.beneficiario = beneficiario;
        this.cantidad = cantidad;

        //Generado automáticamente
        this.creadoEn = LocalDateTime.now();
        this.actualizadoEn = LocalDateTime.now();
    }

    public AjusteFinanciero(Long id, Organizacion organizacion, Miembro pagador, Miembro beneficiario, BigDecimal cantidad, LocalDateTime creadoEn, LocalDateTime actualizadoEn) {
        this.id = id;
        this.organizacion = organizacion;
        this.pagador = pagador;
        this.beneficiario = beneficiario;
        this.cantidad = cantidad;
        this.creadoEn = creadoEn;
        this.actualizadoEn = actualizadoEn;
    }

    public Long getId() {
        return id;
    }


    public Organizacion getOrganizacion() {
        return organizacion;
    }

    public void setOrganizacion(Organizacion organizacion) {
        this.organizacion = organizacion;
    }

    public Miembro getPagador() {
        return pagador;
    }

    public void setPagador(Miembro pagador) {
        this.pagador = pagador;
    }

    public Miembro getBeneficiario() {
        return beneficiario;
    }

    public void setBeneficiario(Miembro beneficiario) {
        this.beneficiario = beneficiario;
    }

    public double getCantidad() {
        return cantidad.doubleValue();
    }

    public void setCantidad(double cantidad) {
        this.cantidad = BigDecimal.valueOf(cantidad);
    }

    public LocalDateTime getCreadoEn() {
        return creadoEn;
    }

    public void setCreadoEn(LocalDateTime creadoEn) {
        this.creadoEn = creadoEn;
    }

    public LocalDateTime getActualizadoEn() {
        return actualizadoEn;
    }

    public void setActualizadoEn(LocalDateTime actualizadoEn) {
        this.actualizadoEn = actualizadoEn;
    }
}
