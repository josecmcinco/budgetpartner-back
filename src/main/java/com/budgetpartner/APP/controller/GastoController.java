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
            description = "Crea un gasto nuevo dado su PENDIENTE. Devuelve el objeto creado como confirmación.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Gasto creado correctamente",
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
    @PostMapping
    public ResponseEntity<GastoDtoResponse> postGasto(@Validated @NotNull @RequestBody GastoDtoPostRequest gastoDtoReq) {
        Gasto gasto = gastoService.postGasto(gastoDtoReq);
        GastoDtoResponse gastoDtoResp = GastoMapper.toDtoResponse(gasto);
        return ResponseEntity.ok(gastoDtoResp);
    }

    @Operation(
            summary = "Crear un gasto",
            description = "Devuelve un gasto existente dado un id.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Gasto creado correctamente",
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
        GastoDtoResponse gastoDtoResp = gastoService.getGastoByIdAndTransform(id);
        return ResponseEntity.ok(gastoDtoResp);
    }


    @Operation(
            summary = "Crear un gasto",
            description = "Crea un gasto nuevo dado su PENDIENTE. Devuelve un mensaje de confirmación.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Gasto creado correctamente",
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
    @PatchMapping({"/{id}"})
    public ResponseEntity<String> patchGastoById(@Validated @NotNull @RequestBody GastoDtoUpdateRequest gastoDtoUpReq,
                                               @PathVariable Long id) {
        Gasto gasto = gastoService.patchGasto(gastoDtoUpReq, id);
        return ResponseEntity.ok("Gasto actualizado correctamente");
    }

    @Operation(
            summary = "Eliminar un gasto por ID",
            description = "Elimina un gasto existente dado su ID. Devuelve un mensaje de confirmación.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Gasto eliminado correctamente",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(
                                            name = "MensajeConfirmacion",
                                            summary = "Mensaje de éxito",
                                            value = "\"Gasto eliminado correctamente\""
                                    )
                            )//Content
                    )}
    )

    @DeleteMapping({"/{id}"})
    public ResponseEntity<String> deleteGastoById(@Validated @NotNull @PathVariable Long id) {
        Gasto gasto = gastoService.deleteGastoById(id);
        GastoDtoResponse gastoDtoResp = GastoMapper.toDtoResponse(gasto);
        return ResponseEntity.ok("Gasto eliminado correctamente");
    }


}
