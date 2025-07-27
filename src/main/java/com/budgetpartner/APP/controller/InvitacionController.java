package com.budgetpartner.APP.controller;

import com.budgetpartner.APP.dto.TokenResponse;
import com.budgetpartner.APP.dto.miembro.MiembroDtoResponse;
import com.budgetpartner.APP.service.InvitacionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/invitacion")
public class InvitacionController

{
    @Autowired
    private InvitacionService invitacionService;

    @Operation(
            summary = "Obtener miembros no adjuntos mediante token de invitación",
            description = "Devuelve una lista de usuarios que aún no están adjuntos a la organización asociada al token",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Miembros no adjuntos obtenidos correctamente",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(
                                            name = "MiembrosNoAdjuntos",
                                            summary = "Lista de miembros",
                                            value = "[{\"id\": 1, \"nombre\": \"Juan Pérez\"}, {\"id\": 2, \"nombre\": \"María López\"}]"
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Organizacion no encontrada con id: 2"
                    )
            }

            ,
            parameters = {
                    @Parameter(
                            name = "token",
                            description = "Token de invitación",
                            required = true,
                            example = "a4d6bbda-93a7-47bc-9f38-5b6ec62a9478",
                            in = ParameterIn.PATH
                    )
            }
    )
    @GetMapping("/{token}/asociarMiembros")
    public ResponseEntity<List<MiembroDtoResponse>> getMiembrosNoAdjuntos(
            @PathVariable String token) {

        List<MiembroDtoResponse> miembros = invitacionService.getMiembrosNoAdjuntosPorToken(token);

        return ResponseEntity.ok(miembros);
    }


    @Operation(
            summary = "Obtener un token de invitación",
            description = "Genera un token para que se puedan asociar usuarios a los miembros o simplemente lo devuelve",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Token generado correctamente",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(
                                            name = "Token de inviatción",
                                            summary = "TokenResponse",
                                            value = "[{\"token\": \"a4d6bbda-93a7-47bc-9f38-5b6ec62a9478\"}]"
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Token inválido o no encontrado"
                    )
            }
    )
    @GetMapping("/{organizacionId}/obtenerToken")
    public ResponseEntity<TokenResponse> obtenerToken(
            @PathVariable Long organizacionId) {

        TokenResponse token = invitacionService.obtenerToken(organizacionId);

        return ResponseEntity.ok(token);
    }
}
