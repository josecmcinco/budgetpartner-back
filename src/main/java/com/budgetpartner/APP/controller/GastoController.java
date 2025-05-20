package com.budgetpartner.APP.controller;

import com.budgetpartner.APP.dto.request.GastoDtoRequest;
import com.budgetpartner.APP.dto.response.GastoDtoResponse;
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

    @PostMapping
    public ResponseEntity<GastoDtoResponse> postGasto(@Validated @NotNull @RequestBody GastoDtoRequest gastoDtoReq) {
        Gasto gasto = gastoService.postGasto(gastoDtoReq);
        GastoDtoResponse gastoDtoResp = GastoMapper.toDtoResponse(gasto);
        return ResponseEntity.ok(gastoDtoResp);
    }

    @GetMapping({"/{id}"})
    public ResponseEntity<GastoDtoResponse> getGastoById(@Validated @NotNull @PathVariable Long id) {
        Gasto gasto = gastoService.getGastoById(id);
        GastoDtoResponse gastoDtoResp = GastoMapper.toDtoResponse(gasto);
        return ResponseEntity.ok(gastoDtoResp);
    }

    /*
    @PutMapping({"/{id}"})
    public GastoDtoResponse putGastoById(@Validated @NotNull @RequestBody GastoDtoRequest gastoDtoReq,
                                         @PathVariable Long id) {
        GastoDtoResponse gastoDtoResp = gastoService.putGastoById(gastoDtoReq, id);
        return gastoDtoResp;
    }

    @PatchMapping({"/{id}"})
    public GastoDtoResponse patchGastoById(@Validated @NotNull @RequestBody GastoDtoRequest gastoDtoReq,
                                           @PathVariable Long id) {
        GastoDtoResponse gastoDtoResp = gastoService.patchGastoById(gastoDtoReq, id);
        return gastoDtoResp;
    }*/
    @Operation(
            summary = "Eliminar un gasto por ID",
            description = "Elimina un gasto existente dado su ID. Devuelve el objeto eliminado como confirmación.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Gasto eliminado correctamente",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(
                                            name = "GastoEliminadoEjemplo",
                                            summary = "Ejemplo de gasto eliminado",
                                            value = "{ \"id\": 42, \"descripcion\": \"Cena en restaurante\", \"monto\": 75.00, \"categoria\": \"Alimentación\" }"
                                    )
                            )//Content
                    )}
    )
    @DeleteMapping({"/{id}"})
    public ResponseEntity<GastoDtoResponse> deleteGastoById(@Validated @NotNull @PathVariable Long id) {
        Gasto gasto = gastoService.deleteGastoById(id);
        GastoDtoResponse gastoDtoResp = GastoMapper.toDtoResponse(gasto);
        return ResponseEntity.ok(gastoDtoResp);
    }


}
