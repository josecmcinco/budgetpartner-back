package com.budgetpartner.APP.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.budgetpartner.APP.dto.gasto.GastoDtoPostRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class GastoDtoPostRequestValidationTest {

    @Autowired
    private Validator validator;

    @Test
    void cuandoCamposSonValidos_NoHayErrores() {
        // Inicialización de variables
        Long tareaId = 10L;
        Long planId = 1L;
        Double cantidad = 200.0;
        String nombre = "Cena grupal";
        Long pagadorId = 5L;
        String descripcion = "Cena del equipo después de la reunión";
        List<Long> listaMiembrosEndeudados = List.of(2L, 3L, 4L);

        // Crear DTO
        GastoDtoPostRequest dto = new GastoDtoPostRequest(
                tareaId,
                planId,
                cantidad,
                nombre,
                pagadorId,
                descripcion,
                listaMiembrosEndeudados
        );

        Set<ConstraintViolation<GastoDtoPostRequest>> violations = validator.validate(dto);
        assertEquals(0, violations.size());
    }

    @Test
    void cuandoCamposSonNulosOInvalidos_SeDetectanErrores() {

        List<Long> listaConNull = new ArrayList<>();
        listaConNull.add(null);
        GastoDtoPostRequest dto = new GastoDtoPostRequest(
                null,           // tareaId es opcional
                null,           // planId nulo -> error
                null,           // cantidad nula -> error
                "   ",          // nombre en blanco -> error
                null,           // pagadorId nulo -> error
                "",             // descripción vacía -> error
                listaConNull    // miembro endeudado nulo -> error
        );

        Set<ConstraintViolation<GastoDtoPostRequest>> violations = validator.validate(dto);
        assertEquals(6, violations.size());

        violations.forEach(v ->
                System.out.println(v.getPropertyPath() + ": " + v.getMessage())
        );
    }

    @Test
    void cuandoListaDeMiembrosEsVacia_SeDetectaError() {
        // Inicialización de variables válidas
        Long tareaId = 10L;
        Long planId = 1L;
        Double cantidad = 200.0;
        String nombre = "Cena grupal";
        Long pagadorId = 5L;
        String descripcion = "Cena del equipo después de la reunión";
        List<Long> listaMiembrosEndeudados = List.of(); // Vacía

        // Crear DTO
        GastoDtoPostRequest dto = new GastoDtoPostRequest(
                tareaId,
                planId,
                cantidad,
                nombre,
                pagadorId,
                descripcion,
                listaMiembrosEndeudados
        );

        Set<ConstraintViolation<GastoDtoPostRequest>> violations = validator.validate(dto);

        assertEquals(1, violations.size());
        ConstraintViolation<GastoDtoPostRequest> violation = violations.iterator().next();
        assertEquals("listaMiembrosEndeudados", violation.getPropertyPath().toString());
        assertEquals("La lista de miembros endeudados no puede estar vacía", violation.getMessage());
    }
}
