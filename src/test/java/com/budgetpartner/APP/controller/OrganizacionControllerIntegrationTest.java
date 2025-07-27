package com.budgetpartner.APP.controller;

import com.budgetpartner.APP.admin.PobladorDB;
import com.budgetpartner.APP.dto.organizacion.OrganizacionDtoPostRequest;
import com.budgetpartner.APP.dto.organizacion.OrganizacionDtoUpdateRequest;
import com.budgetpartner.APP.enums.MonedasDisponibles; // Si lo usas, sino eliminar
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
class OrganizacionControllerIntegrationTest {

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
    void crearOrganizacion() throws Exception {

        String nombreOrg = "Organización Test";
        String descripcionOrg = "Descripción de prueba para organización";
        String nick = "Paco";

        OrganizacionDtoPostRequest request = new OrganizacionDtoPostRequest(nombreOrg, descripcionOrg, nick);

        mockMvc.perform(post("/organizaciones")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.nombre").value(nombreOrg))
                .andExpect(jsonPath("$.miembros").exists())
                .andExpect(jsonPath("$.descripcion").value(descripcionOrg));

    }

    @Test
    @Order(2)
    void obtenerOrganizacionPorId() throws Exception {
        // Asumimos que la organización con id=1 existe tras poblar base o tras crearla en test anterior
        mockMvc.perform(get("/organizaciones/1")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("BudgetCorp"))
                .andExpect(jsonPath("$.descripcion").value("Gestión de presupuestos familiares."));
    }

    @Test
    @Order(3)
    void obtenerOrganizacionesPorUsuarioId() throws Exception {
        mockMvc.perform(get("/organizaciones/organizacion")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].nombre").value("BudgetCorp"));
    }

    @Test
    @Order(4)
    void actualizarOrganizacion() throws Exception {

        String nombre = "Organización Test Actualizada";
        String descrpicion = "Descripción actualizada para organización";

        OrganizacionDtoUpdateRequest request = new OrganizacionDtoUpdateRequest(nombre, descrpicion);

        mockMvc.perform(patch("/organizaciones/1")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().string("Organización actualizada correctamente"));
    }


    @Test
    @Order(5)
    void eliminarOrganizacion() throws Exception {
        mockMvc.perform(delete("/organizaciones/1")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(content().string("Organización eliminada correctamente"));
    }
}