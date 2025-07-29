package com.budgetpartner.APP.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Set;

import com.budgetpartner.APP.dto.usuario.UsuarioDtoPostRequest;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UsuarioDtoPostRequestValidationTest {

    @Autowired
    private Validator validator;

    @Test
    void cuandoCamposSonValidos_NoHayErrores() {
        String email = "usuario@example.com";
        String nombre = "Juan";
        String apellido = "Pérez";
        String contraseña = "contraseñaSegura123";

        UsuarioDtoPostRequest dto = new UsuarioDtoPostRequest(email, nombre, apellido, contraseña);

        Set<ConstraintViolation<UsuarioDtoPostRequest>> violations = validator.validate(dto);
        assertEquals(0, violations.size());
    }

    @Test
    void cuandoEmailEsInvalido_SeDetectaError() {
        String email = "emailinvalido";  // No es un email válido
        String nombre = "Juan";
        String apellido = "Pérez";
        String contraseña = "contraseñaSegura123";

        UsuarioDtoPostRequest dto = new UsuarioDtoPostRequest(email, nombre, apellido, contraseña);

        Set<ConstraintViolation<UsuarioDtoPostRequest>> violations = validator.validate(dto);

        // Esperamos 1 error por email inválido
        assertEquals(1, violations.size());

        ConstraintViolation<UsuarioDtoPostRequest> violation = violations.iterator().next();
        assertEquals("email", violation.getPropertyPath().toString());
        assertEquals("El correo electrónico no es válido", violation.getMessage());
    }

    @Test
    void cuandoCamposEstanVacios_SeDetectanErrores() {
        String email = "  ";
        String nombre = " ";
        String apellido = "";
        String contraseña = "";

        UsuarioDtoPostRequest dto = new UsuarioDtoPostRequest(email, nombre, apellido, contraseña);

        Set<ConstraintViolation<UsuarioDtoPostRequest>> violations = validator.validate(dto);

        // Esperamos 5 errores (todos campos en blanco y email no cuadra)
        assertEquals(5, violations.size());
    }
}