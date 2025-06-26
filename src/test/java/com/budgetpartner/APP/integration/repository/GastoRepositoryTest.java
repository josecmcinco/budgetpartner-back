package com.budgetpartner.APP.integration.repository;

import com.budgetpartner.APP.admin.PobladorDB;
import com.budgetpartner.APP.entity.Gasto;
import com.budgetpartner.APP.repository.GastoRepository;
import com.budgetpartner.config.PobladorTestConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@SpringBootTest
@Import(PobladorTestConfig.class)
public class GastoRepositoryTest {

    @Autowired
    private GastoRepository gastoRepository;


    @Test
    public void testObtenerGastoPorPlanId() {
        Long planId = 1L; // Plan A de la Organización 1
        List<Gasto> gastos = gastoRepository.obtenerGastosPorPlanId(planId);

        assertThat(gastos).isNotEmpty();
        assertThat(gastos).allMatch(g -> g.getPlan().getId().equals(planId));
    }

    @Test
    public void testObtenerGastosPorTareaId() {
        Long tareaId = 1L; // "Comprar comida semanal"
        List<Gasto> gastos = gastoRepository.obtenerGastosPorTareaId(tareaId);

        assertThat(gastos).isNotEmpty();
        assertThat(gastos).allMatch(g -> g.getTarea().getId().equals(tareaId));
    }
}