package com.budgetpartner.APP.service;

import com.budgetpartner.APP.dto.request.RolDtoRequest;
import com.budgetpartner.APP.dto.response.RolDtoResponse;
import com.budgetpartner.APP.entity.Rol;
import com.budgetpartner.APP.exceptions.AppExceptions.RolNotFoundException;
import com.budgetpartner.APP.mapper.RolMapper;
import com.budgetpartner.APP.repository.PlanRepository;
import com.budgetpartner.APP.repository.RolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RolService {

    @Autowired
    private RolRepository rolRepository;

    //ESTRUCTURA GENERAL DE LA LÓGICA DE LOS CONTROLADORES
    //Pasar de DtoRequest a Entity-> Insertar en DB->Pasar de Entity a DtoRequest->Return

    public RolDtoResponse postRol(RolDtoRequest rolDtoReq) {

        //TODO VALIDAR CAMPOS REPETIDOS (nombre, permisos, etc.)
        Rol rol = RolMapper.toEntity(rolDtoReq);
        rolRepository.save(rol);
        return RolMapper.toDtoResponse(rol);
    }

    public RolDtoResponse getRolById(Long id) {
        //Obtener rol usando el id pasado en la llamada
        Rol rol = rolRepository.findById(id)
                .orElseThrow(() -> new RolNotFoundException("Rol no encontrado con id: " + id));

        return RolMapper.toDtoResponse(rol);
    }

    public RolDtoResponse deleteRolById(Long id) {
        //Obtener rol usando el id pasado en la llamada
        Rol rol = rolRepository.findById(id)
                .orElseThrow(() -> new RolNotFoundException("Rol no encontrado con id: " + id));

        rolRepository.delete(rol);

        //TODO AJUSTAR DEPENDENCIAS DE BORRADO (usuarios asignados, permisos, etc.)
        return RolMapper.toDtoResponse(rol);
    }

    public RolDtoResponse actualizarRol(RolDtoRequest dto, Long id) {
        //Obtener rol usando el id pasado en la llamada
        Rol rol = rolRepository.findById(id)
                .orElseThrow(() -> new RolNotFoundException("Rol no encontrado con id: " + id));

        RolMapper.updateEntityFromDtoRes(dto, rol);
        rolRepository.save(rol);
        return RolMapper.toDtoResponse(rol);
    }

    }
