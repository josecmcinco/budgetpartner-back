package com.budgetpartner.APP.exceptions;

import java.time.LocalDateTime;

public class ErrorDto {
    private LocalDateTime timestamp;
    private int status;
    private String message;
    private String path;

    public ErrorDto(int status, String message, String path) {
        this.timestamp = LocalDateTime.now();
        this.status = status;
        this.message = message;
        this.path = path;
    }

    // Getters y setters
}
