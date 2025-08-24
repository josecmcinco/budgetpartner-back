package com.budgetpartner.APP.aiTools;

import com.budgetpartner.APP.exceptions.NotFoundException;
import com.budgetpartner.APP.repository.TareaRepository;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TareaTools {

    @Autowired
    private TareaRepository tareaRepository;

    @Tool(name = "saludoTarea", description = "Saluda desde tarea")
    public String saludoTarea(@ToolParam(description = "Nombre") String nombre) {
        return "Hola desde TareaTools, " + nombre;
    }


    @Tool(name = "obtenerTareaPorId", description = "Obtiene una tarea por su ID.")
    public Object obtenerTareaPorId(@ToolParam(description = "Tarea id") Long id) {
        try {
            return tareaRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("Tarea no encontrada con id: " + id));
        } catch (Exception e) {
            return "Error al obtener la tarea: " + e.getMessage();
        }
    }
}
