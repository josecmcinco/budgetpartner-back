package com.budgetpartner.APP.controller;


import com.budgetpartner.APP.dto.dashborard.DashboardDtoResponse;
import com.budgetpartner.APP.dto.organizacion.OrganizacionDtoResponse;
import com.budgetpartner.APP.dto.token.TokenDtoRequest;
import com.budgetpartner.APP.dto.usuario.UsuarioDtoPostRequest;
import com.budgetpartner.APP.dto.usuario.UsuarioDtoResponse;
import com.budgetpartner.APP.dto.usuario.UsuarioDtoUpdateRequest;
import com.budgetpartner.APP.dto.token.TokenDtoResponse;
import com.budgetpartner.APP.entity.Organizacion;
import com.budgetpartner.APP.entity.Usuario;
import com.budgetpartner.APP.mapper.OrganizacionMapper;
import com.budgetpartner.APP.mapper.UsuarioMapper;
import com.budgetpartner.APP.service.DashboardService;
import com.budgetpartner.APP.service.MiembroService;
import com.budgetpartner.APP.service.OrganizacionService;
import com.budgetpartner.APP.service.UsuarioService;
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

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private OrganizacionService organizacionService;

/*
TODO ELIMINAR
    @Operation(
            summary = "Obtener un usuario por ID",
            description = "Devuelve un usuario existente dado un id.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Usuario obtenido correctamente",
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
    public ResponseEntity<UsuarioDtoResponse> getUsuarioById(@Validated @NotNull @PathVariable Long id) {
        UsuarioDtoResponse usuarioDtoResp = usuarioService.getUsuarioDtoById("id");
        return ResponseEntity.ok(usuarioDtoResp);
    }*/

    @Operation(
            summary = "Actualizar parcialmente un usuario",
            description = "Actualiza los campos email/nombre/apellido/contraseña de un usuario existente. Devuelve un mensaje de confirmación.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Usuario actualizado correctamente",
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
    @PatchMapping
    public ResponseEntity<String> patchUsuarioById(@Validated @NotNull @RequestBody UsuarioDtoUpdateRequest usuarioDtoUpReq) {
        usuarioService.patchUsuario(usuarioDtoUpReq);
        return ResponseEntity.ok("Usuario actualizado correctamente");
    }

    @Operation(
            summary = "Eliminar un usuario por ID",
            description = "Elimina un usuario existente dado su ID. Devuelve un mensaje de confirmación.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Usuario eliminado correctamente",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(
                                            name = "MensajeConfirmacion",
                                            summary = "Mensaje de éxito",
                                            value = "\"Usuario eliminado correctamente\""
                                    )
                            )
                    )
            }
    )
    @DeleteMapping
    public ResponseEntity<String> deleteUsuarioById() {
        Usuario usuario = usuarioService.deleteUsuarioById();
        UsuarioDtoResponse usuarioDtoResp = UsuarioMapper.toDtoResponse(usuario);
        return ResponseEntity.ok("Usuario eliminado correctamente");
    }



    //-----------------------------
    //LLAMADAS RELACIONADAS CON JWT
    //-----------------------------

    //NO NECESITA JWT
    @Operation(
            summary = "Registrar usuario",
            description = "Da de alta un usuario y devuelve los tokens de acceso y refresco",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Usuario creado correctamente",
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
    @PostMapping("/registro")
    public ResponseEntity<TokenDtoResponse> register(@RequestBody UsuarioDtoPostRequest request) {
        final TokenDtoResponse token = usuarioService.register(request);
        return ResponseEntity.ok(token);
    }

    //NO NECESITA JWT
    @Operation(
            summary = "Inicio de sesión",
            description = "Devuelve devuelve los tokens de acceso y refresco si coinciden el usuario y contraseña enviados.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Inicio de sesión correcto correctamente",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(
                                            name = "MensajeConfirmacion",
                                            summary = "Mensaje de éxito",
                                            value = "PENDIENTE"
                                    )
                            )
                    )
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "Credenciales",
                                    summary = "Usuario y contraseña válidos",
                                    value = "{\n  \"email\": \"carlos.martinez@mail.com\",\n  \"contraseña\": \"contraseña789\"\n}"
                            )
                    )
            )

    )

    @PostMapping("/login")
    public ResponseEntity<TokenDtoResponse> authenticate(@RequestBody TokenDtoRequest request) {
        final TokenDtoResponse token = usuarioService.login(request);
        return ResponseEntity.ok(token);
    }

    @Operation(
            summary = "Obtener token de acceso",
            description = "Obtener el token de acceso de un usuario usando su token de refresco.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Token obtenido correctamente",
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
    @GetMapping("/refrescar")
    public TokenDtoResponse refreshToken(@RequestHeader(HttpHeaders.AUTHORIZATION) final String authHeader) {
        return usuarioService.refreshToken(authHeader);
    }



}
