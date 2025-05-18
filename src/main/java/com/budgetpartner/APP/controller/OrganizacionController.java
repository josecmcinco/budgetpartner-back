package com.budgetpartner.APP.controller;

import com.budgetpartner.APP.dto.request.OrganizacionDtoRequest;
import com.budgetpartner.APP.dto.response.OrganizacionDtoResponse;
import com.budgetpartner.APP.service.OrganizacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.constraints.NotNull;

@RestController
@RequestMapping("/organizaciones")
public class OrganizacionController {

    @Autowired
    private OrganizacionService organizacionService;

    @PostMapping
    public OrganizacionDtoResponse postOrganizacion(@Validated @NotNull OrganizacionDtoRequest organizacionDtoReq){
        return null;
    }

    @GetMapping({"/{id}"})
    public OrganizacionDtoResponse getOrganizacion(@Validated @NotNull Long id){
        return null;
    }

    @PutMapping({"/{id}"})
    public OrganizacionDtoResponse putOrganizacion(@Validated @NotNull OrganizacionDtoRequest organizacionDtoReq){
        return null;
    }

    @PatchMapping({"/{id}"})
    public OrganizacionDtoResponse patchOrganizacion(@Validated @NotNull OrganizacionDtoRequest organizacionDtoReq){
        return null;
    }

    @DeleteMapping({"/{id}"})
    public OrganizacionDtoResponse deleteOrganizacion(@Validated @NotNull Long id){
        return null;
    }

    @GetMapping("/{id}/planes")
    public OrganizacionDtoResponse getPlanesByOrganizacionId(@Validated @NotNull Long id){
        return null;
    }

}
