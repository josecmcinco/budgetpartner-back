package com.budgetpartner.APP.controller;


import com.budgetpartner.APP.dto.dashborard.DashboardDtoResponse;
import com.budgetpartner.APP.dto.gasto.GastoDtoResponse;
import com.budgetpartner.APP.dto.token.TokenDtoRequest;
import com.budgetpartner.APP.service.DashboardService;
import com.budgetpartner.APP.service.GastoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/dashboard")
public class DashboardController {

    @Autowired
    DashboardService dashboardService;

    @Operation(
            summary = "Devolver la información del dashboard",
            description = "Devuelve la información del dashboard usando el JWT.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Información devuelta correctamente",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(
                                            name = "MensajeConfirmacion",
                                            summary = "Mensaje de éxito",
                                            value = "PENDIENTE"
                                    )
                            )//Content
                    )}
    )
    @GetMapping
    public ResponseEntity<DashboardDtoResponse> getDashboard() {
        DashboardDtoResponse dashboardDtoResp = dashboardService.getDashboard();
        return ResponseEntity.ok(dashboardDtoResp);
    }


}
