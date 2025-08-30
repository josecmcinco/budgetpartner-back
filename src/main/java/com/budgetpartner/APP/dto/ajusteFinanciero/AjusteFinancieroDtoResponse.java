package com.budgetpartner.APP.dto.ajusteFinanciero;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AjusteFinancieroDtoResponse {

    private Long id;

    private Long organizacionId;

    private Long pagadorId;

    private Long beneficiarioId;

    private Double cantidad;

    private LocalDateTime creadoEn;

    public AjusteFinancieroDtoResponse(Long id, Long organizacionId, Long pagadorId, Long beneficiarioId, Double cantidad, LocalDateTime creadoEn) {
        this.id = id;
        this.pagadorId = pagadorId;
        this.beneficiarioId = beneficiarioId;
        this.cantidad = cantidad;
        this.creadoEn = creadoEn;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrganizacionId() {
        return organizacionId;
    }

    public void setOrganizacionId(Long organizacionId) {
        this.organizacionId = organizacionId;
    }

    public Long getPagadorId() {
        return pagadorId;
    }

    public void setPagadorId(Long pagadorId) {
        this.pagadorId = pagadorId;
    }

    public Long getBeneficiarioId() {
        return beneficiarioId;
    }

    public void setBeneficiarioId(Long beneficiarioId) {
        this.beneficiarioId = beneficiarioId;
    }

    public Double getCantidad() {
        return cantidad;
    }

    public void setCantidad(Double cantidad) {
        this.cantidad = cantidad;
    }

    public LocalDateTime getCreadoEn() {
        return creadoEn;
    }

    public void setCreadoEn(LocalDateTime creadoEn) {
        this.creadoEn = creadoEn;
    }
}
