package com.budgetpartner.APP.controller;

import com.budgetpartner.APP.dto.request.GastoDtoRequest;
import com.budgetpartner.APP.dto.response.GastoDtoResponse;
import com.budgetpartner.APP.service.GastoService;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/gastos")
public class GastoController {
    @Autowired
    GastoService gastoService;

    @PostMapping
    public GastoDtoResponse postGasto(@Validated @NotNull @RequestBody GastoDtoRequest gastoDtoReq) {
        GastoDtoResponse gastoDtoResp = gastoService.postGasto(gastoDtoReq);
        return gastoDtoResp;
    }

    @GetMapping({"/{id}"})
    public GastoDtoResponse getGastoById(@Validated @NotNull @PathVariable Long id) {
        GastoDtoResponse gastoDtoResp = gastoService.getGastoById(id);
        return gastoDtoResp;
    }

    /*
    @PutMapping({"/{id}"})
    public GastoDtoResponse putGastoById(@Validated @NotNull @RequestBody GastoDtoRequest gastoDtoReq,
                                         @PathVariable Long id) {
        GastoDtoResponse gastoDtoResp = gastoService.putGastoById(gastoDtoReq, id);
        return gastoDtoResp;
    }

    @PatchMapping({"/{id}"})
    public GastoDtoResponse patchGastoById(@Validated @NotNull @RequestBody GastoDtoRequest gastoDtoReq,
                                           @PathVariable Long id) {
        GastoDtoResponse gastoDtoResp = gastoService.patchGastoById(gastoDtoReq, id);
        return gastoDtoResp;
    }*/

    @DeleteMapping({"/{id}"})
    public GastoDtoResponse deleteGastoById(@Validated @NotNull @PathVariable Long id) {
        GastoDtoResponse gastoDtoResp = gastoService.deleteGastoById(id);
        return gastoDtoResp;
    }


}
