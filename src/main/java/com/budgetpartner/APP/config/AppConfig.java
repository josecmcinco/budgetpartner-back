package com.budgetpartner.APP.config;

import com.budgetpartner.APP.entity.Usuario;
import com.budgetpartner.APP.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
public class AppConfig {

    private final UsuarioRepository usuarioRepository;

    public AppConfig(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    /**
     * Bean para encriptar contraseñas usando BCrypt.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Bean que transforma el usuario de la DB a UserDetails para Spring Security.
     */
    @Bean
    public UserDetailsService usuarioDetailsService() {
        return username -> {
            final Usuario usuario = usuarioRepository.obtenerUsuarioPorEmail(username)
                    .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
            return org.springframework.security.core.userdetails.User.builder()
                    .username(usuario.getEmail())
                    .password(usuario.getContraseña())
                    .roles() // Puedes asignar roles si quieres
                    .build();
        };
    }

    /**
     * Bean para autenticación basada en DAO (usuario + contraseña).
     */
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(usuarioDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    /**
     * Bean que expone el AuthenticationManager de Spring Security.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}

