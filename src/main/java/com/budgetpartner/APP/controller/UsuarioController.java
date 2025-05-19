package com.budgetpartner.APP.controller;


import com.budgetpartner.APP.dto.request.UsuarioDtoRequest;
import com.budgetpartner.APP.dto.response.MiembroDtoResponse;
import com.budgetpartner.APP.dto.response.UsuarioDtoResponse;
import com.budgetpartner.APP.service.MiembroService;
import com.budgetpartner.APP.service.UsuarioService;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
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

    @PostMapping
    public UsuarioDtoResponse postUsuario(@Validated @NotNull @RequestBody UsuarioDtoRequest usuarioDtoReq){

        UsuarioDtoResponse usuarioDtoResp = usuarioService.postUsuario(usuarioDtoReq);
        return usuarioDtoResp;
    }

    @GetMapping({"/{id}"})
    public UsuarioDtoResponse getUsuarioById(@Validated @NotNull @PathVariable Long id){
        UsuarioDtoResponse usuarioDtoResp = usuarioService.getUsuarioById(id);
        return usuarioDtoResp;

    }

    @PutMapping({"/{id}"})
    public UsuarioDtoResponse putUsuarioById(@Validated @NotNull @PathVariable UsuarioDtoRequest usuarioDtoReq){

        return null;
    }

    @PatchMapping({"/{id}"})
    public UsuarioDtoResponse patchUsuarioById(@Validated @NotNull @PathVariable UsuarioDtoRequest usuarioDtoReq){

        return null;
    }

    @DeleteMapping({"/{id}"})
    public UsuarioDtoResponse deleteUsuarioById(@Validated @NotNull @PathVariable Long id){
        UsuarioDtoResponse usuarioDtoResp = usuarioService.deleteUsuarioById(id);
        return usuarioDtoResp;
    }

    @GetMapping("/{id}/miembros")
    public ResponseEntity<List<MiembroDtoResponse>> getMiembrosByUsuarioId(@Validated @NotNull @PathVariable Long id) {
        List<MiembroDtoResponse> organizaciones = miembroService.findMiembrosByUsuarioId(id);
        return ResponseEntity.ok(organizaciones);
    }

    //TODO autenticación???

}
