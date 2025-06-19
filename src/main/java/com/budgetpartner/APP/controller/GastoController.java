package com.budgetpartner.APP.controller;

import com.budgetpartner.APP.dto.gasto.GastoDtoPostRequest;
import com.budgetpartner.APP.dto.gasto.GastoDtoResponse;
import com.budgetpartner.APP.dto.gasto.GastoDtoUpdateRequest;
import com.budgetpartner.APP.entity.Gasto;
import com.budgetpartner.APP.mapper.GastoMapper;
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
@RequestMapping("/gastos")
public class GastoController {
    @Autowired
    GastoService gastoService;

    @Operation(
            summary = "Crear un gasto",
            description = "Crea un gasto nuevo dado su planId/tareaId/cantidad/nombre/pagadorId/descripcion. Devuelve un mensaje de confirmación.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Gasto creado correctamente"
                    )}
    )
    @PostMapping
    public ResponseEntity<String> postGasto(@NotNull @RequestBody GastoDtoPostRequest gastoDtoReq) {
        gastoService.postGasto(gastoDtoReq);
        return ResponseEntity.ok("Gasto creado correctamente");
    }

    @Operation(
            summary = "Devolver un gasto",
            description = "Devuelve un gasto existente dado un id. Además se devuelve PENDIENTE", //TDOD
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Gasto devuelto correctamente",
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
    @GetMapping({"/{id}"})
    public ResponseEntity<GastoDtoResponse> getGastoById(@Validated @NotNull @PathVariable Long id) {
        GastoDtoResponse gastoDtoResp = gastoService.getGastoDtoById(id);
        return ResponseEntity.ok(gastoDtoResp);
    }


    @Operation(
            summary = "Actualiza un gasto",
            description = "Actualiza las variables cantidad/nombre/pagadorId/descripcion de un gasto. Devuelve un mensaje de confirmación.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Gasto actualizado correctamente"
                    )}
    )
    @PatchMapping({"/{id}"})
    public ResponseEntity<String> patchGastoById(@Validated @NotNull @RequestBody GastoDtoUpdateRequest gastoDtoUpReq,
                                                 @Validated @NotNull @PathVariable Long id) {
        Gasto gasto = gastoService.patchGasto(gastoDtoUpReq, id);
        return ResponseEntity.ok("Gasto actualizado correctamente");
    }

    @Operation(
            summary = "Eliminar un gasto por ID",
            description = "Elimina un gasto existente dado su ID. Devuelve un mensaje de confirmación.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Gasto eliminado correctamente"
                    )}
    )
    @DeleteMapping({"/{id}"})
    public ResponseEntity<String> deleteGastoById(@Validated @NotNull @PathVariable Long id) {
        Gasto gasto = gastoService.deleteGastoById(id);
        GastoDtoResponse gastoDtoResp = GastoMapper.toDtoResponse(gasto);
        return ResponseEntity.ok("Gasto eliminado correctamente");
    }


}
