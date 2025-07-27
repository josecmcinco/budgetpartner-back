package com.budgetpartner.APP.repository;

import com.budgetpartner.APP.admin.PobladorDB;
import com.budgetpartner.APP.entity.Usuario;
import com.budgetpartner.APP.repository.GastoRepository;
import com.budgetpartner.APP.repository.UsuarioRepository;
import com.budgetpartner.config.PobladorTestConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@Import(PobladorTestConfig.class)
public class UsuarioRepositoryTest {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Test
    public void testObtenerUsuarioPorEmail_existente() {
        String email = "juan.perez@mail.com"; // Carlos Martínez en poblador

        Optional<Usuario> resultado = usuarioRepository.obtenerUsuarioPorEmail(email);

        assertThat(resultado).isPresent();
        assertThat(resultado.get().getNombre()).isEqualTo("Juan");
        assertThat(resultado.get().getApellido()).isEqualTo("Pérez");
        assertThat(resultado.get().getId()).isEqualTo(1);
    }

    @Test
    public void testObtenerUsuarioPorEmail_inexistente() {
        String email = "noexiste@example.com";

        Optional<Usuario> resultado = usuarioRepository.obtenerUsuarioPorEmail(email);

        assertThat(resultado).isEmpty();
    }
}