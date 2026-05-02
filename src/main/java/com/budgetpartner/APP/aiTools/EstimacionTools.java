package com.budgetpartner.APP.aiTools;

import com.budgetpartner.APP.dto.estimacion.EstimacionDtoPostRequest;
import com.budgetpartner.APP.enums.MonedasDisponibles;
import com.budgetpartner.APP.enums.TipoEstimacion;
import com.budgetpartner.APP.service.EstimacionService;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EstimacionTools {

    @Autowired
    private EstimacionService estimacionService;

    @Tool(name = "saludoEstimacion", description = "Saluda desde estimacion")
    public String saludoEstimacion(@ToolParam(description = "Nombre") String nombre) {
        return "Hola desde EstimacionTools, " + nombre;
    }

    @Tool(name = "crearEstimacionDesdeTexto", description = "Crea una estimación en un plan. tipoEstimacion puede ser: 'ESTIMACION_PLAN' o 'ESTIMACION_TAREA' (requiere _tareaId).")
    public String crearEstimacionDesdeTexto(
            @ToolParam(description = "Id del plan") Long planId,
            @ToolParam(description = "Id del miembro creador") Long creadorId,
            @ToolParam(description = "Id del miembro pagador") Long pagadorId,
            @ToolParam(description = "Id del gasto asociado") Long gastoId,
            @ToolParam(description = "Cantidad estimada") Double cantidad,
            @ToolParam(description = "Tipo de estimación: 'ESTIMACION_PLAN' o 'ESTIMACION_TAREA'") String tipoEstimacion,
            @ToolParam(description = "Id de la tarea (obligatorio si tipoEstimacion es ESTIMACION_TAREA)") Long _tareaId,
            @ToolParam(description = "Descripción de la estimación") String _descripcion,
            @ToolParam(description = "Moneda (EUR por defecto)") String _moneda
    ) {
        try {
            String descripcion;
            if (_descripcion == null || _descripcion.isEmpty()) {descripcion = "";}
            else {descripcion = _descripcion;}

            MonedasDisponibles moneda;
            if (_moneda == null || _moneda.isEmpty()) {moneda = MonedasDisponibles.EUR;}
            else {moneda = MonedasDisponibles.valueOf(_moneda.toUpperCase());}

            EstimacionDtoPostRequest dto = new EstimacionDtoPostRequest(
                    planId,
                    _tareaId,
                    creadorId,
                    cantidad,
                    TipoEstimacion.valueOf(tipoEstimacion),
                    moneda,
                    descripcion,
                    pagadorId,
                    gastoId
            );
            estimacionService.postEstimacion(dto);
            return "Estimación creada correctamente";
        } catch (Exception e) {
            return "Error al crear la estimación: " + e.getMessage();
        }
    }

    @Tool(name = "obtenerEstimacionPorId", description = "Obtiene una estimación por su ID.")
    public Object obtenerEstimacionPorId(@ToolParam(description = "Id de la estimación") Long id) {
        try {
            return estimacionService.getEstimacionDtoById(id);
        } catch (Exception e) {
            return "Error al obtener la estimación: " + e.getMessage();
        }
    }
}
