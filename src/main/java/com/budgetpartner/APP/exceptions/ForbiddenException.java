package com.budgetpartner.APP.exceptions;

//Representa errores 401 donde un usuario autenticado no tiene permisos para la acción
public class ForbiddenException extends RuntimeException {
    public ForbiddenException(String message) {
        super(message);
    }
}
