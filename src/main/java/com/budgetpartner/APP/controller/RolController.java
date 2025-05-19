package com.budgetpartner.APP.controller;

import com.budgetpartner.APP.dto.request.RolDtoRequest;
import com.budgetpartner.APP.dto.response.RolDtoResponse;
import com.budgetpartner.APP.service.RolService;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/roles")
public class RolController {

    @Autowired
    RolService rolService;

    @PostMapping
    public RolDtoResponse postRol(@Validated @NotNull @RequestBody RolDtoRequest rolDtoReq) {
        RolDtoResponse rolDtoResp = rolService.postRol(rolDtoReq);
        return rolDtoResp;
    }

    @GetMapping({"/{id}"})
    public RolDtoResponse getRolById(@Validated @NotNull @PathVariable Long id) {
        RolDtoResponse rolDtoResp = rolService.getRolById(id);
        return rolDtoResp;
    }

    /*
    @PutMapping({"/{id}"})
    public RolDtoResponse putRolById(@Validated @NotNull @RequestBody RolDtoRequest rolDtoReq,
                                     @PathVariable Long id) {
        RolDtoResponse rolDtoResp = rolService.putRolById(rolDtoReq, id);
        return rolDtoResp;
    }

    @PatchMapping({"/{id}"})
    public RolDtoResponse patchRolById(@Validated @NotNull @RequestBody RolDtoRequest rolDtoReq,
                                       @PathVariable Long id) {
        RolDtoResponse rolDtoResp = rolService.patchRolById(rolDtoReq, id);
        return rolDtoResp;
    }*/

    @DeleteMapping({"/{id}"})
    public RolDtoResponse deleteRolById(@Validated @NotNull @PathVariable Long id) {
        RolDtoResponse rolDtoResp = rolService.deleteRolById(id);
        return rolDtoResp;
    }
}