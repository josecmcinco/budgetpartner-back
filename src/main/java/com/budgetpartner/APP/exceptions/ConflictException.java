package com.budgetpartner.APP.exceptions;

//Representa errores 409 donde un usuario autenticado no tiene permisos para la acción
public class ConflictException extends RuntimeException {
    public ConflictException(String message) {
        super(message);
    }
}
