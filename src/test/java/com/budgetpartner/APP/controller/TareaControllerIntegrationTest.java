package com.budgetpartner.APP.controller;


import com.budgetpartner.APP.admin.PobladorDB;
import com.budgetpartner.APP.dto.tarea.TareaDtoPostRequest;
import com.budgetpartner.APP.dto.tarea.TareaDtoUpdateRequest;
import com.budgetpartner.APP.enums.EstadoTarea;
import com.budgetpartner.APP.enums.MonedasDisponibles;
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

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Testcontainers
@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TareaControllerIntegrationTest {

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

    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;

    @Autowired
    public TareaControllerIntegrationTest(MockMvc mockMvc, ObjectMapper objectMapper) {
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
    }

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
    void crearTarea() throws Exception {

        Long planId = 1L; // Asumir plan con ID 1 existe
        String titulo = "Tarea de prueba";
        String descripcion = "Descripción tarea prueba";
        LocalDateTime fechaFin = LocalDateTime.now().plusDays(5);
        EstadoTarea estado = EstadoTarea.PENDIENTE; // Ajustar según enum o validación
        double costeEstimado = 100.00;
        MonedasDisponibles moneda = MonedasDisponibles.EUR;
        List<Long> listaAtareados = Arrays.asList(1L, 2L);

        TareaDtoPostRequest request = new TareaDtoPostRequest(planId, titulo, descripcion, fechaFin, estado, costeEstimado, moneda, listaAtareados);

        mockMvc.perform(post("/tareas")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.titulo").value("Tarea de prueba"))
                .andExpect(jsonPath("$.descripcion").value("Descripción tarea prueba"))
                .andExpect(jsonPath("$.estado").value("PENDIENTE"))
                .andExpect(jsonPath("$.costeEstimado").value(100.00))
                .andExpect(jsonPath("$.moneda").value(MonedasDisponibles.EUR.toString()));
    }

    @Test
    @Order(2)
    void obtenerTareaPorId() throws Exception {
        mockMvc.perform(get("/tareas/1")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.titulo").value("Comprar comida semanal"))
                .andExpect(jsonPath("$.descripcion").value("Ir al supermercado y comprar alimentos para la semana."))
                .andExpect(jsonPath("$.moneda").value(MonedasDisponibles.EUR.toString()));
    }

    @Test
    @Order(3)
    void actualizarTarea() throws Exception {

        String titulo = "Tarea actualizada";
        String descripcion = "Descripción actualizada";
        LocalDateTime fecha = LocalDateTime.now().plusDays(5);
        EstadoTarea estado = EstadoTarea.PENDIENTE;
        double costeEstimado = 120.00;
        MonedasDisponibles moneda = MonedasDisponibles.EUR;
        List<Long> listaAtareados = Arrays.asList(2L, 3L);

        TareaDtoUpdateRequest request = new TareaDtoUpdateRequest(titulo, descripcion, fecha, estado, costeEstimado, moneda, listaAtareados);

        mockMvc.perform(patch("/tareas/1")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().string("Tarea actualizada correctamente"));
    }

    @Test
    @Order(4)
    void eliminarTarea() throws Exception {
        mockMvc.perform(delete("/tareas/1")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(content().string("Tarea eliminada correctamente"));
    }
}

