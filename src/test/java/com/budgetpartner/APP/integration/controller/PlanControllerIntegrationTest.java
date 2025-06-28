package com.budgetpartner.APP.integration.controller;

import com.budgetpartner.APP.admin.PobladorDB;
import com.budgetpartner.APP.dto.plan.PlanDtoPostRequest;
import com.budgetpartner.APP.dto.plan.PlanDtoUpdateRequest;
import com.budgetpartner.APP.enums.ModoPlan;
import com.budgetpartner.APP.repository.UsuarioRepository;
import com.budgetpartner.APP.service.JwtService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Testcontainers
@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PlanControllerIntegrationTest {

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15")
            .withDatabaseName("testdb")
            .withUsername("testuser")
            .withPassword("testpass");

    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PobladorDB pobladorDB;

    private static String token;

    @BeforeAll
    static void init(@Autowired UsuarioRepository usuarioRepository,
                     @Autowired JwtService jwtService,
                     @Autowired PobladorDB pobladorDB) {
        pobladorDB.poblarTodo();

        var usuario = usuarioRepository.obtenerUsuarioPorEmail("carlos.martinez@mail.com")
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado. No se pueden empezar tests"));

        token = jwtService.generateToken(usuario);
    }

    @Test
    @Order(1)
    void crearPlan() throws Exception {
        Long organizacionId = 1L;
        String nombre = "Plan de prueba";
        String descripcion = "Descripción del plan de prueba";
        LocalDateTime fechaInicio = LocalDateTime.now();
        LocalDateTime fechaFin = LocalDateTime.now().plusDays(10);
        ModoPlan modo =  ModoPlan.simple;

        PlanDtoPostRequest request = new PlanDtoPostRequest(organizacionId, nombre, descripcion, fechaInicio, fechaFin, modo );

        mockMvc.perform(post("/planes")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Plan de prueba"))
                .andExpect(jsonPath("$.descripcion").value("Descripción del plan de prueba"));
    }

    @Test
    @Order(2)
    void obtenerPlanPorId() throws Exception {
        mockMvc.perform(get("/planes/1")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Plan A de la Organización 1"))
                .andExpect(jsonPath("$.descripcion").value("Descripción del Plan A de la Organización. 1 PLAN SIMPLE"));
    }

    @Test
    @Order(3)
    void actualizarPlan() throws Exception {

        String nombre = "Plan actualizado";
        String descripcion = "Descripción actualizada";

        LocalDateTime fechaInicio = null;
        LocalDateTime fechaFin = LocalDateTime.now().plusDays(10);
        ModoPlan modo =  ModoPlan.simple;

        PlanDtoUpdateRequest request = new PlanDtoUpdateRequest(nombre, descripcion, fechaInicio, fechaFin, modo);

        mockMvc.perform(patch("/planes/1")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().string("Plan actualizado correctamente"));
    }

    @Test
    @Order(4)
    void eliminarPlan() throws Exception {
        mockMvc.perform(delete("/planes/1")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(content().string("Plan eliminado correctamente"));
    }
}