package com.budgetpartner.APP.repository;


import com.budgetpartner.APP.admin.PobladorDB;
import com.budgetpartner.APP.entity.Rol;
import com.budgetpartner.APP.enums.NombreRol;
import com.budgetpartner.APP.repository.GastoRepository;
import com.budgetpartner.config.PobladorTestConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@SpringBootTest
@Import(PobladorTestConfig.class)
public class RolRepositoryTest {

    @Autowired
    RolRepository rolRepository;
/*
TODO
    @Test
    void testObtenerMiembroPorNombre_existente() {
        Optional<Rol> resultado = rolRepository.obtenerMiembroPorNombre("ROLE_ADMIN");

        assertThat(resultado).isPresent();
        assertThat(resultado.get().getNombre()).isEqualTo("ROLE_ADMIN");
    }


    @Test
    void testObtenerMiembroPorNombre_inexistente() {
        Optional<Rol> resultado = rolRepository.obtenerMiembroPorNombre("INVITADO");

        assertThat(resultado).isPresent();
    }*/


}
