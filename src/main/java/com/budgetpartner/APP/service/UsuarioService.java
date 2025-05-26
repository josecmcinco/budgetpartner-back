package com.budgetpartner.APP.service;

import com.budgetpartner.APP.dto.request.UsuarioDtoRequest;
import com.budgetpartner.APP.dto.response.TokenResponse;
import com.budgetpartner.APP.entity.Usuario;
import com.budgetpartner.APP.dto.response.UsuarioDtoResponse;
import com.budgetpartner.APP.mapper.UsuarioMapper;
import com.budgetpartner.APP.repository.MiembroRepository;
import com.budgetpartner.APP.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.*;

import com.budgetpartner.APP.exceptions.NotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private MiembroRepository miembroRepository;

    //ESTRUCTURA GENERAL DE LA LÓGICA DE LOS CONTROLADORES
    //Pasar de DtoRequest a Entity-> Insertar en DB->Pasar de Entity a DtoRequest->Return

    public Usuario postUsuario(UsuarioDtoRequest UsuarioDtoReq){

        //TODO VARIABLES REPETIDAS (EMAIL)
        Usuario usuario = UsuarioMapper.toEntity(UsuarioDtoReq);
        usuarioRepository.save(usuario);
        return usuario;
    }

    public Usuario getUsuarioById(Long id){
        //Obtener ususario usando el id pasado en la llamada
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado con id: " + id));

        return usuario;
    }

    public Usuario deleteUsuarioById(Long id){
        //Obtener ususario usando el id pasado en la llamada
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado con id: " + id));

        usuarioRepository.delete(usuario);

        //TODO AJSUTAR DEPENDENCIAS DE BORRADO
        return usuario;
    }



    public Usuario actualizarUsuario(UsuarioDtoRequest dto, Long id) {

        //Obtener ususario usando el id pasado en la llamada
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado con id: " + id));

        UsuarioMapper.updateEntityFromDtoRes(dto, usuario);
        usuarioRepository.save(usuario);
        return usuario;
    }

    /// Auth
    //@Service
    private JwtService jwtService;

    public TokenResponse register(UsuarioDtoRequest UsuarioDtoReq){

        //TODO VARIABLES REPETIDAS (EMAIL)
        Usuario usuario = UsuarioMapper.toEntity(UsuarioDtoReq);
        usuarioRepository.save(usuario);

        Usuario usuarioGuardado = usuarioRepository.save(usuario);

        var jwtToken = jwtService.generateToken(usuarioGuardado);
        var refreshToken = jwtService.generateTokenRefresh(usuarioGuardado);

        return new TokenResponse(jwtToken, refreshToken);
    }
}
