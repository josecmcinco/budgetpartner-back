package com.budgetpartner.APP.dto.api;

public class ChatResponse {
    private String result;

    public ChatResponse(String result) {
        this.result = result;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
