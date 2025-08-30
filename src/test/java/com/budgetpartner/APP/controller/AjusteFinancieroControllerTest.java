package com.budgetpartner.APP.controller;

import com.budgetpartner.APP.admin.PobladorDB;
import com.budgetpartner.APP.dto.ajusteFinanciero.AjusteFinancieroDtoPostRequest;
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

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Testcontainers
@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AjusteFinancieroControllerTest {


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
    void crearAjusteFinanciero() throws Exception {
        Long organizacionId = 1L;

        AjusteFinancieroDtoPostRequest dto = new AjusteFinancieroDtoPostRequest(2L, 3L, 150.75);

        mockMvc.perform(post("/organizaciones/{organizacionId}/ajustes-financieros", organizacionId)
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.cantidad").value(150.75))
                .andExpect(jsonPath("$.pagadorId").value(2))
                .andExpect(jsonPath("$.beneficiarioId").value(3));

    }
}
