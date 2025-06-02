package com.budgetpartner.APP.config;

import com.budgetpartner.APP.entity.Usuario;
import com.budgetpartner.APP.repository.UsuarioRepository;
import io.jsonwebtoken.security.Password;
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
import org.springframework.stereotype.Service;

@Service
//@Configuration
public class AppConfig {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    //Transformación del usuario de la DB al usuario de S.B
    @Bean
    public UserDetailsService usuarioDetailsService(){

        return username ->{
            final Usuario usuario = usuarioRepository.findByEmail(username)
                    .orElseThrow(()->new UsernameNotFoundException("Usuario no encontradoBBB"));
            return org.springframework.security.core.userdetails.User.builder()
                    .username(usuario.getEmail())
                    .password(usuario.getContraseña())
                    .build();
        };
    }


    //Como sabe spring si la contraseña y el uus son correctos e inyectar en spring
    @Bean
    public AuthenticationProvider authenticationProvider (AuthenticationConfiguration config) throws Exception{
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(usuarioDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;

    }

    @Bean
    public AuthenticationManager authenticationManager (AuthenticationConfiguration config) throws Exception{
        return config.getAuthenticationManager();
    }
}
