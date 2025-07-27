package com.budgetpartner.APP.dto.api;

public class ChatQuery {
    private String prompt;
    private boolean isConversacionNueva;

    public String getPrompt() {
        return prompt;
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }

    public boolean isConversacionNueva() {
        return isConversacionNueva;
    }

    public void setConversacionNueva(boolean conversacionNueva) {
        isConversacionNueva = conversacionNueva;
    }
}
