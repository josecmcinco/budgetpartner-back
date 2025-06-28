package com.budgetpartner.APP.integration.controller;

import com.budgetpartner.APP.admin.PobladorDB;
import com.budgetpartner.APP.dto.miembro.MiembroDtoPostRequest;
import com.budgetpartner.APP.dto.miembro.MiembroDtoUpdateRequest;
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
    }

    @Test
    @Order(1)
    void crearMiembro() throws Exception {
        // Preparamos petición
        MiembroDtoPostRequest req = new MiembroDtoPostRequest(
                1L,
                2L,
                "nuevo_nick",
                true
        );

        //System.out.println(req.getRolId());

        mockMvc.perform(post("/miembros")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(content().string("Miembro creado correctamente"));
    }

    @Test
    @Order(2)
    void obtenerMiembroPorId() throws Exception {
        // Leemos el miembro que acabamos de crear, que tendrá id = última posición (8)
        mockMvc.perform(get("/miembros/{id}", 3L)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nick").value("cmartinez1"))
                .andExpect(jsonPath("$.isActivo").value(true));
    }

    @Test
    @Order(3)
    void actualizarMiembro() throws Exception {
        MiembroDtoUpdateRequest update = new MiembroDtoUpdateRequest(
                3L,
                2L,
                "nick_editado",
                false// desactivar
        );

        mockMvc.perform(patch("/miembros/{id}", 3L)
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(update)))
                .andExpect(status().isOk())
                .andExpect(content().string("Miembro actualizado correctamente"));
    }

    /*
    TODO NI IDEA DE QUE DEBERÍA DE HACER

    @Test
    @Order(4)
    void eliminarMiembro() throws Exception {
        mockMvc.perform(delete("/miembros/{id}", 3L)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(content().string("Miembro eliminado correctamente"));
    }*/
}