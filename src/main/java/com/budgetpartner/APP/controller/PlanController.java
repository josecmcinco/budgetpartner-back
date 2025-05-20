package com.budgetpartner.APP.controller;

import com.budgetpartner.APP.dto.request.PlanDtoRequest;
import com.budgetpartner.APP.dto.response.PlanDtoResponse;
import com.budgetpartner.APP.service.PlanService;
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

    @PostMapping
    public ResponseEntity<PlanDtoResponse> postPlan(@Validated @NotNull @RequestBody PlanDtoRequest planDtoReq) {
        PlanDtoResponse planDtoResp = planService.postPlan(planDtoReq);
        return ResponseEntity.ok(planDtoResp);
    }

    @GetMapping({"/{id}"})
    public ResponseEntity<PlanDtoResponse> getPlanById(@Validated @NotNull @PathVariable Long id) {
        PlanDtoResponse planDtoResp = planService.getPlanById(id);
        return ResponseEntity.ok(planDtoResp);
    }

    /*
    @PutMapping({"/{id}"})
    public PlanDtoResponse putPlanById(@Validated @NotNull @RequestBody PlanDtoRequest planDtoReq,
                                       @PathVariable Long id) {
        PlanDtoResponse planDtoResp = planService.putPlanById(planDtoReq, id);
        return planDtoResp;
    }

    @PatchMapping({"/{id}"})
    public PlanDtoResponse patchPlanById(@Validated @NotNull @RequestBody PlanDtoRequest planDtoReq,
                                         @PathVariable Long id) {
        PlanDtoResponse planDtoResp = planService.patchPlanById(planDtoReq, id);
        return planDtoResp;
    }*/

    @DeleteMapping({"/{id}"})
    public ResponseEntity<PlanDtoResponse> deletePlanById(@Validated @NotNull @PathVariable Long id) {
        PlanDtoResponse planDtoResp = planService.deletePlanById(id);
        return ResponseEntity.ok(planDtoResp);
    }
}
