package com.budgetpartner.APP.integration.controller;

import com.budgetpartner.APP.admin.PobladorDB;
import com.budgetpartner.APP.dto.gasto.GastoDtoPostRequest;
import com.budgetpartner.APP.entity.Usuario;
import com.budgetpartner.APP.repository.UsuarioRepository;
import com.budgetpartner.APP.service.JwtService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Testcontainers
@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class GastoControllerIntegrationTest {

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

    //Uso de JWT
    @Autowired
    private JwtService jwtService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private PobladorDB pobladorDB;


    private static Long createdGastoId;

    private static String token;

    @BeforeAll
    static void init(@Autowired UsuarioRepository usuarioRepository,
                     @Autowired JwtService jwtService,
                     @Autowired PobladorDB pobladorDb

    ) {
        pobladorDb.poblarTodo();

        Usuario usuario = usuarioRepository.obtenerUsuarioPorEmail("carlos.martinez@mail.com")
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado. No se pueden empezar tests"));
        token = jwtService.generateToken(usuario);
    }


    @Test
    @Order(1)
    void crearGasto() throws Exception {
        Long planId = 2L;
        Long tareaId = 1L;
        Long pagadorId = 3L;
        double cantidad = 100;
        String nombre = "Comida";
        String descripcion = "Pizza entre amigos";
        List<Long> miembrosEndeudados = Arrays.asList(1L, 2L);

        GastoDtoPostRequest request = new GastoDtoPostRequest(tareaId, planId, cantidad, nombre, pagadorId, descripcion, miembrosEndeudados);

        mockMvc.perform(post("/gastos")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().string("Gasto creado correctamente"));
    }

    @Test
    @Order(2)
    void obtenerGastoPorId() throws Exception {

        mockMvc.perform(get("/gastos/2")
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Transporte"))
                .andExpect(jsonPath("$.cantidad").value(120.0));
    }

    @Test
    @Order(3)
    void actualizarGasto() throws Exception {
        Long pagadorId = 1L;
        double cantidad = 100;
        String nombre = "Alimentacion";
        String descripcion = "Pizza entre amigos";
        List<Long> miembrosEndeudados = Arrays.asList(1L, 2L, 3L);

        var updateRequest = new com.budgetpartner.APP.dto.gasto.GastoDtoUpdateRequest(cantidad, nombre, pagadorId, descripcion, miembrosEndeudados );

        mockMvc.perform(patch("/gastos/1")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isOk())
                .andExpect(content().string("Gasto actualizado correctamente"));
    }

    @Test
    @Order(4)
    void eliminarGasto() throws Exception {
        mockMvc.perform(delete("/gastos/1")
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(content().string("Gasto eliminado correctamente"));
    }

    /*
    TODO CUANDO SE GESTIONEN LOS ERRORES CORRECTAMENTE
    @Test
    @Order(5)
    void crearGastoConError() throws Exception {
        Long planId = 1L; // Plan 1 es de tipo simple. No puede tener tarea
        Long tareaId = 1L;
        Long pagadorId = 3L;
        double cantidad = 100;
        String nombre = "Comida";
        String descripcion = "Pizza entre amigos";
        List<Long> miembrosEndeudados = Arrays.asList(1L, 2L);

        GastoDtoPostRequest request = new GastoDtoPostRequest(tareaId, planId, cantidad, nombre, pagadorId, descripcion, miembrosEndeudados);

        mockMvc.perform(post("/gastos")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().is4xxClientError()) // o isBadRequest(), isNotFound(), etc.
                .andExpect(content().string("Se está tratando de asignar una tarea a un gasto en un plan simple"));
    }*/


}