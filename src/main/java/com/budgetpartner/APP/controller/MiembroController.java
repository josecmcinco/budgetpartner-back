package com.budgetpartner.APP.controller;


import com.budgetpartner.APP.dto.request.MiembroDtoRequest;
import com.budgetpartner.APP.dto.response.GastoDtoResponse;
import com.budgetpartner.APP.dto.response.MiembroDtoResponse;
import com.budgetpartner.APP.dto.response.OrganizacionDtoResponse;
import com.budgetpartner.APP.dto.response.UsuarioDtoResponse;
import com.budgetpartner.APP.entity.Miembro;
import com.budgetpartner.APP.entity.Organizacion;
import com.budgetpartner.APP.mapper.GastoMapper;
import com.budgetpartner.APP.mapper.MiembroMapper;
import com.budgetpartner.APP.mapper.OrganizacionMapper;
import com.budgetpartner.APP.service.MiembroService;
import com.budgetpartner.APP.service.OrganizacionService;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/miembros")
public class MiembroController {

   @Autowired
    private MiembroService miembroService;
    @Autowired
    private OrganizacionService organizacionService;

    @PostMapping
    public ResponseEntity<MiembroDtoResponse> postMiembro(@Validated @NotNull @RequestBody MiembroDtoRequest miembroDtoR){
        Miembro miembro = miembroService.postMiembro(miembroDtoR);
        MiembroDtoResponse miembroDtoResp = MiembroMapper.toDtoResponse(miembro);
        return ResponseEntity.ok(miembroDtoResp);
    }

    @GetMapping
    public ResponseEntity<MiembroDtoResponse> getMiembroByUsuario (@Validated @NotNull Long userId){
        Miembro miembro = miembroService.getMiembroById(userId);
        MiembroDtoResponse miembroDtoResp = MiembroMapper.toDtoResponse(miembro);
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
    public ResponseEntity<MiembroDtoResponse> deleteMiembro(@Validated @NotNull Long userId){
        Miembro miembro = miembroService.deleteMiembroById(userId);
        MiembroDtoResponse miembroDtoResp = MiembroMapper.toDtoResponse(miembro);
        return ResponseEntity.ok(miembroDtoResp);
    }

    @GetMapping("/{id}/organizaciones")
    public ResponseEntity<List<OrganizacionDtoResponse>> getOrganizacionesByMiembroId(@Validated @NotNull @PathVariable Long id) {
        List<Organizacion> organizaciones = organizacionService.findOrganizacionesByMiembroId(id);
        List<OrganizacionDtoResponse> organizacionesDtoResp  = OrganizacionMapper.toDtoResponseListOrganizacion(organizaciones);
        return ResponseEntity.ok(organizacionesDtoResp);
    }

}
