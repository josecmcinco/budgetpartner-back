package com.budgetpartner.APP.controller;

import com.budgetpartner.APP.dto.organizacion.OrganizacionDtoResponse;
import com.budgetpartner.APP.dto.organizacion.OrganizacionDtoPostRequest;
import com.budgetpartner.APP.dto.organizacion.OrganizacionDtoUpdateRequest;
import com.budgetpartner.APP.entity.Organizacion;
import com.budgetpartner.APP.mapper.OrganizacionMapper;
import com.budgetpartner.APP.service.OrganizacionService;
import com.budgetpartner.APP.service.PlanService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.constraints.NotNull;

@RestController
@RequestMapping("/organizaciones")
public class OrganizacionController {

    @Autowired
    private OrganizacionService organizacionService;
    @Autowired
    private PlanService planService;

    @Operation(
            summary = "Crear una organización",
            description = "Crea una organización nueva dado su PENDIENTE. Devuelve el objeto creado como confirmación.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Organización creada correctamente",
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
    public ResponseEntity<OrganizacionDtoResponse> postOrganizacion(@Validated @NotNull @RequestBody OrganizacionDtoPostRequest organizacionDtoReq) {
        Organizacion organizacion = organizacionService.postOrganizacion(organizacionDtoReq);
        OrganizacionDtoResponse organizacionDtoResp = OrganizacionMapper.toDtoResponse(organizacion);
        return ResponseEntity.ok(organizacionDtoResp);
    }

    @Operation(
            summary = "Obtener una organización por ID",
            description = "Devuelve una organización existente dado un id.",
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
        OrganizacionDtoResponse organizacionDtoResp = organizacionService.getOrganizacionByIdAndTransform(id);
        return ResponseEntity.ok(organizacionDtoResp);
    }

    @Operation(
            summary = "Actualizar parcialmente una organización",
            description = "Actualiza los campos indicados de una organización existente. Devuelve un mensaje de confirmación.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Organización actualizada correctamente",
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
    public ResponseEntity<String> patchOrganizacionById(@Validated @NotNull @RequestBody OrganizacionDtoUpdateRequest organizacionDtoUpReq) {
        organizacionService.patchOrganizacion(organizacionDtoUpReq);
        return ResponseEntity.ok("Organización actualizada correctamente");
    }

    @Operation(
            summary = "Eliminar una organización por ID",
            description = "Elimina una organización existente dado su ID. Devuelve un mensaje de confirmación.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Organización eliminada correctamente",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(
                                            name = "MensajeConfirmacion",
                                            summary = "Mensaje de éxito",
                                            value = "\"Organización eliminada correctamente\""
                                    )
                            )
                    )
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteOrganizacionById(@Validated @NotNull @PathVariable Long id) {
        Organizacion organizacion = organizacionService.deleteOrganizacionById(id);
        OrganizacionDtoResponse organizacionDtoResp = OrganizacionMapper.toDtoResponse(organizacion);
        return ResponseEntity.ok("Organización eliminada correctamente");
    }

    @GetMapping("/{id}/planes")
    public OrganizacionDtoResponse getPlanesByOrganizacionId(@Validated @NotNull @PathVariable Long id){
        //List<PlanDtoResponse> planes = planService.findPlanesByOrganizacionId(id);
        //return ResponseEntity.ok(planes);
        return null;
    }

}
