package com.budgetpartner.APP.service;

import com.budgetpartner.APP.dto.usuario.UsuarioDtoPostRequest;
import com.budgetpartner.APP.dto.usuario.UsuarioDtoResponse;
import com.budgetpartner.APP.dto.usuario.UsuarioDtoUpdateRequest;
import com.budgetpartner.APP.dto.token.TokenResponse;
import com.budgetpartner.APP.entity.Usuario;
import com.budgetpartner.APP.mapper.UsuarioMapper;
import com.budgetpartner.APP.repository.MiembroRepository;
import com.budgetpartner.APP.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.*;

import com.budgetpartner.APP.exceptions.NotFoundException;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;


@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;
    private MiembroRepository miembroRepository;
    private AuthenticationManager authenticationManager;

    //ESTRUCTURA GENERAL DE LA LÓGICA DE LOS CONTROLADORES
    //Pasar de DtoRequest a Entity-> Insertar en DB->Pasar de Entity a DtoRequest->Return

    public Usuario postUsuario(UsuarioDtoPostRequest UsuarioDtoReq){

        //TODO VARIABLES REPETIDAS (EMAIL)
        Usuario usuario = UsuarioMapper.toEntity(UsuarioDtoReq);
        usuarioRepository.save(usuario);
        return usuario;
    }

    public UsuarioDtoResponse getUsuarioByIdAndTransform(Long id){
        Usuario usuario = getUsuarioById(id);
        UsuarioDtoResponse dto = UsuarioMapper.toDtoResponse(usuario);

        return dto;
    }

    public Usuario deleteUsuarioById(Long id){
        //Obtener ususario usando el id pasado en la llamada
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado con id: " + id));

        usuarioRepository.delete(usuario);

        //TODO AJSUTAR DEPENDENCIAS DE BORRADO
        return usuario;
    }



    public Usuario patchUsuario(UsuarioDtoUpdateRequest dto) {

        //Obtener ususario usando el id pasado en la llamada
        Usuario usuario = usuarioRepository.findById(dto.getId())
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado con id: " + dto.getId()));

        UsuarioMapper.updateEntityFromDtoRes(dto, usuario);
        usuarioRepository.save(usuario);
        return usuario;
    }

    /// Auth
    @Autowired
    private JwtService jwtService;

    public TokenResponse register(UsuarioDtoUpdateRequest UsuarioDtoReq){

        //TODO VARIABLES REPETIDAS (EMAIL)
        Usuario usuario = UsuarioMapper.toEntity(UsuarioDtoReq);
        usuarioRepository.save(usuario);

        Usuario usuarioGuardado = usuarioRepository.save(usuario);


        String jwtToken = jwtService.generateToken(usuarioGuardado);
        String refreshToken = jwtService.generateTokenRefresh(usuarioGuardado);

        return new TokenResponse(jwtToken, refreshToken);
    }

    public TokenResponse login(UsuarioDtoUpdateRequest usuarioDtoReq){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        usuarioDtoReq.getEmail(),
                        usuarioDtoReq.getContraseña()

                )
        );

        Usuario usuario = usuarioRepository.findByEmail(usuarioDtoReq.getEmail())
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado con email: " + usuarioDtoReq.getEmail()));

        var jwtToken = jwtService.generateToken(usuario);
        var refreshToken = jwtService.generateTokenRefresh(usuario);
        //revokeAllUserTokens(user);No es necesario porque no se guardan tokens
        return new TokenResponse(jwtToken, refreshToken);
    }



    public TokenResponse refreshToken(final String authHeader){
        if(authHeader == null || !authHeader.startsWith("Bearer ")){
            throw new IllegalArgumentException("Invalid bearer token");
        }

        //Obtener token sin bearer
        final String refreshToken = authHeader.substring(7);
        final String usuarioEmail = jwtService.extractUsuario(refreshToken);

        if(usuarioEmail == null){
            throw new IllegalArgumentException("Invalid refresh token");
        }

        final Usuario usuario = usuarioRepository.findByEmail(usuarioEmail).
                orElseThrow(() -> new NotFoundException("Usuario no encontrado con email: " + usuarioEmail));

        //Conifirmar que el token es válido

        if(!jwtService.isTokenValid(refreshToken, usuario)){
            throw new IllegalArgumentException("Invalid refresh token");
        }

        final String accessToken = jwtService.generateToken(usuario);

        return new TokenResponse(accessToken, refreshToken);

    }

    //OTROS MÉTODOS
    public Usuario getUsuarioById(Long id){
        //Obtener ususario usando el id pasado en la llamada
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado con id: " + id));

        return usuario;
    }

}
