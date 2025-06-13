package com.budgetpartner.APP.controller;


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
    private MiembroService miembroService;
    @Autowired
    private OrganizacionService organizacionService;


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
    @GetMapping("/{id}/organizaciones")
    public ResponseEntity<List<OrganizacionDtoResponse>> getOrganizacionesByUsuarioId(@Validated @NotNull @PathVariable Long id) {
        List<Organizacion> organizacion = organizacionService.getOrganizacionesByUsuarioId(id);
        List<OrganizacionDtoResponse> organizacionDtoResponses = OrganizacionMapper.toDtoResponseListOrganizacion(organizacion);
        return ResponseEntity.ok(organizacionDtoResponses);
    }

    //-----------------------------
    //LLAMADAS RELACIONADAS CON JWT
    //-----------------------------

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
