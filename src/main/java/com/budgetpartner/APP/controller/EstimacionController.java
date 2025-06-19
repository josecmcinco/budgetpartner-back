package com.budgetpartner.APP.controller;

import com.budgetpartner.APP.dto.estimacion.EstimacionDtoPostRequest;
import com.budgetpartner.APP.dto.estimacion.EstimacionDtoResponse;
import com.budgetpartner.APP.dto.estimacion.EstimacionDtoUpdateRequest;
import com.budgetpartner.APP.entity.Estimacion;
import com.budgetpartner.APP.service.EstimacionService;
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
@RequestMapping("/estimaciones")
public class EstimacionController {

    @Autowired
    EstimacionService estimacionService;

    @Operation(
            summary = "Crear una estimación",
            description = "Crea una estimación nueva. Devuelve un mensaje de confirmación.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Estimación creada correctamente"
                    )}
    )
    @PostMapping
    public ResponseEntity<String> postEstimacion(@NotNull @RequestBody EstimacionDtoPostRequest estimacionDtoReq) {
        estimacionService.postEstimacion(estimacionDtoReq);
        return ResponseEntity.ok("Estimación creada correctamente");
    }

    @Operation(
            summary = "Devolver una estimación",
            description = "Devuelve una estimación existente dado un id.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Estimación devuelta correctamente",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(
                                            name = "EstimacionEjemplo",
                                            summary = "Ejemplo de respuesta",
                                            value = "PENDIENTE"
                                    )
                            )
                    )}
    )
    @GetMapping("/{id}")
    public ResponseEntity<EstimacionDtoResponse> getEstimacionById(@Validated @NotNull @PathVariable Long id) {
        EstimacionDtoResponse estimacionDtoResp = estimacionService.getEstimacionDtoById(id);
        return ResponseEntity.ok(estimacionDtoResp);
    }

    @Operation(
            summary = "Actualizar una estimación",
            description = "Actualiza los datos de una estimación existente. Devuelve un mensaje de confirmación.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Estimación actualizada correctamente"
                    )}
    )
    @PatchMapping("/{id}")
    public ResponseEntity<String> patchEstimacionById(@Validated @NotNull @RequestBody EstimacionDtoUpdateRequest estimacionDtoUpReq,
                                                      @Validated @NotNull @PathVariable Long id) {
        estimacionService.patchEstimacion(estimacionDtoUpReq, id);
        return ResponseEntity.ok("Estimación actualizada correctamente");
    }

    @Operation(
            summary = "Eliminar una estimación",
            description = "Elimina una estimación existente dado su ID. Devuelve un mensaje de confirmación.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Estimación eliminada correctamente"
                    )}
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEstimacionById(@Validated @NotNull @PathVariable Long id) {
       estimacionService.deleteEstimacionById(id);
        // Puedes mapearlo a DTO si lo necesitas
        return ResponseEntity.ok("Estimación eliminada correctamente");
    }
}
