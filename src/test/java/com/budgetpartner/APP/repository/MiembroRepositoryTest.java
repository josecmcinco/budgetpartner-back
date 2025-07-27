package com.budgetpartner.APP.repository;

import com.budgetpartner.APP.entity.Miembro;
import com.budgetpartner.APP.exceptions.NotFoundException;
import com.budgetpartner.APP.repository.MiembroRepository;
import com.budgetpartner.config.PobladorTestConfig;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;


import java.util.List;
import java.util.Optional;


@SpringBootTest
@Import(PobladorTestConfig.class)
public class MiembroRepositoryTest {

    @Autowired
    private MiembroRepository miembroRepository;


    @Test
    public void testObtenerMiembrosPorUsuarioId() {
        Long usuarioId = 1L; // Juan Pérez
        List<Miembro> miembros = miembroRepository.obtenerMiembrosPorUsuarioId(usuarioId);

        assertThat(miembros).isNotEmpty();
        assertThat(miembros).allMatch(m -> m.getUsuario().getId().equals(usuarioId));
    }

    @Test
    public void testContarMiembrosPorUsuarioId() {
        Long usuarioId = 3L; // Carlos Martínez → tiene 2 miembros
        Integer count = miembroRepository.contarMiembrosPorUsuarioId(usuarioId);

        assertThat(count).isEqualTo(2);
    }

    @Test
    public void testContarMiembrosPorOrganizacionId() {
        Long organizacionId = 1L; // BudgetCorp → tiene 5 miembros
        Integer count = miembroRepository.contarMiembrosPorOrganizacionId(organizacionId);

        assertThat(count).isEqualTo(5);
    }

    @Test
    public void testObtenerMiembrosPorOrganizacionId() {
        Long organizacionId = 2L; // FinanceFlow → tiene 2 miembros
        List<Miembro> miembros = miembroRepository.obtenerMiembrosPorOrganizacionId(organizacionId);

        assertThat(miembros).hasSize(2);
        assertThat(miembros).allMatch(m -> m.getOrganizacion().getId().equals(organizacionId));
    }

    @Test
    public void testObtenerMiembroConIdOrganizacionYUsuario(){
        Long usuarioId = 3L;
        Long organizacionId = 1L;

        Optional<Miembro> miembroOpt = miembroRepository.obtenerMiembroPorUsuarioYOrgId(usuarioId, organizacionId);
        assertThat(miembroOpt).isPresent();
        Miembro miembro = miembroOpt.get();

        assertThat(miembro.getUsuario().getId()).isEqualTo(usuarioId);
        assertThat(miembro.getOrganizacion().getId()).isEqualTo(organizacionId);

    }


}
