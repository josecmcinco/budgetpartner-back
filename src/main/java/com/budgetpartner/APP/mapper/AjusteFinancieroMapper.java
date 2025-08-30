package com.budgetpartner.APP.mapper;

import com.budgetpartner.APP.dto.ajusteFinanciero.AjusteFinancieroDtoPostRequest;
import com.budgetpartner.APP.dto.ajusteFinanciero.AjusteFinancieroDtoResponse;
import com.budgetpartner.APP.entity.AjusteFinanciero;
import com.budgetpartner.APP.entity.Miembro;
import com.budgetpartner.APP.entity.Organizacion;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AjusteFinancieroMapper {

    /**
     * Convierte una entidad AjusteFinanciero a su DTO de respuesta.
     */
    public static AjusteFinancieroDtoResponse toDtoResponse(AjusteFinanciero ajuste) {
        if (ajuste == null) return null;

        return new AjusteFinancieroDtoResponse(
                ajuste.getId(),
                ajuste.getOrganizacion().getId(),
                ajuste.getPagador().getId(),
                ajuste.getBeneficiario().getId(),
                ajuste.getCantidad(),
                ajuste.getCreadoEn()
        );
    }

    /**
     * Convierte un DTO de creación a una entidad AjusteFinanciero.
     */
    public static AjusteFinanciero toEntity(
            AjusteFinancieroDtoPostRequest dto,
            Organizacion organizacion,
            Miembro pagador,
            Miembro beneficiario
    ) {
        if (dto == null) return null;

        AjusteFinanciero ajuste = new AjusteFinanciero();
        ajuste.setOrganizacion(organizacion);
        ajuste.setPagador(pagador);
        ajuste.setBeneficiario(beneficiario);
        ajuste.setCantidad(dto.getCantidad());
        ajuste.setCreadoEn(LocalDateTime.now());
        ajuste.setActualizadoEn(LocalDateTime.now());
        return ajuste;
    }


    /**
     * Convierte una lista de entidades AjusteFinanciero a una lista de DTOs de respuesta.
     */
    public static List<AjusteFinancieroDtoResponse> toDtoResponseList(List<AjusteFinanciero> ajustes) {
        if (ajustes == null || ajustes.isEmpty()) {
            return Collections.emptyList();
        }

        List<AjusteFinancieroDtoResponse> lista = new ArrayList<>();
        for (AjusteFinanciero ajuste : ajustes) {
            lista.add(toDtoResponse(ajuste));
        }
        return lista;
    }
}
