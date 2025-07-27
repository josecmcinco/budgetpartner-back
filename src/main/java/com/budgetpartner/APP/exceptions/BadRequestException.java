package com.budgetpartner.APP.exceptions;


//Representa errores 400 por datos inválidos del cliente
public class BadRequestException extends RuntimeException {
    public BadRequestException(String message) {
        super(message);
    }
}
