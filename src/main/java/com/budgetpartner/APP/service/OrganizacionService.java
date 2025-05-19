package com.budgetpartner.APP.service;

import com.budgetpartner.APP.dto.request.OrganizacionDtoRequest;
import com.budgetpartner.APP.dto.response.OrganizacionDtoResponse;
import com.budgetpartner.APP.entity.Organizacion;
import com.budgetpartner.APP.mapper.OrganizacionMapper;
import com.budgetpartner.APP.repository.OrganizacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.budgetpartner.APP.exceptions.AppExceptions.OrganizacionNotFoundException;

import java.util.List;

@Service
public class OrganizacionService {

    @Autowired
    private OrganizacionRepository organizacionRepository;

    public OrganizacionDtoResponse postOrganizacion(OrganizacionDtoRequest organizacionDtoReq) {

        //TODO VARIABLES REPETIDAS (NOMBRE, CÓDIGO, ETC. según tu lógica de negocio)
        Organizacion organizacion = OrganizacionMapper.toEntity(organizacionDtoReq);
        organizacionRepository.save(organizacion);
        return OrganizacionMapper.toDtoResponse(organizacion);
    }

    public OrganizacionDtoResponse getOrganizacionById(Long id) {
        //Obtener organización usando el id pasado en la llamada
        Organizacion organizacion = organizacionRepository.findById(id)
                .orElseThrow(() -> new OrganizacionNotFoundException("Organización no encontrada con id: " + id));

        return OrganizacionMapper.toDtoResponse(organizacion);
    }

    public OrganizacionDtoResponse deleteOrganizacionById(Long id) {
        //Obtener organización usando el id pasado en la llamada
        Organizacion organizacion = organizacionRepository.findById(id)
                .orElseThrow(() -> new OrganizacionNotFoundException("Organización no encontrada con id: " + id));

        organizacionRepository.delete(organizacion);

        //TODO AJUSTAR DEPENDENCIAS DE BORRADO (por ejemplo, planes, miembros, tareas relacionadas)
        return OrganizacionMapper.toDtoResponse(organizacion);
    }

    public OrganizacionDtoResponse actualizarOrganizacion(OrganizacionDtoRequest dto, Long id) {
        //Obtener organización usando el id pasado en la llamada
        Organizacion organizacion = organizacionRepository.findById(id)
                .orElseThrow(() -> new OrganizacionNotFoundException("Organización no encontrada con id: " + id));

        OrganizacionMapper.updateEntityFromDtoRes(dto, organizacion);
        organizacionRepository.save(organizacion);
        return OrganizacionMapper.toDtoResponse(organizacion);
    }

    public List<OrganizacionDtoResponse> findOrganizacionesByMiembroId(Long id) {
        return null;
        //TODO
    }
}
