package com.budgetpartner.APP.integration.repository;

import com.budgetpartner.APP.entity.Organizacion;
import com.budgetpartner.APP.repository.OrganizacionRepository;
import com.budgetpartner.config.PobladorTestConfig;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import java.util.List;

@SpringBootTest
@Import(PobladorTestConfig.class)
public class OrganizacionRepositoryTest {
    @Autowired
    private OrganizacionRepository organizacionRepository;

    @Test
    void testObtenerOrganizacionesPorUsuarioId() {
        Long usuarioId = 3L; // Carlos Martínez → miembro de org1 y org2

        List<Organizacion> organizaciones = organizacionRepository.obtenerOrganizacionesPorUsuarioId(usuarioId);

        assertThat(organizaciones).hasSize(2);
        assertThat(organizaciones)
                .extracting(Organizacion::getNombre)
                .containsExactlyInAnyOrder("BudgetCorp", "FinanceFlow");
    }

    @Test
    void testObtenerOrganizacionesPorUsuarioId_SinResultados() {
        Long usuarioId = 5L; // Luis Fernández → no es miembro de ninguna org

        List<Organizacion> organizaciones = organizacionRepository.obtenerOrganizacionesPorUsuarioId(usuarioId);

        assertThat(organizaciones).isEmpty();
    }

}
