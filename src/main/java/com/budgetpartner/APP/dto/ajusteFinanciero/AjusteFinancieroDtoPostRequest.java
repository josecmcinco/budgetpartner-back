package com.budgetpartner.APP.dto.ajusteFinanciero;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class AjusteFinancieroDtoPostRequest {

    @NotNull(message = "El ID del pagador no puede ser nulo")
    private Long pagadorId;

    @NotNull(message = "El ID del beneficiario no puede ser nulo")
    private Long beneficiarioId;

    @NotNull(message = "La cantidad no puede ser nula")
    private Double cantidad;

    public AjusteFinancieroDtoPostRequest(Long pagadorId, Long beneficiarioId, Double cantidad) {
        this.pagadorId = pagadorId;
        this.beneficiarioId = beneficiarioId;
        this.cantidad = cantidad;
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
}
