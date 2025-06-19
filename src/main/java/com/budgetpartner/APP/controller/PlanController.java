package com.budgetpartner.APP.controller;

import com.budgetpartner.APP.dto.plan.PlanDtoResponse;
import com.budgetpartner.APP.dto.plan.PlanDtoPostRequest;
import com.budgetpartner.APP.dto.plan.PlanDtoUpdateRequest;
import com.budgetpartner.APP.entity.Plan;
import com.budgetpartner.APP.mapper.PlanMapper;
import com.budgetpartner.APP.service.PlanService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/planes")
public class PlanController {

    @Autowired
    PlanService planService;

    @Operation(
            summary = "Crear un plan",
            description = "Crea un plan nuevo dado su organizacionId/nombre/Descripcion/fechaIncio/FechaFin/modoPlan. Devuelve un mensaje de confirmación..",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Plan creado correctamente"
                    )
            }
    )
    @PostMapping
    public ResponseEntity<PlanDtoResponse> postPlan(@Validated @NotNull @RequestBody PlanDtoPostRequest planDtoReq) {
        Plan plan = planService.postPlan(planDtoReq);
        PlanDtoResponse planDtoResp = PlanMapper.toDtoResponse(plan);
        return ResponseEntity.ok(planDtoResp);
    }

    @Operation(
            summary = "Obtener un plan por ID",
            description = "Devuelve un plan existente dado un id.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Plan obtenido correctamente",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(
                                            name = "MensajeConfirmacion",
                                            summary = "Mensaje de éxito",
                                            value = "PENDIENTE"
                                    )
                            )
                    )
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<PlanDtoResponse> getPlanById(@Validated @NotNull @PathVariable Long id) {
        PlanDtoResponse planDtoResp= planService.getPlanByIdAndTrasnform(id);
        return ResponseEntity.ok(planDtoResp);
    }

    @Operation(
            summary = "Actualizar parcialmente un plan",
            description = "Actualiza los campos indicados de un plan existente. Devuelve un mensaje de confirmación.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Plan actualizado correctamente"
                    )
            }
    )
    @PatchMapping("/{id}")
    public ResponseEntity<String> patchPlanById(@Validated @NotNull @RequestBody PlanDtoUpdateRequest planDtoUpReq,
                                                @PathVariable Long id) {
        planService.patchPlan(planDtoUpReq, id);
        return ResponseEntity.ok("Plan actualizado correctamente");
    }

    @Operation(
            summary = "Eliminar un plan por ID",
            description = "Elimina un plan existente dado su ID. Devuelve un mensaje de confirmación.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Plan eliminado correctamente"
                    )
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePlanById(@Validated @NotNull @PathVariable Long id) {
        Plan plan = planService.deletePlanById(id);
        PlanDtoResponse planDtoResp = PlanMapper.toDtoResponse(plan);
        return ResponseEntity.ok("Plan eliminado correctamente");
    }
}
