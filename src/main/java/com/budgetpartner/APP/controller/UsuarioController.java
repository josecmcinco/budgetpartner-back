package com.budgetpartner.APP.controller;


import com.budgetpartner.APP.dto.miembro.MiembroDtoResponse;
import com.budgetpartner.APP.dto.token.TokenDtoRequest;
import com.budgetpartner.APP.dto.usuario.UsuarioDtoPostRequest;
import com.budgetpartner.APP.dto.usuario.UsuarioDtoResponse;
import com.budgetpartner.APP.dto.usuario.UsuarioDtoUpdateRequest;
import com.budgetpartner.APP.dto.token.TokenDtoResponse;
import com.budgetpartner.APP.entity.Miembro;
import com.budgetpartner.APP.entity.Usuario;
import com.budgetpartner.APP.mapper.MiembroMapper;
import com.budgetpartner.APP.mapper.UsuarioMapper;
import com.budgetpartner.APP.service.MiembroService;
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
    private MiembroService miembroService;

    @Operation(
            summary = "Crear un usuario",
            description = "Crea un usuario nuevo dado su PENDIENTE. Devuelve el objeto creado como confirmación.",
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
    @PostMapping
    public ResponseEntity<UsuarioDtoResponse> postUsuario(@Validated @NotNull @RequestBody UsuarioDtoPostRequest usuarioDtoReq) {
        Usuario usuario = usuarioService.postUsuario(usuarioDtoReq);
        UsuarioDtoResponse usuarioDtoResp = UsuarioMapper.toDtoResponse(usuario);
        return ResponseEntity.ok(usuarioDtoResp);
    }

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
        UsuarioDtoResponse usuarioDtoResp = usuarioService.getUsuarioByIdAndTransform("id");
        return ResponseEntity.ok(usuarioDtoResp);
    }

    @Operation(
            summary = "Actualizar parcialmente un usuario",
            description = "Actualiza los campos indicados de un usuario existente. Devuelve un mensaje de confirmación.",
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
    @PatchMapping("/{id}")
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
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUsuarioById(@Validated @NotNull @PathVariable Long id) {
        Usuario usuario = usuarioService.deleteUsuarioById(id);
        UsuarioDtoResponse usuarioDtoResp = UsuarioMapper.toDtoResponse(usuario);
        return ResponseEntity.ok("Usuario eliminado correctamente");
    }

    @GetMapping("/{id}/miembros")
    public ResponseEntity<List<MiembroDtoResponse>> getMiembrosByUsuarioId(@Validated @NotNull @PathVariable Long id) {
        List<Miembro> miembros = miembroService.findMiembrosByUsuarioId(id);
        List<MiembroDtoResponse> miembroDtoRespList = MiembroMapper.toDtoResponseListMiembro(miembros);
        return ResponseEntity.ok(miembroDtoRespList);
    }

    //LLAMADAS RELACIONADAS CON JWT

    //NO NECESITA JWT
    @PostMapping("/registro")
    public ResponseEntity<TokenDtoResponse> register(@RequestBody UsuarioDtoPostRequest request) {
        final TokenDtoResponse token = usuarioService.register(request);
        return ResponseEntity.ok(token);
    }

    //NO NECESITA JWT
    @PostMapping("/login")
    public ResponseEntity<TokenDtoResponse> authenticate(@RequestBody TokenDtoRequest request) {
        final TokenDtoResponse token = usuarioService.login(request);
        return ResponseEntity.ok(token);
    }

    @GetMapping("/refrescar")
    public TokenDtoResponse refreshToken(@RequestHeader(HttpHeaders.AUTHORIZATION) final String authHeader) {
        return usuarioService.refreshToken(authHeader);
    }



}
