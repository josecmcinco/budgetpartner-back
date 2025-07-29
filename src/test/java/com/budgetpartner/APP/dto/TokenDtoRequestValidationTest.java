package com.budgetpartner.APP.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Set;

import com.budgetpartner.APP.dto.token.TokenDtoRequest;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TokenDtoRequestValidationTest {

    @Autowired
    private Validator validator;

    @Test
    void cuandoCamposSonValidos_NoHayErrores() {
        String email = "usuario@example.com";
        String contraseña = "contraseñaSegura123";

        TokenDtoRequest dto = new TokenDtoRequest(email, contraseña);

        Set<ConstraintViolation<TokenDtoRequest>> violations = validator.validate(dto);
        assertEquals(0, violations.size());
    }

    @Test
    void cuandoEmailEsInvalido_SeDetectanErrores() {
        String email = "emailinvalido";  // No es un email válido
        String contraseña = "contraseñaSegura123";

        TokenDtoRequest dto = new TokenDtoRequest(email, contraseña);

        Set<ConstraintViolation<TokenDtoRequest>> violations = validator.validate(dto);

        // Esperamos 1 error por email inválido
        assertEquals(1, violations.size());

        ConstraintViolation<TokenDtoRequest> violation = violations.iterator().next();
        assertEquals("email", violation.getPropertyPath().toString());
        assertEquals("El correo electrónico no es válido", violation.getMessage());
    }

    @Test
    void cuandoCamposEstanVacios_SeDetectanErrores() {
        String email = "   ";
        String contraseña = "   ";

        TokenDtoRequest dto = new TokenDtoRequest(email, contraseña);

        Set<ConstraintViolation<TokenDtoRequest>> violations = validator.validate(dto);

        // Esperamos 3 errores, dos para email en blanco y otro para contraseña en blanco
        assertEquals(3, violations.size());
    }
}