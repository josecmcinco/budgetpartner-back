package com.budgetpartner.APP.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Set;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;

import com.budgetpartner.APP.dto.estimacion.EstimacionDtoPostRequest;
import com.budgetpartner.APP.enums.MonedasDisponibles;
import com.budgetpartner.APP.enums.TipoEstimacion;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EstimacionDtoPostRequestValidationTest {

    @Autowired
    private Validator validator;

    @Test
    void cuandoCamposSonValidos_NoHayErrores() {
        // Inicialización de variables
        Long planId = 1L;
        Long tareaId = 2L;
        Long creadorId = 3L;
        Double cantidad = 150.0;
        TipoEstimacion tipoEstimacion = TipoEstimacion.ESTIMACION_TAREA;
        MonedasDisponibles tipoMoneda = MonedasDisponibles.EUR;
        String descripcion = "Estimación de coste";
        Long pagadorId = 4L;
        Long gastoId = 5L;

        // Creación del DTO con el constructor
        EstimacionDtoPostRequest dto = new EstimacionDtoPostRequest(
                planId,
                tareaId,
                creadorId,
                cantidad,
                tipoEstimacion,
                tipoMoneda,
                descripcion,
                pagadorId,
                gastoId
        );

        Set<ConstraintViolation<EstimacionDtoPostRequest>> violations = validator.validate(dto);
        assertEquals(0, violations.size());
    }

    @Test
    void cuandoCamposSonNulos_SeDetectanErrores() {
        EstimacionDtoPostRequest dto = new EstimacionDtoPostRequest(
                null, null, null, null, null, null, "  ", null, null
        );

        Set<ConstraintViolation<EstimacionDtoPostRequest>> violations = validator.validate(dto);

        assertEquals(8, violations.size());  // 8 campos a validar

    }

    @Test
    void cuandoDescripcionEsVacia_SeDetectaError() {

        // Inicialización de variables
        Long planId = 1L;
        Long tareaId = 2L;
        Long creadorId = 3L;
        Double cantidad = 150.0;
        TipoEstimacion tipoEstimacion = TipoEstimacion.ESTIMACION_TAREA;
        MonedasDisponibles tipoMoneda = MonedasDisponibles.EUR;
        String descripcion = "  ";
        Long pagadorId = 4L;
        Long gastoId = 5L;

        // Creación del DTO con el constructor
        EstimacionDtoPostRequest dto = new EstimacionDtoPostRequest(
                planId,
                tareaId,
                creadorId,
                cantidad,
                tipoEstimacion,
                tipoMoneda,
                descripcion,
                pagadorId,
                gastoId
        );


        Set<ConstraintViolation<EstimacionDtoPostRequest>> violations = validator.validate(dto);

        // Solo un error esperado en descripcion
        assertEquals(1, violations.size());
        ConstraintViolation<EstimacionDtoPostRequest> violation = violations.iterator().next();
        assertEquals("descripcion", violation.getPropertyPath().toString());
        assertEquals("La descripción no puede estar vacía", violation.getMessage());
    }
}