package com.budgetpartner.APP.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Set;

import com.budgetpartner.APP.dto.api.ChatbotQuery;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ChatbotQueryValidationTest {

    @Autowired
    private Validator validator;

    @Test
    void cuandoCamposSonValidos_NoHayErrores() {
        ChatbotQuery query = new ChatbotQuery("¿Cómo estás?", true);

        Set<ConstraintViolation<ChatbotQuery>> violations = validator.validate(query);
        assertEquals(0, violations.size());
    }

    @Test
    void cuandoCamposSonNulosOInvalidos_SeDetectanErrores() {
        // prompt nulo o vacío
        ChatbotQuery query1 = new ChatbotQuery("  ", null);

        Set<ConstraintViolation<ChatbotQuery>> violations = validator.validate(query1);

        // Esperamos 2 errores:
        // - prompt está en blanco
        // - isConversacionNueva es null
        assertEquals(2, violations.size());

        violations.forEach(v -> System.out.println(v.getPropertyPath() + ": " + v.getMessage()));
    }
}