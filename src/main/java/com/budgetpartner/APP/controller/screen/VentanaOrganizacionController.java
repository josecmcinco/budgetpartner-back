package com.budgetpartner.APP.controller.screen;

import com.budgetpartner.APP.dto.response.GastoDtoResponse;
import com.budgetpartner.APP.dto.response.MiembroDtoResponse;
import com.budgetpartner.APP.dto.response.OrganizacionDtoResponse;
import com.budgetpartner.APP.dto.response.PlanDtoResponse;
import com.budgetpartner.APP.dto.screen.VentanaOrganizacionDto;
import com.budgetpartner.APP.entity.Gasto;
import com.budgetpartner.APP.entity.Miembro;
import com.budgetpartner.APP.entity.Organizacion;
import com.budgetpartner.APP.entity.Plan;
import com.budgetpartner.APP.mapper.GastoMapper;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
/*
@RestController
@RequestMapping()
public class VentanaOrganizacionController {


    @GetMapping({"organizationScreen/{organizacionId}"})
    public ResponseEntity<VentanaOrganizacionDto> getVentanaOrganizacionController(@Validated @NotNull @PathVariable Long organizacionId) {
        OrganizacionDtoResponse organizacion = null;
        PlanDtoResponse plan = null;
        List<MiembroDtoResponse> miembros = Arrays.asList();

        VentanaOrganizacionDto ventanaOrganizacionDto = new VentanaOrganizacionDto(organizacion, plan, miembros);
        return ResponseEntity.ok(ventanaOrganizacionDto);
    }

    -
    -Dashborard- Ultima actividad  - no org no planes no tareas
    -OrganizacionesVentana -NumMiembros -NumPlanes
    -Organizacion - Organizacion-Miembro-Rol-Plan-Tarea-GastoTotal-GastoGlobal

}
*/