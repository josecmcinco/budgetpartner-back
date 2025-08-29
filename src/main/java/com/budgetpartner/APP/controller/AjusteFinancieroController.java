package com.budgetpartner.APP.controller;

import com.budgetpartner.APP.dto.ajusteFinanciero.AjusteFinancieroDtoPostRequest;
import com.budgetpartner.APP.dto.ajusteFinanciero.AjusteFinancieroDtoResponse;
import com.budgetpartner.APP.dto.gasto.GastoDtoPostRequest;
import com.budgetpartner.APP.dto.gasto.GastoDtoResponse;
import com.budgetpartner.APP.service.AjusteFinancieroService;
import com.budgetpartner.APP.service.GastoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/organizaciones/{organizacionId}/ajustes-financieros")
public class AjusteFinancieroController {
    @Autowired
    AjusteFinancieroService ajusteFinancieroService;

    @Operation(
            summary = "Crear un ajuste financiero",
            description = "Crea un ajuste financiero nuevo dados los valores pagador/beneficiario/cantidad. Devuelve un mensaje de confirmación.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Ajuste creado correctamente"
                    )}
    )
    @PostMapping
    public ResponseEntity<AjusteFinancieroDtoResponse> postGasto(@NotNull @RequestBody AjusteFinancieroDtoPostRequest ajusteDtoPostRequest,
                                                                @Validated @NotNull @PathVariable Long organizacionId) {
        AjusteFinancieroDtoResponse ajusteDtoRes = ajusteFinancieroService.postAjusteFinanciero(ajusteDtoPostRequest, organizacionId);
        return ResponseEntity.ok(ajusteDtoRes);
    }
}
