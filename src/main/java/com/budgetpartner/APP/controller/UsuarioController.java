package com.budgetpartner.APP.controller;


import com.budgetpartner.APP.dto.request.UsuarioDtoRequest;
import com.budgetpartner.APP.dto.response.MiembroDtoResponse;
import com.budgetpartner.APP.dto.response.TareaDtoResponse;
import com.budgetpartner.APP.dto.response.UsuarioDtoResponse;
import com.budgetpartner.APP.entity.Miembro;
import com.budgetpartner.APP.entity.Usuario;
import com.budgetpartner.APP.mapper.MiembroMapper;
import com.budgetpartner.APP.mapper.TareaMapper;
import com.budgetpartner.APP.mapper.UsuarioMapper;
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
    public ResponseEntity<UsuarioDtoResponse> postUsuario(@Validated @NotNull @RequestBody UsuarioDtoRequest usuarioDtoReq){

        Usuario usuario = usuarioService.postUsuario(usuarioDtoReq);
        UsuarioDtoResponse usuarioDtoResp = UsuarioMapper.toDtoResponse(usuario);
        return ResponseEntity.ok(usuarioDtoResp);
    }

    @GetMapping({"/{id}"})
    public ResponseEntity<UsuarioDtoResponse> getUsuarioById(@Validated @NotNull @PathVariable Long id){
        Usuario usuario = usuarioService.getUsuarioById(id);
        UsuarioDtoResponse usuarioDtoResp = UsuarioMapper.toDtoResponse(usuario);
        return ResponseEntity.ok(usuarioDtoResp);
        /*

         */

    }

    /*
    @PutMapping({"/{id}"})
    public UsuarioDtoResponse putUsuarioById(@Validated @NotNull @PathVariable UsuarioDtoRequest usuarioDtoReq){

        return null;
    }

    @PatchMapping({"/{id}"})
    public UsuarioDtoResponse patchUsuarioById(@Validated @NotNull @PathVariable UsuarioDtoRequest usuarioDtoReq){

        return null;
    }*/

    @DeleteMapping({"/{id}"})
    public ResponseEntity<UsuarioDtoResponse> deleteUsuarioById(@Validated @NotNull @PathVariable Long id){
        Usuario usuario = usuarioService.deleteUsuarioById(id);
        UsuarioDtoResponse usuarioDtoResp = UsuarioMapper.toDtoResponse(usuario);
        return ResponseEntity.ok(usuarioDtoResp);
    }

    @GetMapping("/{id}/miembros")
    public ResponseEntity<List<MiembroDtoResponse>> getMiembrosByUsuarioId(@Validated @NotNull @PathVariable Long id) {
        List<Miembro> miembros = miembroService.findMiembrosByUsuarioId(id);
        List<MiembroDtoResponse> miembroDtoRespList = MiembroMapper.toDtoResponseListMiembro(miembros);
        return ResponseEntity.ok(miembroDtoRespList);
    }

    //TODO autenticación???

}
