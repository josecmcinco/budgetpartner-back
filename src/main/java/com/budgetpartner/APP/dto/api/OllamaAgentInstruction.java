package com.budgetpartner.APP.dto.api;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

public class OllamaAgentInstruction {
    private String toolName;  // Ejemplo: "MiembroTools.crearMiembro"
    private List<String> arguments; // Lista de argumentos
    private Boolean finished; // Indica si la tarea terminó
    private String finalResponse; // Respuesta final para el usuario (si finished == true)


    public String getToolName() {
        return toolName;
    }

    public void setToolName(String toolName) {
        this.toolName = toolName;
    }

    public List<String> getArguments() {
        return arguments;
    }

    public void setArguments(List<String> arguments) {
        this.arguments = arguments;
    }

    public Boolean isFinished() {
        return finished;
    }

    public void setFinished(Boolean finished) {
        this.finished = finished;
    }

    public String getFinalResponse() {
        return finalResponse;
    }

    public void setFinalResponse(String finalResponse) {
        this.finalResponse = finalResponse;
    }

    public static OllamaAgentInstruction fromJson(String json) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(json, OllamaAgentInstruction.class);
        } catch (Exception e) {
            throw new RuntimeException("Error al deserializar JSON a DeepseekAgentInstruction", e);
        }
    }
}
