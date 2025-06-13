package com.budgetpartner.APP.controller;

import com.budgetpartner.APP.dto.tarea.TareaDtoResponse;
import com.budgetpartner.APP.dto.tarea.TareaDtoPostRequest;
import com.budgetpartner.APP.dto.tarea.TareaDtoUpdateRequest;
import com.budgetpartner.APP.entity.Tarea;
import com.budgetpartner.APP.mapper.TareaMapper;
import com.budgetpartner.APP.service.TareaService;
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
@RequestMapping("/tareas")
public class TareaController {

    @Autowired
    TareaService tareaService;

    @Operation(
            summary = "Crear una tarea",
            description = "Crea una tarea nueva dado su PENDIENTE. Devuelve el objeto creado como confirmación.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Tarea creada correctamente",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(
                                            name = "MensajeConfirmacion",
                                            summary = "Mensaje de éxito",
                                            value = "PENDIENTE"
                                    )
                            )
                    )
            }
    )
    @PostMapping
    public ResponseEntity<TareaDtoResponse> postTarea(@Validated @NotNull @RequestBody TareaDtoPostRequest tareaDtoReq) {
        Tarea tarea = tareaService.postTarea(tareaDtoReq);
        TareaDtoResponse tareaDtoResp = TareaMapper.toDtoResponse(tarea);
        return ResponseEntity.ok(tareaDtoResp);
    }

    @Operation(
            summary = "Obtener una tarea por ID",
            description = "Devuelve una tarea existente dado un ID.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Tarea obtenida correctamente",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(
                                            name = "MensajeConfirmacion",
                                            summary = "Mensaje de éxito",
                                            value = "PENDIENTE"
                                    )
                            )
                    )
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<TareaDtoResponse> getTareaById(@Validated @NotNull @PathVariable Long id) {
        TareaDtoResponse tareaDtoResp  = tareaService.getTareaDtoById(id);
        return ResponseEntity.ok(tareaDtoResp);
    }

    @Operation(
            summary = "Actualizar parcialmente una tarea",
            description = "Actualiza los campos indicados de una tarea existente. Devuelve un mensaje de confirmación.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Tarea actualizada correctamente",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(
                                            name = "MensajeConfirmacion",
                                            summary = "Mensaje de éxito",
                                            value = "PENDIENTE"
                                    )
                            )
                    )
            }
    )
    @PatchMapping("/{id}")
    public ResponseEntity<String> patchTareaById(@Validated @NotNull @RequestBody TareaDtoUpdateRequest tareaDtoUpReq,
                                                 @PathVariable Long id) {
        tareaService.patchTarea(tareaDtoUpReq);
        return ResponseEntity.ok("Tarea actualizada correctamente");
    }

    @Operation(
            summary = "Eliminar una tarea por ID",
            description = "Elimina una tarea existente dado su ID. Devuelve un mensaje de confirmación.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Tarea eliminada correctamente",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(
                                            name = "MensajeConfirmacion",
                                            summary = "Mensaje de éxito",
                                            value = "\"Tarea eliminada correctamente\""
                                    )
                            )
                    )
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTareaById(@Validated @NotNull @PathVariable Long id) {
        Tarea tarea = tareaService.deleteTareaById(id);
        TareaDtoResponse tareaDtoResp = TareaMapper.toDtoResponse(tarea);
        return ResponseEntity.ok("Tarea eliminada correctamente");
    }
}