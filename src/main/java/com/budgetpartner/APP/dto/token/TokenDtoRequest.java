package com.budgetpartner.APP.dto.token;

public class TokenDtoRequest {

    private String email;
    private String contraseña;

    public TokenDtoRequest(String email, String contraseña) {
        this.email = email;
        this.contraseña = contraseña;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }
}
