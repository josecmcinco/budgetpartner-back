package com.budgetpartner.APP.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.Set;

import com.budgetpartner.APP.dto.tarea.TareaDtoPostRequest;
import com.budgetpartner.APP.enums.EstadoTarea;
import com.budgetpartner.APP.enums.MonedasDisponibles;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TareaDtoPostRequestValidationTest {

    @Autowired
    private Validator validator;

    @Test
    void cuandoCamposSonValidos_NoHayErrores() {
        Long planId = 1L;
        String titulo = "Implementar login";
        String descripcion = "Desarrollar el módulo de autenticación";
        LocalDateTime fechaFin = LocalDateTime.now().plusDays(10);
        EstadoTarea estado = EstadoTarea.PENDIENTE;

        // Lista no vacía y sin nulos
        var listaAtareados = Arrays.asList(1L, 2L, 3L);

        TareaDtoPostRequest dto = new TareaDtoPostRequest(
                planId,
                titulo,
                descripcion,
                fechaFin,
                estado,
                listaAtareados
        );

        Set<ConstraintViolation<TareaDtoPostRequest>> violations = validator.validate(dto);
        assertEquals(0, violations.size());
    }

    @Test
    void cuandoCamposSonInvalidos_SeDetectanErrores() {
        TareaDtoPostRequest dto = new TareaDtoPostRequest(
                null,       // planId nulo
                "  ",       // titulo vacío
                null,       // descripcion sin validación
                null,       // fechaFin nulo
                null,       // estado sin validación
                Collections.emptyList() // listaAtareados vacía
        );

        Set<ConstraintViolation<TareaDtoPostRequest>> violations = validator.validate(dto);

        // Validaciones que fallan:
        // planId, titulo, fechaFin, listaAtareados (vacía)
        // Total 4 errores esperados
        assertEquals(4, violations.size());
    }

    @Test
    void cuandoListaAtareadosContieneNulos_SeDetectaError() {
        Long planId = 1L;
        String titulo = "Tarea con lista inválida";
        LocalDateTime fechaFin = LocalDateTime.now().plusDays(5);
        var listaAtareados = Arrays.asList(1L, null, 3L);

        TareaDtoPostRequest dto = new TareaDtoPostRequest(
                planId,
                titulo,
                null,
                fechaFin,
                null,
                listaAtareados
        );

        Set<ConstraintViolation<TareaDtoPostRequest>> violations = validator.validate(dto);

        // Un error por el null dentro de la lista
        assertEquals(1, violations.size());

        ConstraintViolation<TareaDtoPostRequest> violation = violations.iterator().next();
        String path = violation.getPropertyPath().toString();
        assertTrue(path.equals("listaAtareados[1]") || path.equals("listaAtareados[1].<list element>"));
        assertEquals("Cada ID en la lista de atareados debe ser válido", violation.getMessage());
    }
}