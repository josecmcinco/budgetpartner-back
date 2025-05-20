package com.budgetpartner.APP.controller;


import com.budgetpartner.APP.dto.request.MiembroDtoRequest;
import com.budgetpartner.APP.dto.response.MiembroDtoResponse;
import com.budgetpartner.APP.dto.response.OrganizacionDtoResponse;
import com.budgetpartner.APP.dto.response.UsuarioDtoResponse;
import com.budgetpartner.APP.service.MiembroService;
import com.budgetpartner.APP.service.OrganizacionService;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//TODO TEMPORAL
//import org.antlr.v4.runtime.misc.NotNull;
//import java.util.List;

@RestController
@RequestMapping("/miembros")
public class MiembroController {

   @Autowired
    private MiembroService miembroService;
    @Autowired
    private OrganizacionService organizacionService;

    @PostMapping
    public ResponseEntity<MiembroDtoResponse> postMiembro(@Validated @NotNull @RequestBody MiembroDtoRequest miembroDtoR){
        MiembroDtoResponse miembroDtoResp = miembroService.postMiembro(miembroDtoR);
        return ResponseEntity.ok(miembroDtoResp);
    }

    @GetMapping
    public ResponseEntity<MiembroDtoResponse> getMiembroByUsuario (@Validated @NotNull Long userId){
        MiembroDtoResponse miembroDtoResp = miembroService.getMiembroById(userId);
        return ResponseEntity.ok(miembroDtoResp);
    }
/*
    @PutMapping("/{id}")
    public MiembroDtoResponse putMiembro(@Validated @NotNull @RequestBody MiembroDtoRequest miembroDtoR){
        return null;
    }

    @PatchMapping("/{id}")
    public MiembroDtoResponse patchMiembro(@Validated @NotNull  @RequestBody MiembroDtoRequest miembroDtoR){
        return null;
    }*/

    @DeleteMapping("/{id}")
    public ResponseEntity<MiembroDtoResponse> deleteMiembro(@Validated @NotNull @RequestBody Long userId){
        MiembroDtoResponse miembroDtoResp = miembroService.deleteMiembroById(userId);
        return ResponseEntity.ok(miembroDtoResp);
    }

    @GetMapping("/{id}/organizaciones")
    public ResponseEntity<List<OrganizacionDtoResponse>> getOrganizacionesByMiembroId(@Validated @NotNull @PathVariable Long id) {
        List<OrganizacionDtoResponse> organizaciones = organizacionService.findOrganizacionesByMiembroId(id);
        return ResponseEntity.ok(organizaciones);
    }

}
