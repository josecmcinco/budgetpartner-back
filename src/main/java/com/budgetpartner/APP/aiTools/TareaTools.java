package com.budgetpartner.APP.aiTools;

import com.budgetpartner.APP.dto.tarea.TareaDtoPostRequest;
import com.budgetpartner.APP.enums.EstadoTarea;
import com.budgetpartner.APP.service.TareaService;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class TareaTools {

    @Autowired
    private TareaService tareaService;

    @Tool(name = "saludoTarea", description = "Saluda desde tarea")
    public String saludoTarea(@ToolParam(description = "Nombre") String nombre) {
        return "Hola desde TareaTools, " + nombre;
    }

    @Tool(name = "crearTareaDesdeTexto", description = "Crea una tarea en un plan. estado puede ser: 'PENDIENTE', 'EN_PROCESO' o 'COMPLETADA'.")
    public String crearTareaDesdeTexto(
            @ToolParam(description = "Id del plan") Long planId,
            @ToolParam(description = "Título de la tarea") String titulo,
            @ToolParam(description = "Fecha de fin en formato ISO (ej: 2024-06-30T23:59:59)") String fechaFin,
            @ToolParam(description = "Ids de miembros asignados separados por comas (ej: 1,2,3)") String listaAtareados,
            @ToolParam(description = "Descripción de la tarea") String _descripcion,
            @ToolParam(description = "Estado de la tarea: 'PENDIENTE', 'EN_PROCESO' o 'COMPLETADA'") String _estado
    ) {
        try {
            String descripcion;
            if (_descripcion == null || _descripcion.isEmpty()) {descripcion = "";}
            else {descripcion = _descripcion;}

            EstadoTarea estado;
            if (_estado == null || _estado.isEmpty()) {estado = EstadoTarea.PENDIENTE;}
            else {estado = EstadoTarea.valueOf(_estado);}

            List<Long> atareados = Arrays.stream(listaAtareados.split(","))
                    .map(String::trim)
                    .map(Long::parseLong)
                    .collect(Collectors.toList());

            TareaDtoPostRequest dto = new TareaDtoPostRequest(
                    planId,
                    titulo,
                    descripcion,
                    LocalDateTime.parse(fechaFin),
                    estado,
                    atareados
            );
            Long id = tareaService.postTarea(dto).getId();
            return "Tarea creada correctamente: " + titulo + ". ID: " + id;
        } catch (Exception e) {
            return "Error al crear la tarea: " + e.getMessage();
        }
    }

    @Tool(name = "obtenerTareaPorId", description = "Obtiene una tarea por su ID.")
    public Object obtenerTareaPorId(@ToolParam(description = "Id de la tarea") Long id) {
        try {
            return tareaService.getTareaDtoById(id);
        } catch (Exception e) {
            return "Error al obtener la tarea: " + e.getMessage();
        }
    }
}
