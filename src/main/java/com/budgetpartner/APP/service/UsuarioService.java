package com.budgetpartner.APP.service;

import com.budgetpartner.APP.dto.token.TokenDtoRequest;
import com.budgetpartner.APP.dto.usuario.UsuarioDtoPostRequest;
import com.budgetpartner.APP.dto.usuario.UsuarioDtoUpdateRequest;
import com.budgetpartner.APP.dto.token.TokenDtoResponse;
import com.budgetpartner.APP.entity.Usuario;
import com.budgetpartner.APP.exceptions.BadRequestException;
import com.budgetpartner.APP.exceptions.UnauthorizedException;
import com.budgetpartner.APP.mapper.UsuarioMapper;
import com.budgetpartner.APP.repository.MiembroRepository;
import com.budgetpartner.APP.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.*;

import com.budgetpartner.APP.exceptions.NotFoundException;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


/**
 * Servicio encargado de la gestión de usuarios y autenticación JWT.
 */
@Service
public class UsuarioService {

    private final JwtService jwtService;
    private final AutorizacionService autorizacionService;
    private final UsuarioRepository usuarioRepository;
    private final MiembroRepository miembroRepository;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UsuarioService(JwtService jwtService,
                          AutorizacionService autorizacionService,
                          UsuarioRepository usuarioRepository,
                          MiembroRepository miembroRepository,
                          AuthenticationManager authenticationManager,
                          PasswordEncoder passwordEncoder) {
        this.jwtService = jwtService;
        this.autorizacionService = autorizacionService;
        this.usuarioRepository = usuarioRepository;
        this.miembroRepository = miembroRepository;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Elimina al usuario autenticado.
     *
     * @return usuario eliminado
     */
    public Usuario deleteUsuarioById(){

        Usuario usuario = autorizacionService.devolverUsuarioAutenticado();

        //Eliminar usuario. Quita los valores de sus claves ajenas
        usuarioRepository.delete(usuario);

        return usuario;
    }

    /**
     * Actualiza los datos del usuario autenticado.
     *
     * @param dto DTO con los datos a actualizar
     * @return usuario actualizado
     */
    public Usuario patchUsuario(UsuarioDtoUpdateRequest dto) {
        Usuario usuario = autorizacionService.devolverUsuarioAutenticado();

        // Hashear contraseña si se proporciona
        if (dto.getContraseña() != null) {
            dto.setContraseña(passwordEncoder.encode(dto.getContraseña()));
        }

        UsuarioMapper.updateEntityFromDtoRes(dto, usuario);
        return usuarioRepository.save(usuario);
    }

    /**
     * Registra un nuevo usuario y devuelve tokens JWT.
     *
     * @param usuarioDtoReq DTO con datos del usuario
     * @return tokens JWT
     */
    public TokenDtoResponse register(UsuarioDtoPostRequest usuarioDtoReq){

        //TODO VARIABLES REPETIDAS (EMAIL)

        //Cifrar contraseña
        String contraseñaHash = passwordEncoder.encode(usuarioDtoReq.getContraseña());
        usuarioDtoReq.setContraseña(contraseñaHash); //Todo: cifrado en el mapper

        Usuario usuario = UsuarioMapper.toEntity(usuarioDtoReq);
        usuarioRepository.save(usuario);

        Usuario usuarioGuardado = usuarioRepository.save(usuario);

        String jwtToken = jwtService.generateToken(usuarioGuardado);
        String refreshToken = jwtService.generateTokenRefresh(usuarioGuardado);

        return new TokenDtoResponse(jwtToken, refreshToken);
    }


    /**
     * Autentica un usuario y devuelve tokens JWT si las credenciales son correctas.
     *
     * @param dto DTO con email y contraseña
     * @return tokens JWT
     */
    public TokenDtoResponse login(TokenDtoRequest dto) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getContraseña())
            );
        } catch (BadCredentialsException ex) {
            throw new BadRequestException("Email o contraseña incorrectos");
        } catch (DisabledException ex) {
            throw new UnauthorizedException("Usuario deshabilitado");
        } catch (LockedException ex) {
            throw new UnauthorizedException("Cuenta bloqueada");
        } catch (AuthenticationException ex) {
            throw new UnauthorizedException("Error de autenticación");
        }

        Usuario usuario = usuarioRepository.obtenerUsuarioPorEmail(dto.getEmail())
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado con email: " + dto.getEmail()));

        String jwtToken = jwtService.generateToken(usuario);
        String refreshToken = jwtService.generateTokenRefresh(usuario);
        return new TokenDtoResponse(jwtToken, refreshToken);
    }


    /**
     * Refresca el token de autenticación usando un refresh token válido.
     *
     * @param authHeader encabezado Authorization con el refresh token
     * @return nuevo token de acceso y refresh token
     */
    public TokenDtoResponse refreshToken(final String authHeader){
            if(authHeader == null || !authHeader.startsWith("Bearer ")){
            throw new BadRequestException("Invalid bearer token");
        }

        //Obtener token de refresco sin bearer
        final String refreshToken = authHeader.substring(7);
        final String usuarioEmail = jwtService.extractEmailUsuario(refreshToken);

        //Comprobar que existe un usuario con ese correo
        //NUNCA DEBERÍA DE ERRAR
        if(usuarioEmail == null){
            throw new BadRequestException("Invalid refresh token");
        }

        final Usuario usuario = usuarioRepository.obtenerUsuarioPorEmail(usuarioEmail).
                orElseThrow(() -> new NotFoundException("Usuario no encontrado con email: " + usuarioEmail));

        //Conifirmar que el token de refresco es válido
        if(!jwtService.isTokenValid(refreshToken, usuario)){
            throw new BadRequestException("Invalid refresh token");
        }

        final String accessToken = jwtService.generateToken(usuario);

        return new TokenDtoResponse(accessToken, refreshToken);

    }

}
