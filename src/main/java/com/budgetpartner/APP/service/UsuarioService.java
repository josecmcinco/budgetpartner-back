package com.budgetpartner.APP.service;

import com.budgetpartner.APP.dto.request.UsuarioDtoRequest;
import com.budgetpartner.APP.entity.Usuario;
import com.budgetpartner.APP.dto.response.UsuarioDtoResponse;
import com.budgetpartner.APP.mapper.UsuarioMapper;
import com.budgetpartner.APP.repository.MiembroRepository;
import com.budgetpartner.APP.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.*;

import com.budgetpartner.APP.exceptions.NotFoundException;
import org.springframework.stereotype.Service;


@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private MiembroRepository miembroRepository;

    //ESTRUCTURA GENERAL DE LA LÓGICA DE LOS CONTROLADORES
    //Pasar de DtoRequest a Entity-> Insertar en DB->Pasar de Entity a DtoRequest->Return

    public UsuarioDtoResponse postUsuario(UsuarioDtoRequest UsuarioDtoReq){

        //TODO VARIABLES REPETIDAS (EMAIL)
        Usuario usuario = UsuarioMapper.toEntity(UsuarioDtoReq);
        usuarioRepository.save(usuario);
        return UsuarioMapper.toDtoResponse(usuario);
    }

    public UsuarioDtoResponse getUsuarioById(Long id){
        //Obtener ususario usando el id pasado en la llamada
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado con id: " + id));

        return UsuarioMapper.toDtoResponse(usuario);
    }

    public UsuarioDtoResponse deleteUsuarioById(Long id){
        //Obtener ususario usando el id pasado en la llamada
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado con id: " + id));

        usuarioRepository.delete(usuario);

        //TODO AJSUTAR DEPENDENCIAS DE BORRADO
        return UsuarioMapper.toDtoResponse(usuario);
    }



    public UsuarioDtoResponse actualizarUsuario(UsuarioDtoRequest dto, Long id) {

        //Obtener ususario usando el id pasado en la llamada
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado con id: " + id));

        UsuarioMapper.updateEntityFromDtoRes(dto, usuario);
        usuarioRepository.save(usuario);
        return UsuarioMapper.toDtoResponse(usuario);
    }
}
