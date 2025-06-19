package com.budgetpartner.APP.repository;


import com.budgetpartner.APP.entity.Tarea;
import com.budgetpartner.config.PobladorTestConfig;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@SpringBootTest
@Import(PobladorTestConfig.class)
public class TareaRepositoryTest {

    @Autowired
    private TareaRepository tareaRepository;

    @Test
    void testContarTareasPorUsuarioId_existente() {
        Long usuarioId = 1L;

        Integer cantidadTareas = tareaRepository.contarTareasPorUsuarioId(usuarioId);

        assertThat(cantidadTareas).isNotNull();
        assertThat(cantidadTareas).isGreaterThan(0);
    }

    @Test
    void testContarTareasPorUsuarioId_inexistente() {
        Long usuarioIdInexistente = 999L;

        Integer cantidadTareas = tareaRepository.contarTareasPorUsuarioId(usuarioIdInexistente);

        assertThat(cantidadTareas).isNotNull();
        assertThat(cantidadTareas).isEqualTo(0);
    }
}
