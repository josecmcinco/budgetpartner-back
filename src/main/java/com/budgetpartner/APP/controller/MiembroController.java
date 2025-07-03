package com.budgetpartner.APP.controller;


import com.budgetpartner.APP.dto.gasto.GastoDtoUpdateRequest;
import com.budgetpartner.APP.dto.miembro.MiembroDtoResponse;
import com.budgetpartner.APP.dto.miembro.MiembroDtoPostRequest;
import com.budgetpartner.APP.dto.miembro.MiembroDtoUpdateRequest;
import com.budgetpartner.APP.entity.Miembro;
import com.budgetpartner.APP.mapper.MiembroMapper;
import com.budgetpartner.APP.service.MiembroService;
import com.budgetpartner.APP.service.OrganizacionService;
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
@RequestMapping("/miembros")
public class MiembroController {

    @Autowired
    private MiembroService miembroService;
    @Autowired
    private OrganizacionService organizacionService;

    @Operation(
            summary = "Crear un miembro",
            description = "Crea un miembro nuevo dado su organizacionId/Rol/nick/isActivo. Devuelve un mensaje de confirmación.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Miembro creado correctamente"
                    )
            }
    )
    @PostMapping
    public ResponseEntity<MiembroDtoResponse> postMiembro(@Validated @NotNull @RequestBody MiembroDtoPostRequest miembroDtoReq) {
        MiembroDtoResponse miembroDtoResp = miembroService.postMiembro(miembroDtoReq);
        return ResponseEntity.ok(miembroDtoResp);
    }

    @Operation(
            summary = "Obtener un miembro por ID",
            description = "Devuelve un miembro existente dado un id.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Miembro obtenido correctamente",
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
    public ResponseEntity<MiembroDtoResponse> getMiembroById(@Validated @NotNull @PathVariable Long id) {
        MiembroDtoResponse miembroDtoResp = miembroService.getMiembroDtoById(id);
        return ResponseEntity.ok(miembroDtoResp);
    }

    @Operation(
            summary = "Actualizar parcialmente un miembro",
            description = "Actualiza los campos rol/miembro/nick/isActivo de un miembro existente. Devuelve un mensaje de confirmación.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Miembro actualizado correctamente"
                    )
            }
    )
    @PatchMapping("/{id}")
    public ResponseEntity<String> patchMiembroById(@Validated @NotNull @RequestBody MiembroDtoUpdateRequest miembroDtoUpReq,
                                                   @Validated @NotNull @PathVariable Long id) {
        miembroService.patchMiembro(miembroDtoUpReq, id);
        return ResponseEntity.ok("Miembro actualizado correctamente");
    }

    @Operation(
            summary = "Eliminar un miembro por ID",
            description = "Elimina un miembro existente dado su ID. Devuelve un mensaje de confirmación.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Miembro eliminado correctamente"
                    )
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteMiembroById(@Validated @NotNull @PathVariable Long id) {
        Miembro miembro = miembroService.deleteMiembroById(id);
        MiembroDtoResponse miembroDtoResp = MiembroMapper.toDtoResponse(miembro);
        return ResponseEntity.ok("Miembro eliminado correctamente");
    }
}

