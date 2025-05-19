package com.budgetpartner.APP.controller;

import com.budgetpartner.APP.dto.request.OrganizacionDtoRequest;
import com.budgetpartner.APP.dto.response.OrganizacionDtoResponse;
import com.budgetpartner.APP.dto.response.PlanDtoResponse;
import com.budgetpartner.APP.service.OrganizacionService;
import com.budgetpartner.APP.service.PlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.constraints.NotNull;

@RestController
@RequestMapping("/organizaciones")
public class OrganizacionController {

    @Autowired
    private OrganizacionService organizacionService;
    @Autowired
    private PlanService planService;

    @PostMapping
    public OrganizacionDtoResponse postOrganizacion(@Validated @NotNull OrganizacionDtoRequest organizacionDtoReq){
        OrganizacionDtoResponse organizacionDtoResp = organizacionService.postOrganizacion(organizacionDtoReq);
        return organizacionDtoResp;
    }

    @GetMapping({"/{id}"})
    public OrganizacionDtoResponse getOrganizacion(@Validated @NotNull Long id){
        OrganizacionDtoResponse organizacionDtoResp = organizacionService.getOrganizacionById(id);
        return organizacionDtoResp;
    }

    /*
    @PutMapping({"/{id}"})
    public OrganizacionDtoResponse putOrganizacion(@Validated @NotNull OrganizacionDtoRequest organizacionDtoReq){
        OrganizacionDtoResponse organizacionDtoResp = organizacionService.putOrganizacionById(organizacionDtoReq, id);
        return organizacionDtoResp;
    }

    @PatchMapping({"/{id}"})
    public OrganizacionDtoResponse patchOrganizacion(@Validated @NotNull OrganizacionDtoRequest organizacionDtoReq){
        OrganizacionDtoResponse organizacionDtoResp = organizacionService.patchOrganizacionById(organizacionDtoReq, id);
        return organizacionDtoResp;
    }
    */

    @DeleteMapping({"/{id}"})
    public OrganizacionDtoResponse deleteOrganizacion(@Validated @NotNull Long id){
            OrganizacionDtoResponse organizacionDtoResp = organizacionService.deleteOrganizacionById(id);
            return organizacionDtoResp;
    }

    @GetMapping("/{id}/planes")
    public OrganizacionDtoResponse getPlanesByOrganizacionId(@Validated @NotNull Long id){
        //List<PlanDtoResponse> planes = planService.findPlanesByOrganizacionId(id);
        //return ResponseEntity.ok(planes);
        return null;
    }

}
