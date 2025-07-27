package com.budgetpartner.APP.dto.api;

import java.util.List;

public class OpenAIAgentInstruction {
    private String toolName;  // Ejemplo: "MiembroTools.crearMiembro"
    private List<String> arguments; // Lista de argumentos
    private boolean finished; // Indica si la tarea terminó
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

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public String getFinalResponse() {
        return finalResponse;
    }

    public void setFinalResponse(String finalResponse) {
        this.finalResponse = finalResponse;
    }
}
