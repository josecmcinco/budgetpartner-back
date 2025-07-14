package com.budgetpartner.APP.integration.repository;

import com.budgetpartner.APP.entity.Miembro;
import com.budgetpartner.APP.entity.RepartoGasto;
import com.budgetpartner.APP.exceptions.NotFoundException;
import com.budgetpartner.APP.repository.MiembroRepository;
import com.budgetpartner.APP.repository.RepartoGastoRepository;
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
class RepartoGastoRepositoryTest {

    @Autowired
    private RepartoGastoRepository repartoGastoRepository;


    @Test
    void testSumarGastosPorMiembroYPlan() {

        Long planId = 2L;
        Long miembroId = 3L;

        double cantidadDevuelta = 52.44;
        //Gastos del miembro en el plan:
        //TODO EXPLICAR

        Double suma = repartoGastoRepository.sumarGastosPorMiembroYTPlanId(miembroId, planId);
        assertThat(suma).isEqualTo(cantidadDevuelta);
    }
}