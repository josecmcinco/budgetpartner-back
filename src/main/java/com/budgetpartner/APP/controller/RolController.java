package com.budgetpartner.APP.controller;

import com.budgetpartner.APP.dto.rol.RolDtoResponse;
import com.budgetpartner.APP.dto.rol.RolDtoPostRequest;
import com.budgetpartner.APP.dto.rol.RolDtoUpdateRequest;
import com.budgetpartner.APP.entity.Rol;
import com.budgetpartner.APP.mapper.RolMapper;
import com.budgetpartner.APP.service.RolService;
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
@RequestMapping("/roles")
public class RolController {

    @Autowired
    RolService rolService;

    @Operation(
            summary = "Crear un rol",
            description = "Crea un rol nuevo dado su PENDIENTE. Devuelve el objeto creado como confirmación.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Rol creado correctamente",
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
    public ResponseEntity<RolDtoResponse> postRol(@Validated @NotNull @RequestBody RolDtoPostRequest rolDtoReq) {
        Rol rol = rolService.postRol(rolDtoReq);
        RolDtoResponse rolDtoResp = RolMapper.toDtoResponse(rol);
        return ResponseEntity.ok(rolDtoResp);
    }

    @Operation(
            summary = "Obtener un rol por ID",
            description = "Devuelve un rol existente dado un id.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Rol obtenido correctamente",
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
    public ResponseEntity<RolDtoResponse> getRolById(@Validated @NotNull @PathVariable Long id) {
        Rol rol = rolService.getRolById(id);
        RolDtoResponse rolDtoResp = RolMapper.toDtoResponse(rol);
        return ResponseEntity.ok(rolDtoResp);
    }

    @Operation(
            summary = "Actualizar parcialmente un rol",
            description = "Actualiza los campos indicados de un rol existente. Devuelve un mensaje de confirmación.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Rol actualizado correctamente",
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
    public ResponseEntity<String> patchRolById(@Validated @NotNull @RequestBody RolDtoUpdateRequest rolDtoUpReq,
                                               @PathVariable Long id) {
        //rolService.patchRol(rolDtoUpReq, id);
        return ResponseEntity.ok("Rol actualizado correctamente");
    }

    @Operation(
            summary = "Eliminar un rol por ID",
            description = "Elimina un rol existente dado su ID. Devuelve un mensaje de confirmación.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Rol eliminado correctamente",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(
                                            name = "MensajeConfirmacion",
                                            summary = "Mensaje de éxito",
                                            value = "\"Rol eliminado correctamente\""
                                    )
                            )
                    )
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRolById(@Validated @NotNull @PathVariable Long id) {
        Rol rol = rolService.deleteRolById(id);
        RolDtoResponse rolDtoResp = RolMapper.toDtoResponse(rol);
        return ResponseEntity.ok("Rol eliminado correctamente");
    }
}