package com.budgetpartner.APP.service;

import com.budgetpartner.APP.dto.rol.RolDtoPostRequest;
import com.budgetpartner.APP.entity.Rol;
import com.budgetpartner.APP.exceptions.NotFoundException;
import com.budgetpartner.APP.mapper.RolMapper;
import com.budgetpartner.APP.repository.RolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RolService {

    @Autowired
    private RolRepository rolRepository;

    //ESTRUCTURA GENERAL DE LA LÓGICA DE LOS CONTROLADORES
    //Pasar de DtoRequest a Entity-> Insertar en DB->Pasar de Entity a DtoRequest->Return

    public Rol postRol(RolDtoPostRequest rolDtoReq) {

        //TODO VALIDAR CAMPOS REPETIDOS (nombre, permisos, etc.)
        Rol rol = RolMapper.toEntity(rolDtoReq);
        rolRepository.save(rol);
        return rol;
    }

    public Rol getRolById(Long id) {
        //Obtener rol usando el id pasado en la llamada
        Rol rol = rolRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Rol no encontrado con id: " + id));

        return rol;
    }

    public Rol deleteRolById(Long id) {
        //Obtener rol usando el id pasado en la llamada
        Rol rol = rolRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Rol no encontrado con id: " + id));

        rolRepository.delete(rol);

        //TODO AJUSTAR DEPENDENCIAS DE BORRADO (usuarios asignados, permisos, etc.)
        return rol;
    }

    public Rol actualizarRol(RolDtoPostRequest dto, Long id) {
        //Obtener rol usando el id pasado en la llamada
        Rol rol = rolRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Rol no encontrado con id: " + id));

        RolMapper.updateEntityFromDtoRes(dto, rol);
        rolRepository.save(rol);
        return rol;
    }

    }
