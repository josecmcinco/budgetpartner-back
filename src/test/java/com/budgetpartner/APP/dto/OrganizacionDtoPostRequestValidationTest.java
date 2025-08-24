package com.budgetpartner.APP.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Set;

import com.budgetpartner.APP.dto.organizacion.OrganizacionDtoPostRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class OrganizacionDtoPostRequestValidationTest {

    @Autowired
    private Validator validator;

    @Test
    void cuandoCamposSonValidos_NoHayErrores() {
        // Inicialización de variables
        String nombreOrganizacion = "Budget Partners";
        String descripcionOrganizacion = "Gestión de gastos compartidos";
        String nickMiembroCreador = "juanito";

        // Creación del DTO
        OrganizacionDtoPostRequest dto = new OrganizacionDtoPostRequest(
                nombreOrganizacion,
                descripcionOrganizacion,
                nickMiembroCreador
        );

        Set<ConstraintViolation<OrganizacionDtoPostRequest>> violations = validator.validate(dto);
        assertEquals(0, violations.size());
    }

    @Test
    void cuandoCamposSonNulosOVacios_SeDetectanErrores() {
        // Todos los campos en blanco o vacíos
        OrganizacionDtoPostRequest dto = new OrganizacionDtoPostRequest(
                "  ",   // nombre vacío
                null,   // descripción nula
                ""      // nick vacío
        );

        Set<ConstraintViolation<OrganizacionDtoPostRequest>> violations = validator.validate(dto);
        assertEquals(3, violations.size());
    }
}
