package com.budgetpartner.APP.aiTools;

import com.budgetpartner.APP.dto.plan.PlanDtoPostRequest;
import com.budgetpartner.APP.enums.ModoPlan;
import com.budgetpartner.APP.service.PlanService;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class PlanTools {

    @Autowired
    private PlanService planService;

    @Tool(name = "saludoPlan", description = "Saluda desde plan")
    public String saludoPlan(@ToolParam(description = "Nombre") String nombre) {
        return "Hola desde PlanTools, " + nombre;
    }

    @Tool(name = "crearPlanDesdeTexto", description = "Crea un plan en una organización. modoPlan puede ser: 'simple' o 'estructurado'.")
    public String crearPlanDesdeTexto(
            @ToolParam(description = "Id de la organización") Long organizacionId,
            @ToolParam(description = "Nombre del plan") String nombre,
            @ToolParam(description = "Modo del plan: 'simple' o 'estructurado'") String modoPlan,
            @ToolParam(description = "Descripción del plan") String _descripcion,
            @ToolParam(description = "Fecha de inicio en formato ISO (ej: 2024-06-01T00:00:00)") String _fechaInicio,
            @ToolParam(description = "Fecha de fin en formato ISO (ej: 2024-06-30T23:59:59)") String _fechaFin,
            @ToolParam(description = "Latitud de la ubicación") Double _latitud,
            @ToolParam(description = "Longitud de la ubicación") Double _longitud
    ) {
        try {
            String descripcion;
            if (_descripcion == null || _descripcion.isEmpty()) {descripcion = "";}
            else {descripcion = _descripcion;}

            LocalDateTime fechaInicio;
            if (_fechaInicio == null || _fechaInicio.isEmpty()) {fechaInicio = null;}
            else {fechaInicio = LocalDateTime.parse(_fechaInicio);}

            LocalDateTime fechaFin;
            if (_fechaFin == null || _fechaFin.isEmpty()) {fechaFin = null;}
            else {fechaFin = LocalDateTime.parse(_fechaFin);}

            PlanDtoPostRequest dto = new PlanDtoPostRequest(
                    organizacionId,
                    nombre,
                    descripcion,
                    fechaInicio,
                    fechaFin,
                    ModoPlan.valueOf(modoPlan),
                    _latitud,
                    _longitud
            );
            Long id = planService.postPlan(dto).getId();
            return "Plan creado correctamente: " + nombre + ". ID: " + id;
        } catch (Exception e) {
            return "Error al crear el plan: " + e.getMessage();
        }
    }

    @Tool(name = "obtenerPlanPorId", description = "Obtiene la información completa de un plan: gastos, tareas y estimaciones.")
    public Object obtenerPlanPorId(@ToolParam(description = "Id del plan") Long id) {
        try {
            return planService.getPlanByIdAndTrasnform(id);
        } catch (Exception e) {
            return "Error al obtener el plan: " + e.getMessage();
        }
    }
}
