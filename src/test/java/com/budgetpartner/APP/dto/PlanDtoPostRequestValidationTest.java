package com.budgetpartner.APP.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.util.Set;

import com.budgetpartner.APP.dto.plan.PlanDtoPostRequest;
import com.budgetpartner.APP.enums.ModoPlan;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PlanDtoPostRequestValidationTest {

    @Autowired
    private Validator validator;

    @Test
    void cuandoCamposSonValidos_NoHayErrores() {
        Long organizacionId = 1L;
        String nombre = "Plan A";
        String descripcion = "Descripción del plan";
        LocalDateTime fechaInicio = LocalDateTime.now();
        LocalDateTime fechaFin = fechaInicio.plusDays(30);
        ModoPlan modoPlan = ModoPlan.estructurado;

        PlanDtoPostRequest dto = new PlanDtoPostRequest(
                organizacionId,
                nombre,
                descripcion,
                fechaInicio,
                fechaFin,
                modoPlan,
                null,
                null
        );

        Set<ConstraintViolation<PlanDtoPostRequest>> violations = validator.validate(dto);
        assertEquals(0, violations.size());
    }

    @Test
    void cuandoCamposSonNulosOVacios_SeDetectanErrores() {
        PlanDtoPostRequest dto = new PlanDtoPostRequest(
                null,      // organizacionId nulo
                "  ",      // nombre vacío (solo espacios)
                null,      // descripcion (no tiene validación, puede ser null)
                null,      // fechaInicio (no tiene validación)
                null,      // fechaFin (no tiene validación)
                null,       // modoPlan nulo
                null,       //latitud
                null        //longitud
        );

        Set<ConstraintViolation<PlanDtoPostRequest>> violations = validator.validate(dto);
        // organizacionId y modoPlan nulos + nombre vacío = 3 errores
        assertEquals(3, violations.size());
    }
}