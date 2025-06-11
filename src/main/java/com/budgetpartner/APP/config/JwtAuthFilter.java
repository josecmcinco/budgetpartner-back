package com.budgetpartner.APP.config;

import com.budgetpartner.APP.entity.Usuario;
import com.budgetpartner.APP.repository.UsuarioRepository;
import com.budgetpartner.APP.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.http.HttpHeaders;

import java.io.IOException;
import java.util.Optional;

//Require Args constructor
@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private UsuarioRepository usuarioRepository;


    //Asegura que el filtro vaya verificado y sea correcto
    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        // Si no OK sigue con lo tuyo
        if(request.getServletPath().contains("/auth")){
            filterChain.doFilter(request, response);
            return;
        }

        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (authHeader == null || !authHeader.startsWith("Bearer ") ){
            filterChain.doFilter(request, response);
            return;

        }

        //El token a partir del 7º carácter
        final String jwtToken = authHeader.substring(7);
        final String ususarioEmail = jwtService.extractEmailUsuario(jwtToken);

        if(ususarioEmail == null || SecurityContextHolder.getContext().getAuthentication() != null){
            return;
        }

        final UserDetails userDetails = this.userDetailsService.loadUserByUsername(ususarioEmail);
        final Optional<Usuario> usuario = usuarioRepository.findByEmail(userDetails.getUsername());

        if(usuario.isEmpty()){
            filterChain.doFilter(request, response);
            return;
        }
        final boolean isTokenValid = jwtService.isTokenValid(jwtToken, usuario.get());

        if(!isTokenValid){
            return;
        }

        final var authToken = new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.getAuthorities()
        );

        authToken.setDetails(
                new WebAuthenticationDetailsSource().buildDetails(request)
        );

        SecurityContextHolder.getContext().setAuthentication(authToken);

        filterChain.doFilter(request, response);
    }
}
