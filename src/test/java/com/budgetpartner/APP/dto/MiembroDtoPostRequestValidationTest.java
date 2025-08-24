package com.budgetpartner.APP.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Set;

import com.budgetpartner.APP.dto.miembro.MiembroDtoPostRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MiembroDtoPostRequestValidationTest {

    @Autowired
    private Validator validator;

    @Test
    void cuandoCamposSonValidos_NoHayErrores() {
        // Inicialización de variables
        Long organizacionId = 1L;
        Long rolId = 2L;
        String nick = "juanito";

        // Creación del DTO
        MiembroDtoPostRequest dto = new MiembroDtoPostRequest(
                organizacionId,
                rolId,
                nick
        );

        Set<ConstraintViolation<MiembroDtoPostRequest>> violations = validator.validate(dto);
        assertEquals(0, violations.size());
    }

    @Test
    void cuandoCamposSonNulosOInvalidos_SeDetectanErrores() {
        // nick en blanco (no solo null, también espacios)
        MiembroDtoPostRequest dto = new MiembroDtoPostRequest(
                null,   // organizacionId nulo -> error
                null,   // rolId nulo -> error
                "   "   // nick en blanco -> error
        );

        Set<ConstraintViolation<MiembroDtoPostRequest>> violations = validator.validate(dto);
        assertEquals(3, violations.size());
    }
}