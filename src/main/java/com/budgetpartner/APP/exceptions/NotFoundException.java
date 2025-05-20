package com.budgetpartner.APP.exceptions;

//Representa errores 404 por recursos no encontrados
public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }
}
