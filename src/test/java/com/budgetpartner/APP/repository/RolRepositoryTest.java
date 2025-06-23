package com.budgetpartner.APP.repository;




import com.budgetpartner.APP.entity.Rol;
import com.budgetpartner.APP.enums.NombreRol;
import com.budgetpartner.config.PobladorTestConfig;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import java.util.Optional;

@SpringBootTest
@Import(PobladorTestConfig.class)
public class RolRepositoryTest {

    @Autowired
    private RolRepository rolRepository;

    @Test
    void testObtenerMiembroPorNombre_existente() {
        Optional<Rol> resultado = rolRepository.obtenerRolPorNombre(NombreRol.ROLE_ADMIN);

        assertThat(resultado).isPresent();
        assertThat(resultado.get().getId()).isEqualTo(2L);
    }

}