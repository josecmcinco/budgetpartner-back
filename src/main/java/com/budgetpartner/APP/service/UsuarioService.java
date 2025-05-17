package com.budgetpartner.APP.service;

import com.budgetpartner.APP.entity.Usuario;
import com.budgetpartner.APP.dto.UsuarioDto;
import com.budgetpartner.APP.mapper.UsuarioMapper;
import com.budgetpartner.APP.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.*;

import com.budgetpartner.APP.exceptions.AppExceptions.UsuarioNotFoundException;
import com.budgetpartner.APP.exceptions.AppExceptions.InvalidRequestException;


public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Usuario actualizarUsuario(UsuarioDto dto) {
        //Comprovación de que no hay errores en la llamada
        if (dto == null) {
            throw new InvalidRequestException("UsuarioDto no puede ser null");}

        if (dto.getId() == null) {
            throw new InvalidRequestException("El id no puede ser null para actualizar un usuario");}

        Usuario usuario = usuarioRepository.findById(dto.getId())
                .orElseThrow(() -> new UsuarioNotFoundException("Usuario no encontrado con id: " + dto.getId()));

        //Crear usuario actualizado usando la Entidad de la DB y el Dto
        UsuarioMapper.updateEntityFromDto(dto, usuario);

        //Actualización de la DB
        usuarioRepository.save(usuario);

        return usuarioRepository.save(usuario);
    }
}
