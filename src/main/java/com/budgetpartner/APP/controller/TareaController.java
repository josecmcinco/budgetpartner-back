package com.budgetpartner.APP.controller;

import com.budgetpartner.APP.dto.request.TareaDtoRequest;
import com.budgetpartner.APP.dto.response.TareaDtoResponse;
import com.budgetpartner.APP.service.TareaService;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tareas")
public class TareaController {

    @Autowired
    TareaService tareaService;

    @PostMapping
    public TareaDtoResponse postTarea(@Validated @NotNull @RequestBody TareaDtoRequest tareaDtoReq) {
        TareaDtoResponse tareaDtoResp = tareaService.postTarea(tareaDtoReq);
        return tareaDtoResp;
    }

    @GetMapping({"/{id}"})
    public TareaDtoResponse getTareaById(@Validated @NotNull @PathVariable Long id) {
        TareaDtoResponse tareaDtoResp = tareaService.getTareaById(id);
        return tareaDtoResp;
    }

    /*
    @PutMapping({"/{id}"})
    public TareaDtoResponse putTareaById(@Validated @NotNull @RequestBody TareaDtoRequest tareaDtoReq,
                                         @PathVariable Long id) {
        TareaDtoResponse tareaDtoResp = tareaService.putTareaById(tareaDtoReq, id);
        return tareaDtoResp;
    }

    @PatchMapping({"/{id}"})
    public TareaDtoResponse patchTareaById(@Validated @NotNull @RequestBody TareaDtoRequest tareaDtoReq,
                                           @PathVariable Long id) {
        TareaDtoResponse tareaDtoResp = tareaService.patchTareaById(tareaDtoReq, id);
        return tareaDtoResp;
    }*/

    @DeleteMapping({"/{id}"})
    public TareaDtoResponse deleteTareaById(@Validated @NotNull @PathVariable Long id) {
        TareaDtoResponse tareaDtoResp = tareaService.deleteTareaById(id);
        return tareaDtoResp;
    }
}