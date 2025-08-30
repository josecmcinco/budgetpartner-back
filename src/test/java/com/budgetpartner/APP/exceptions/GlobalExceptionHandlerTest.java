package com.budgetpartner.APP.exceptions;

import com.budgetpartner.APP.dto.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler handler;
    private HttpServletRequest request;

    @BeforeEach
    void setUp() {
        handler = new GlobalExceptionHandler();
        request = Mockito.mock(HttpServletRequest.class);
        Mockito.when(request.getRequestURI()).thenReturn("/test-uri");
    }

    @Test
    void testHandleBadRequest() {
        BadRequestException ex = new BadRequestException("Bad request error");
        ResponseEntity<ErrorResponse> response = handler.handleBadRequest(ex, request);

        assertEquals(400, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals("Bad request error", response.getBody().getMessage());
        assertEquals("/test-uri", response.getBody().getPath());
    }

    @Test
    void testHandleUnauthorized() {
        UnauthorizedException ex = new UnauthorizedException("Unauthorized access");
        ResponseEntity<ErrorResponse> response = handler.handleUnauthorized(ex, request);

        assertEquals(401, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals("Unauthorized access", response.getBody().getMessage());
        assertEquals("/test-uri", response.getBody().getPath());
    }

    @Test
    void testHandleForbidden() {
        ForbiddenException ex = new ForbiddenException("Forbidden operation");
        ResponseEntity<ErrorResponse> response = handler.handleForbidden(ex, request);

        assertEquals(403, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals("Forbidden operation", response.getBody().getMessage());
        assertEquals("/test-uri", response.getBody().getPath());
    }

    @Test
    void testHandleNotFound() {
        NotFoundException ex = new NotFoundException("Resource not found");
        ResponseEntity<ErrorResponse> response = handler.handleNotFound(ex, request);

        assertEquals(404, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals("Resource not found", response.getBody().getMessage());
        assertEquals("/test-uri", response.getBody().getPath());
    }

    @Test
    void testHandleConflict() {
        ConflictException ex = new ConflictException("Conflict occurred");
        ResponseEntity<ErrorResponse> response = handler.handleConflict(ex, request);

        assertEquals(409, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals("Conflict occurred", response.getBody().getMessage());
        assertEquals("/test-uri", response.getBody().getPath());
    }

    @Test
    void testHandleGenericException() {
        Exception ex = new Exception("Generic error");
        ResponseEntity<ErrorResponse> response = handler.handleGeneric(ex, request);

        assertEquals(500, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals("Error interno del servidor", response.getBody().getMessage());
        assertEquals("/test-uri", response.getBody().getPath());
    }
}
