package com.budgetpartner.APP.controller;

import com.budgetpartner.APP.dto.organizacion.OrganizacionDtoResponse;
import com.budgetpartner.APP.dto.organizacion.OrganizacionDtoPostRequest;
import com.budgetpartner.APP.dto.organizacion.OrganizacionDtoUpdateRequest;
import com.budgetpartner.APP.service.OrganizacionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.constraints.NotNull;

import java.util.List;

@RestController
@RequestMapping("/organizaciones")
public class OrganizacionController {

    @Autowired
    private OrganizacionService organizacionService;

    @Operation(
            summary = "Crear una organización",
            description = "Crea una organización nueva dado una OrganizacionDtoRequest. Devuelve un OK como confirmación.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Organización creada correctamente"
                    )
            }
    )
    @PostMapping
    public ResponseEntity<String> postOrganizacion(@Validated @NotNull @RequestBody OrganizacionDtoPostRequest organizacionDtoReq
    ) {
        organizacionService.postOrganizacion(organizacionDtoReq);
        return ResponseEntity.ok("Organización creada correctamente");
    }

    @Operation(
            summary = "Obtener una organización por ID",
            description = "Devuelve una organización junto con sus miembros, planes, presupuesto estimado y gastos reales dado un id.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Organización obtenida correctamente",
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
    public ResponseEntity<OrganizacionDtoResponse> getOrganizacionById(@Validated @NotNull @PathVariable Long id) {
        OrganizacionDtoResponse organizacionDtoResp = organizacionService.getOrganizacionDtoById(id);
        return ResponseEntity.ok(organizacionDtoResp);
    }

    @Operation(
            summary = "Actualizar parcialmente una organización",
            description = "Actualiza los campos 'Nombre'/'Descripción' de una organización existente. Devuelve un mensaje de confirmación.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Organización actualizada correctamente"
                    )
            }
    )
    @PatchMapping("/{id}")
    public ResponseEntity<String> patchOrganizacion(@Validated @NotNull @RequestBody OrganizacionDtoUpdateRequest organizacionDtoUpReq) {
        organizacionService.patchOrganizacion(organizacionDtoUpReq);
        return ResponseEntity.ok("Organización actualizada correctamente");
    }

    @Operation(
            summary = "Eliminar una organización por ID",
            description = "Elimina una organización existente dado su ID. Devuelve un mensaje de confirmación.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Organización eliminada correctamente"
                    )
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteOrganizacionById(@Validated @NotNull @PathVariable Long id) {
        organizacionService.deleteOrganizacionById(id);
        return ResponseEntity.ok("Organización eliminada correctamente");
    }

    @Operation(
            summary = "Obtener todas las organizaciones junto con sus miembros dado el id de un miembro",
            description = "Devuelve una organización junto con planes, presupuesto estimado y gastos reales dado un id.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Organizaciones obtenidas correctamente",
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
    @GetMapping("/organizacion")
    public ResponseEntity<List<OrganizacionDtoResponse>> getOrganizacionesByUsuarioId() {
        List<OrganizacionDtoResponse> organizacionDtoResponses = organizacionService.getOrganizacionesDtoByUsuarioId();
        return ResponseEntity.ok(organizacionDtoResponses);
    }

/*
    @Operation(
            summary = "Obtener todas las organizaciones junto con sus miembros dado el id de un miembro",
            description = "Devuelve una organización junto con planes, presupuesto estimado y gastos reales dado un id.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Organizacioes obtenidas correctamente",
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


    @GetMapping("/{id}/planes")
    public OrganizacionDtoResponse getPlanesByOrganizacionId(@Validated @NotNull @PathVariable Long id){
        //List<PlanDtoResponse> planes = planService.findPlanesByOrganizacionId(id);
        //return ResponseEntity.ok(planes);
        return null;
    }*/

}
