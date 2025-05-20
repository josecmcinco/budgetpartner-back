package com.budgetpartner.APP.controller;

import com.budgetpartner.APP.dto.request.TareaDtoRequest;
import com.budgetpartner.APP.dto.response.RolDtoResponse;
import com.budgetpartner.APP.dto.response.TareaDtoResponse;
import com.budgetpartner.APP.entity.Tarea;
import com.budgetpartner.APP.mapper.RolMapper;
import com.budgetpartner.APP.mapper.TareaMapper;
import com.budgetpartner.APP.service.TareaService;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tareas")
public class TareaController {

    @Autowired
    TareaService tareaService;

    @PostMapping
    public ResponseEntity<TareaDtoResponse> postTarea(@Validated @NotNull @RequestBody TareaDtoRequest tareaDtoReq) {
        Tarea tarea = tareaService.postTarea(tareaDtoReq);
        TareaDtoResponse tareaDtoResp = TareaMapper.toDtoResponse(tarea);
        return ResponseEntity.ok(tareaDtoResp);
    }

    @GetMapping({"/{id}"})
    public ResponseEntity<TareaDtoResponse> getTareaById(@Validated @NotNull @PathVariable Long id) {
        Tarea tarea = tareaService.getTareaById(id);
        TareaDtoResponse tareaDtoResp = TareaMapper.toDtoResponse(tarea);
        return ResponseEntity.ok(tareaDtoResp);
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
    public ResponseEntity<TareaDtoResponse> deleteTareaById(@Validated @NotNull @PathVariable Long id) {
        Tarea tarea = tareaService.deleteTareaById(id);
        TareaDtoResponse tareaDtoResp = TareaMapper.toDtoResponse(tarea);
        return ResponseEntity.ok(tareaDtoResp);
    }
}