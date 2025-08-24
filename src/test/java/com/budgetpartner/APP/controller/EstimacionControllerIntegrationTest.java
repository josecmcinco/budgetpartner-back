package com.budgetpartner.APP.controller;

import com.budgetpartner.APP.admin.PobladorDB;
import com.budgetpartner.APP.dto.estimacion.EstimacionDtoPostRequest;
import com.budgetpartner.APP.dto.estimacion.EstimacionDtoUpdateRequest;
import com.budgetpartner.APP.enums.MonedasDisponibles;
import com.budgetpartner.APP.enums.TipoEstimacion;
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

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Testcontainers
@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class EstimacionControllerIntegrationTest {

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
    void crearEstimacionPlan() throws Exception {

        //Datos para EstimacionDtoPostRequest
        Long planId = 1L;
        Long tareaId = null; //Es estimación de tipo plan -> tareaId tiene que ser null
        Long creadorId = 3L;
        double cantidad = 150.75;
        TipoEstimacion tipoEstimacion = TipoEstimacion.ESTIMACION_PLAN;
        MonedasDisponibles tipoMoneda = MonedasDisponibles.EUR;
        String descripcion = "Estimación de plan para la tarea";
        Long pagadorId = 4L;
        Long gastoId = 5L;

        //Creación del dto
        EstimacionDtoPostRequest request = new EstimacionDtoPostRequest(
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


        mockMvc.perform(post("/estimaciones")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tareaId").exists())
                .andExpect(jsonPath("$.creadorId").value(creadorId))
                .andExpect(jsonPath("$.cantidad").value(cantidad))
                .andExpect(jsonPath("$.tipoEstimacion").value(TipoEstimacion.ESTIMACION_PLAN.toString()))
                .andExpect(jsonPath("$.tipoMoneda").value("EUR"))
                .andExpect(jsonPath("$.descripcion").value(descripcion))
                .andExpect(jsonPath("$.pagadorId").value(pagadorId))
                .andExpect(jsonPath("$.gastoId").value(gastoId));
    }

    @Test
    @Order(2)
    void crearEstimacionTarea() throws Exception {

        //Datos para EstimacionDtoPostRequest
        Long planId = 1L;
        Long tareaId = 1L; //Es estimación de tipo plan -> Ha de ser distinto de null
        Long creadorId = 3L;
        double cantidad = 150.75;
        TipoEstimacion tipoEstimacion = TipoEstimacion.ESTIMACION_TAREA;
        MonedasDisponibles tipoMoneda = MonedasDisponibles.EUR;
        String descripcion = "Estimación inicial para la tarea";
        Long pagadorId = 4L;
        Long gastoId = 5L;

        //Creación del dto
        EstimacionDtoPostRequest request = new EstimacionDtoPostRequest(
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


        mockMvc.perform(post("/estimaciones")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tareaId").value(tareaId))
                .andExpect(jsonPath("$.creadorId").value(creadorId))
                .andExpect(jsonPath("$.cantidad").value(cantidad))
                .andExpect(jsonPath("$.tipoEstimacion").value(TipoEstimacion.ESTIMACION_TAREA.toString()))
                .andExpect(jsonPath("$.tipoMoneda").value("EUR"))
                .andExpect(jsonPath("$.descripcion").value(descripcion))
                .andExpect(jsonPath("$.pagadorId").value(pagadorId))
                .andExpect(jsonPath("$.gastoId").value(gastoId));
    }

    @Test
    @Order(3)
    void obtenerEstimacionPorId() throws Exception {
        mockMvc.perform(get("/estimaciones/1")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.planId").value(2))
                .andExpect(jsonPath("$.tareaId").value(2))
                .andExpect(jsonPath("$.creadorId").value(1))
                .andExpect(jsonPath("$.cantidad").value(24.52))
                .andExpect(jsonPath("$.tipoEstimacion").value(TipoEstimacion.ESTIMACION_TAREA.toString()))
                .andExpect(jsonPath("$.tipoMoneda").value("EUR"))
                .andExpect(jsonPath("$.descripcion").value("Estimacion de tipo Tarea con pagador"))
                .andExpect(jsonPath("$.pagadorId").value(2))
                .andExpect(jsonPath("$.gastoId").value(1));
    }

    @Test
    @Order(4)
    void actualizarEstimacion() throws Exception {
        //Datos para EstimacionDtoUpdateRequest
        double cantidad = 150.75;
        TipoEstimacion tipoEstimacion = TipoEstimacion.ESTIMACION_PLAN;
        MonedasDisponibles tipoMoneda = MonedasDisponibles.EUR;
        String descripcion = "Estimación inicial para la tarea";
        Long pagadorId = 4L;
        Long gastoId = 5L;

        //Creación del dto
        EstimacionDtoUpdateRequest request = new EstimacionDtoUpdateRequest(
                cantidad,
                tipoMoneda,
                descripcion,
                pagadorId,
                gastoId
        );

        mockMvc.perform(patch("/estimaciones/1")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().string("Estimación actualizada correctamente"));
    }

    @Test
    @Order(5)
    void eliminarEstimacion() throws Exception {
        mockMvc.perform(delete("/estimaciones/1")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(content().string("Estimación eliminada correctamente"));
    }
/*
TODO PARA CUANDO FUNCIONEN LOS ERRORES

    @Test
    @Order(2)
    void crearEstimacionPlanConTareaIdDevuelveError() throws Exception {

        // Datos incorrectos: estimación de tipo plan, pero con tareaId
        Long planId = 1L;
        Long tareaId = 1L; // ERROR: no debería haber tareaId si es de tipo plan
        Long creadorId = 3L;
        double cantidad = 150.75;
        TipoEstimacion tipoEstimacion = TipoEstimacion.ESTIMACION_PLAN;
        MonedasDisponibles tipoMoneda = MonedasDisponibles.EUR;
        String descripcion = "Estimación incorrecta para plan con tarea";
        Long pagadorId = 4L;
        Long gastoId = 5L;

        EstimacionDtoPostRequest request = new EstimacionDtoPostRequest(
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

        mockMvc.perform(post("/estimaciones")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("No se puede asociar una tarea a una estimación de tipo plan"));
    }*/
}