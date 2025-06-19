package com.budgetpartner.APP.service;

import com.budgetpartner.APP.dto.token.TokenDtoRequest;
import com.budgetpartner.APP.dto.usuario.UsuarioDtoPostRequest;
import com.budgetpartner.APP.dto.usuario.UsuarioDtoResponse;
import com.budgetpartner.APP.dto.usuario.UsuarioDtoUpdateRequest;
import com.budgetpartner.APP.dto.token.TokenDtoResponse;
import com.budgetpartner.APP.entity.Usuario;
import com.budgetpartner.APP.mapper.UsuarioMapper;
import com.budgetpartner.APP.repository.MiembroRepository;
import com.budgetpartner.APP.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.*;

import com.budgetpartner.APP.exceptions.NotFoundException;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UsuarioService {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private MiembroRepository miembroRepository;

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private PasswordEncoder passwordEncoder;

    //ESTRUCTURA GENERAL DE LA LÓGICA DE LOS CONTROLADORES
    //Pasar de DtoRequest a Entity-> Insertar en DB->Pasar de Entity a DtoRequest->Return

    //Llamada para Endpoint
    //Elimina una Entidad usando el id recibido por el usuario
     /*DEVUELVE AL USUARIO:

    */

    //Llamada para Endpoint
    //Elimina una Entidad usando el id recibido por el usuario
    public Usuario deleteUsuarioById(){

        Usuario usuario = devolverUsuarioAutenticado();

        usuarioRepository.delete(usuario);

        //TODO AJSUTAR DEPENDENCIAS DE BORRADO
        return usuario;
    }

    //Llamada para Endpoint
    //Actualiza una Entidad usando el id recibido por el usuario
    public Usuario patchUsuario(UsuarioDtoUpdateRequest dto) {

        Usuario usuario = devolverUsuarioAutenticado();

        UsuarioMapper.updateEntityFromDtoRes(dto, usuario);
        usuarioRepository.save(usuario);
        return usuario;
    }



    //Relacionado con JWT

    //Llamada para Endpoint
    //Crea una Entidad usando el DTO recibido por el usuario
    //Devuelve los JWT
    public TokenDtoResponse register(UsuarioDtoPostRequest UsuarioDtoReq){

        //TODO VARIABLES REPETIDAS (EMAIL)
        Usuario usuario = UsuarioMapper.toEntity(UsuarioDtoReq);
        usuarioRepository.save(usuario);

        Usuario usuarioGuardado = usuarioRepository.save(usuario);

        String jwtToken = jwtService.generateToken(usuarioGuardado);
        String refreshToken = jwtService.generateTokenRefresh(usuarioGuardado);

        return new TokenDtoResponse(jwtToken, refreshToken);
    }


    //Llamada para Endpoint
    //Devuelve los JWT si coincide el usuario y contraseña
    public TokenDtoResponse login(TokenDtoRequest dto){

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            dto.getEmail(),
                            dto.getContraseña()

                    )
            );
        }
        catch(Exception E){System.out.println(E.getMessage());}

        Usuario usuario = usuarioRepository.obtenerUsuarioPorEmail(dto.getEmail())
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado con email: " + dto.getEmail()));
        var jwtToken = jwtService.generateToken(usuario);
        var refreshToken = jwtService.generateTokenRefresh(usuario);
        return new TokenDtoResponse(jwtToken, refreshToken);
    }


    //Llamada para Endpoint
    //Devuelve el token de autentificacion si el de refresco es correcto
    public TokenDtoResponse refreshToken(final String authHeader){
            if(authHeader == null || !authHeader.startsWith("Bearer ")){
            throw new IllegalArgumentException("Invalid bearer token");
        }

        //Obtener token de refresco sin bearer
        final String refreshToken = authHeader.substring(7);
        final String usuarioEmail = jwtService.extractEmailUsuario(refreshToken);

        //Comprobar que existe un usuario con ese coreeo
        //NUNCA DEBERÍA DE ERRAR
        if(usuarioEmail == null){
            throw new IllegalArgumentException("Invalid refresh token");
        }

        final Usuario usuario = usuarioRepository.obtenerUsuarioPorEmail(usuarioEmail).
                orElseThrow(() -> new NotFoundException("Usuario no encontrado con email: " + usuarioEmail));

        //Conifirmar que el token de refresco es válido
        if(!jwtService.isTokenValid(refreshToken, usuario)){
            throw new IllegalArgumentException("Invalid refresh token");
        }

        final String accessToken = jwtService.generateToken(usuario);

        return new TokenDtoResponse(accessToken, refreshToken);

    }

    //OTROS MÉTODOS


    //USADO ANTES DE GESTIONAR LA PETICIÓN
    //Permite saber si el token es correcto
    public Usuario devolverUsuarioAutenticado(){

        String usuarioEmail = SecurityContextHolder.getContext().getAuthentication().getName();

        /*
        if(authHeader == null || !authHeader.startsWith("Bearer ")){
            throw new IllegalArgumentException("Invalid bearer token");
        }

        //Obtener token de de autentificación sin bearer
        final String authenticationToken = authHeader.substring(7);
        final String usuarioEmail = jwtService.extractEmailUsuario(authenticationToken);

        //Comprobar que existe un usuario con ese coreeo
        //NUNCA DEBERÍA DE ERRAR
        if(usuarioEmail == null){
            throw new IllegalArgumentException("Invalid refresh token");
        }*/

        final Usuario usuario = usuarioRepository.obtenerUsuarioPorEmail(usuarioEmail)
                .orElseThrow(() -> new NotFoundException("Miembro no encontrado con id: " + usuarioEmail));

        //Conifirmar que el token de refresco es válido
        /*
        if(!jwtService.isTokenValid(authenticationToken, usuario)){
            throw new IllegalArgumentException("Invalid refresh token");
        }*/
        return usuario;
    }

}
