package com.budgetpartner.APP.controller;

import com.budgetpartner.APP.admin.PobladorDB;
import com.budgetpartner.APP.dto.TokenResponse;
import com.budgetpartner.APP.dto.miembro.MiembroDtoResponse;
import com.budgetpartner.APP.repository.UsuarioRepository;
import com.budgetpartner.APP.service.InvitacionService;
import com.budgetpartner.APP.service.JwtService;
import com.fasterxml.jackson.core.type.TypeReference;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Testcontainers
@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class InvitacionControllerIntegrationTest {

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
    public InvitacionControllerIntegrationTest(MockMvc mockMvc, ObjectMapper objectMapper) {
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
    }

    private static String token;

    private static String tokenValido = "a4d6bbda-93a7-47bc-9f38-5b6ec62a9478";;

    @BeforeAll
    static void init(@Autowired JwtService jwtService,
                     @Autowired UsuarioRepository usuarioRepository,
                     @Autowired PobladorDB pobladorDB) {
        pobladorDB.poblarTodo();

        var usuario = usuarioRepository.obtenerUsuarioPorEmail("carlos.martinez@mail.com")
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado. No se pueden empezar tests"));

        token = jwtService.generateToken(usuario);
    }

    @Test
    @Order(1)
    void obtenerMiembrosNoAdjuntos() throws Exception {
        var mvcResult = mockMvc.perform(get("/invitacion/{token}/asociarMiembros", tokenValido)
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String jsonResponse = mvcResult.getResponse().getContentAsString();
        List<MiembroDtoResponse> miembros = objectMapper.readValue(jsonResponse, new TypeReference<List<MiembroDtoResponse>>() {});

        assertThat(miembros).isNotNull();
        assertThat(miembros).isNotEmpty();
        assertThat(miembros.get(0).getNick()).isNotBlank();
    }

    @Test
    @Order(2)
    void generateToken() throws Exception {

        Long organizacionIdValido = 2L;
        var mvcResult = mockMvc.perform(get("/invitacion/{organizacionId}/obtenerToken", organizacionIdValido)
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String jsonResponse = mvcResult.getResponse().getContentAsString();
        TokenResponse tokenResponse = objectMapper.readValue(jsonResponse, TokenResponse.class);

        assertThat(tokenResponse).isNotNull();
        assertThat(tokenResponse.getToken()).isNotBlank();
    }

}