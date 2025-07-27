package com.budgetpartner.APP.mcp;

import com.budgetpartner.APP.repository.*;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EstimacionTools {

    @Autowired
    private EstimacionRepository estimacionRepository;
    @Autowired
    private PlanRepository planRepository;
    @Autowired
    private TareaRepository tareaRepository;
    @Autowired
    private MiembroRepository miembroRepository;
    @Autowired
    private GastoRepository gastoRepository;

    @Tool(name = "saludoEstimacion", description = "Saluda desde estimacion")
    public String saludoEstimacion(@ToolParam(description = "Nombre") String nombre) {
        return "Hola desde EstimacionTools, " + nombre;
    }
/*
    @Tool(name = "crearEstimacionDesdeTexto", description = "Crea una estimación con los datos en lenguaje natural.")
    public String crearEstimacionDesdeTexto(@ToolParam(description = "estimación") EstimacionLlmCompletionDto dto) {
        try {
            Plan plan = planRepository.findById(1L).orElse(null);
            Tarea tarea = tareaRepository.findById(1L).orElse(null);
            Miembro creador = miembroRepository.findById(1L).orElse(null);
            Miembro pagador = miembroRepository.findById(2L).orElse(null);
            Gasto gasto = gastoRepository.findById(1L).orElse(null);

            Estimacion estimacion = new Estimacion(
                    plan, tarea, creador, dto.getCantidad(),
                    dto.getTipoEstimacion(), dto.getTipoMoneda(),
                    dto.getDescripcion(), pagador, gasto
            );
            estimacionRepository.save(estimacion);
            return "Estimación creada correctamente: " + dto.getDescripcion();
        } catch (Exception e) {
            return "No se pudo crear la estimación: " + e.getMessage();
        }
    }*/

}
