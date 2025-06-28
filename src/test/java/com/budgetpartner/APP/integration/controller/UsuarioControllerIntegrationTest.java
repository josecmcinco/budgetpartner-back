package com.budgetpartner.APP.integration.controller;

import com.budgetpartner.APP.admin.PobladorDB;
import com.budgetpartner.APP.dto.usuario.UsuarioDtoPostRequest;
import com.budgetpartner.APP.dto.usuario.UsuarioDtoUpdateRequest;
import com.budgetpartner.APP.dto.token.TokenDtoRequest;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Testcontainers
@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UsuarioControllerIntegrationTest {

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

    private String tokenCaducado = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIzIiwibmFtZSI6IkNhcmxvcyIsInN1YiI6ImNhcmxvcy5tYXJ0aW5lekBtYWlsLmNvbSIsImV4cCI6MTc1MTIwNTg4N30.WM9J7fJrWAFPZc0b13wCN1INQSWSwpsYFJTTEHm_j1k";

    @BeforeAll
    static void setup(@Autowired UsuarioRepository usuarioRepository,
                      @Autowired JwtService jwtService,
                      @Autowired PobladorDB pobladorDB) {
        pobladorDB.poblarTodo();

        var usuario = usuarioRepository.obtenerUsuarioPorEmail("carlos.martinez@mail.com")
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado. No se pueden empezar tests"));

        token = jwtService.generateToken(usuario);
    }

    @Test
    @Order(1)
    void registrarUsuario() throws Exception {

        String email = "nuevo.usuario@mail.com";
        String nombre = "Nuevo";
        String apellido = "Usuario";
        String contraseña = "password123";

        UsuarioDtoPostRequest request = new UsuarioDtoPostRequest(email, nombre, apellido, contraseña);

        mockMvc.perform(post("/usuarios/registro")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").exists())
                .andExpect(jsonPath("$.refreshToken").exists());
    }

    @Test
    @Order(2)
    void loginUsuario() throws Exception {

        String email = "carlos.martinez@mail.com";
        String contraseña = "contraseña789";

        TokenDtoRequest loginRequest = new TokenDtoRequest(email, contraseña);
        loginRequest.setEmail("carlos.martinez@mail.com");
        loginRequest.setContraseña("contraseña789");

        mockMvc.perform(post("/usuarios/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").exists())
                .andExpect(jsonPath("$.refreshToken").exists());
    }

    @Test
    @Order(3)
    void actualizarUsuario() throws Exception {
        String email = "carlos.martinez@mail.com";
        String nombre = "Carlos Actualizado";
        String apellido = "Martinez Actualizado";
        String contraseña = "nuevaContraseña123";

        UsuarioDtoUpdateRequest updateRequest = new UsuarioDtoUpdateRequest(email, nombre, apellido, contraseña);

        mockMvc.perform(patch("/usuarios")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isOk())
                .andExpect(content().string("Usuario actualizado correctamente"));
    }

    @Test
    @Order(4)
    void refrescarToken() throws Exception {
        // Para refrescar, usamos el token de refresco en el header Authorization
        mockMvc.perform(get("/usuarios/refrescar")
                        .header("Authorization", "Bearer " + token)) // En producción, aquí debería ir el refreshToken real
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").exists());
    }

    @Test
    @Order(5)
    void eliminarUsuario() throws Exception {
        mockMvc.perform(delete("/usuarios")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(content().string("Usuario eliminado correctamente"));
    }
}
