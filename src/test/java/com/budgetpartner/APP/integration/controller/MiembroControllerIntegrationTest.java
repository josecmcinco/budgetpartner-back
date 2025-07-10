package com.budgetpartner.APP.integration.controller;

import com.budgetpartner.APP.admin.PobladorDB;
import com.budgetpartner.APP.dto.miembro.MiembroDtoPostRequest;
import com.budgetpartner.APP.dto.miembro.MiembroDtoResponse;
import com.budgetpartner.APP.dto.miembro.MiembroDtoUpdateRequest;
import com.budgetpartner.APP.repository.UsuarioRepository;
import com.budgetpartner.APP.service.JwtService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Testcontainers
@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class MiembroControllerIntegrationTest {

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15")
            .withDatabaseName("testdb")
            .withUsername("testuser")
            .withPassword("testpass");

    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url",    postgres::getJdbcUrl);
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
    private static String token2;

    @BeforeAll
    static void init(@Autowired UsuarioRepository usuarioRepository,
                     @Autowired JwtService jwtService,
                     @Autowired PobladorDB pobladorDB) {
        // Poblamos DB con datos necesarios (organizaciones, roles, usuarios, miembros iniciales…)
        pobladorDB.poblarTodo();

        var usuario = usuarioRepository
                .obtenerUsuarioPorEmail("carlos.martinez@mail.com")
                .orElseThrow(() -> new RuntimeException("Usuario para tests no encontrado"));

        token = jwtService.generateToken(usuario);


        //Token usado para asicar y desasociar usuario porque el usuario no está en la org2
        var usuario2 = usuarioRepository
                .obtenerUsuarioPorEmail("carlos.martinez@mail.com")
                .orElseThrow(() -> new RuntimeException("Usuario para tests no encontrado"));

        token2 = jwtService.generateToken(usuario2);
    }

    @Test
    @Order(1)
    void crearMiembro() throws Exception {
        // Preparación petición
        MiembroDtoPostRequest req = new MiembroDtoPostRequest(
                1L,
                2L,
                "cmartinez10"
        );


        mockMvc.perform(post("/miembros")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.nick").value("cmartinez10"))
                .andExpect(jsonPath("$.isAsociado").value(false))
                .andExpect(jsonPath("$.isActivo").value(true));
    }

    @Test
    @Order(2)
    void obtenerMiembroPorId() throws Exception {
        mockMvc.perform(get("/miembros/{id}", 3L)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nick").value("cmartinez1"))
                .andExpect(jsonPath("$.isAsociado").value(true));
    }

    @Test
    @Order(3)
    void actualizarMiembro() throws Exception {
        MiembroDtoUpdateRequest update = new MiembroDtoUpdateRequest(
                3L,
                2L,
                "nick_editado"
        );

        mockMvc.perform(patch("/miembros/{id}", 3L)
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(update)))
                .andExpect(status().isOk())
                .andExpect(content().string("Miembro actualizado correctamente"));
    }


    @Test
    @Order(4)
    void asociarMiembro() throws Exception {

        Long idMiembroAsociado = 5L;

        mockMvc.perform(patch("/miembros/{id}/associate", idMiembroAsociado)
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nick").value("org1_invitado"))
                .andExpect(jsonPath("$.isAsociado").value(true));
    }

    @Test
    @Order(5)
    void desasociarMiembro() throws Exception {
        Long idMiembroAsociado = 5L;

        mockMvc.perform(patch("/miembros/{id}/dissociate", idMiembroAsociado)
                        .header("Authorization", "Bearer " + token2))
                .andExpect(status().isOk())
                .andExpect(content().string("Miembro desasociado correctamente"));
    }

    @Test
    @Order(6)
    void obtenerMiembroConUsuarioYOrgId() throws Exception {
        Long idOrganizacion = 2L;
        mockMvc.perform(get("/miembros/organizacion/{organizacionId}", idOrganizacion)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nick").value("cmartinez2"))
                .andExpect(jsonPath("$.isAsociado").value(true));
    }

    @Test
    @Order(7)
    void eliminarMiembro() throws Exception {
        mockMvc.perform(delete("/miembros/{id}", 3L)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(content().string("Miembro eliminado correctamente"));
    }
}