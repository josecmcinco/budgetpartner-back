package com.budgetpartner.APP.aiTools;

import com.budgetpartner.APP.dto.gasto.GastoDtoPostRequest;
import com.budgetpartner.APP.enums.MonedasDisponibles;
import com.budgetpartner.APP.service.GastoService;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class GastoTools {

    @Autowired
    private GastoService gastoService;

    @Tool(name = "saludoGasto", description = "Saluda desde gasto")
    public String saludoGasto(@ToolParam(description = "Nombre") String nombre) {
        return "Hola desde GastoTools, " + nombre;
    }

    @Tool(name = "crearGastoDesdeTexto", description = "Crea un gasto en un plan. moneda puede ser: 'EUR', 'USD', 'GBP', etc.")
    public String crearGastoDesdeTexto(
            @ToolParam(description = "Id del plan") Long planId,
            @ToolParam(description = "Nombre del gasto") String nombre,
            @ToolParam(description = "Cantidad del gasto") Double cantidad,
            @ToolParam(description = "Id del miembro pagador") Long pagadorId,
            @ToolParam(description = "Ids de miembros endeudados separados por comas (ej: 1,2,3)") String listaMiembrosEndeudados,
            @ToolParam(description = "Id de la tarea asociada (nulo en planes simples)") Long _tareaId,
            @ToolParam(description = "Descripción del gasto") String _descripcion,
            @ToolParam(description = "Moneda del gasto (EUR por defecto)") String _moneda
    ) {
        try {
            String descripcion;
            if (_descripcion == null || _descripcion.isEmpty()) {descripcion = "";}
            else {descripcion = _descripcion;}

            MonedasDisponibles moneda;
            if (_moneda == null || _moneda.isEmpty()) {moneda = MonedasDisponibles.EUR;}
            else {moneda = MonedasDisponibles.valueOf(_moneda.toUpperCase());}

            List<Long> endeudados = Arrays.stream(listaMiembrosEndeudados.split(","))
                    .map(String::trim)
                    .map(Long::parseLong)
                    .collect(Collectors.toList());

            GastoDtoPostRequest dto = new GastoDtoPostRequest(
                    _tareaId,
                    planId,
                    cantidad,
                    nombre,
                    pagadorId,
                    descripcion,
                    endeudados,
                    moneda
            );
            Long id = gastoService.postGasto(dto).getId();
            return "Gasto creado correctamente: " + nombre + " por " + cantidad + " " + moneda + ". ID: " + id;
        } catch (Exception e) {
            return "Error al crear el gasto: " + e.getMessage();
        }
    }

    @Tool(name = "obtenerGastoPorId", description = "Obtiene un gasto por su ID.")
    public Object obtenerGastoPorId(@ToolParam(description = "Id del gasto") Long id) {
        try {
            return gastoService.getGastoDtoById(id);
        } catch (Exception e) {
            return "Error al obtener el gasto: " + e.getMessage();
        }
    }
}
