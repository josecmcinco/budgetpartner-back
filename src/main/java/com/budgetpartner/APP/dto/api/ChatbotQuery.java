package com.budgetpartner.APP.dto.api;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ChatbotQuery {


    @NotBlank(message = "El prompt no puede ser nulo")
    private String prompt;

    @NotNull(message = "Es necesario introducir si la conversación es nueva o no")
    private Boolean isConversacionNueva;

    public ChatbotQuery(String prompt, Boolean isConversacionNueva) {
        this.prompt = prompt;
        this.isConversacionNueva = isConversacionNueva;
    }

    public String getPrompt() {
        return prompt;
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }

    public Boolean isConversacionNueva() {
        return isConversacionNueva;
    }

    public void setConversacionNueva(Boolean conversacionNueva) {
        isConversacionNueva = conversacionNueva;
    }
}
