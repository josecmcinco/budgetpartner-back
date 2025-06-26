package com.budgetpartner.APP.integration.repository;

import com.budgetpartner.APP.entity.Plan;
import com.budgetpartner.APP.repository.PlanRepository;
import com.budgetpartner.config.PobladorTestConfig;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import java.util.List;

@SpringBootTest
@Import(PobladorTestConfig.class)
public class PlanRepositoryTest {

    @Autowired
    private PlanRepository planRepository;



    @Test
    void testContarPlanesPorUsuarioId_existente() {
        // Carlos está vinculado a una organización con planes
        Long usuarioId = 1L;

        Integer cantidadPlanes = planRepository.contarPlanesPorUsuarioId(usuarioId);

        assertThat(cantidadPlanes).isNotNull();
        assertThat(cantidadPlanes).isGreaterThan(0);
    }

    @Test
    void testContarPlanesPorUsuarioId_inexistente() {
        Long usuarioIdInexistente = 999L;

        Integer cantidadPlanes = planRepository.contarPlanesPorUsuarioId(usuarioIdInexistente);

        assertThat(cantidadPlanes).isNotNull();
        assertThat(cantidadPlanes).isEqualTo(0);
    }

    @Test
    void testObtenerPlanesPorOrganizacionId_existente() {
        Long organizacionId = 1L; // ajusta según tu método real

        List<Plan> planes = planRepository.obtenerPlanesPorOrganizacionId(organizacionId);

        assertThat(planes).isNotEmpty();
    }

    @Test
    void testObtenerPlanesPorOrganizacionId_inexistente() {
        Long organizacionIdInexistente = 999L;

        List<Plan> planes = planRepository.obtenerPlanesPorOrganizacionId(organizacionIdInexistente);

        assertThat(planes).isEmpty();
    }

}
