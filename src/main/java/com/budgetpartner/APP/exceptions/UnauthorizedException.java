package com.budgetpartner.APP.exceptions;

//Representa errores 401 donde la autenticación falla o no se provee token válido
public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException(String message) {
        super(message);
    }
}
