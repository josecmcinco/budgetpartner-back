package com.budgetpartner.APP.controller;


import com.budgetpartner.APP.dto.request.MiembroDtoRequest;
import com.budgetpartner.APP.dto.response.MiembroDtoResponse;
import com.budgetpartner.APP.dto.response.OrganizacionDtoResponse;
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
    public MiembroDtoResponse postMiembro(@Validated @NotNull @RequestBody MiembroDtoRequest miembroDtoR){
        return null;
    }

    @GetMapping
    public MiembroDtoResponse getMiembroByUsuario (@Validated @NotNull Long userId){
         return null;
    }

    @PutMapping("/{id}")
    public MiembroDtoResponse putMiembro(@Validated @NotNull @RequestBody MiembroDtoRequest miembroDtoR){
        return null;
    }

    @PatchMapping("/{id}")
    public MiembroDtoResponse patchMiembro(@Validated @NotNull  @RequestBody MiembroDtoRequest miembroDtoR){
        return null;
    }

    @DeleteMapping("/{id}")
    public MiembroDtoResponse deleteMiembro(@Validated @NotNull @RequestBody Long userId){
        return null;
    }


    @GetMapping("/{id}/organizaciones")
    public ResponseEntity<List<OrganizacionDtoResponse>> getMiembrosByUsuarioId(@Validated @NotNull @PathVariable Long id) {
        List<OrganizacionDtoResponse> organizaciones = organizacionService.findOrganizacionesByMiembroId(id);
        return ResponseEntity.ok(organizaciones);
    }

}
