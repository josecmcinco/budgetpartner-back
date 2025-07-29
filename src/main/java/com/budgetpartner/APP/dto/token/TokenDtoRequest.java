package com.budgetpartner.APP.dto.token;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class TokenDtoRequest {

    @NotBlank(message = "El correo electrónico no puede estar vacío")
    @Email(message = "El correo electrónico no es válido")
    private String email;

    @NotBlank(message = "La contraseña no puede esta vacía")
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
