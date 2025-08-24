package com.budgetpartner.APP.aiTools;

import com.budgetpartner.APP.exceptions.NotFoundException;
import com.budgetpartner.APP.repository.PlanRepository;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PlanTools {

    @Autowired
    private PlanRepository planRepository;

    @Tool(name = "saludoPlan", description = "Saluda desde plan")
    public String saludoPlan(@ToolParam(description = "Nombre") String nombre) {
        return "Hola desde PlanTools, " + nombre;
    }

    @Tool(name = "obtenerPlanPorId", description = "Obtiene un plan por su ID.")
    public Object obtenerPlanPorId(@ToolParam(description = "Plan id") Long id) {
        try {
            return planRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("Plan no encontrado con id: " + id));
        } catch (Exception e) {
            return "Error al obtener el plan: " + e.getMessage();
        }
    }
}
