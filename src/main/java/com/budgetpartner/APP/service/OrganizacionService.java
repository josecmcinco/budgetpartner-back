package com.budgetpartner.APP.service;

import com.budgetpartner.APP.dto.organizacion.OrganizacionDtoPostRequest;
import com.budgetpartner.APP.dto.organizacion.OrganizacionDtoResponse;
import com.budgetpartner.APP.dto.organizacion.OrganizacionDtoUpdateRequest;
import com.budgetpartner.APP.entity.Organizacion;
import com.budgetpartner.APP.mapper.OrganizacionMapper;
import com.budgetpartner.APP.repository.OrganizacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.budgetpartner.APP.exceptions.NotFoundException;

import java.util.List;

@Service
public class OrganizacionService {

    @Autowired
    private OrganizacionRepository organizacionRepository;

    //ENDPOINTS

    public Organizacion postOrganizacion(OrganizacionDtoPostRequest organizacionDtoReq) {
        //TODO VARIABLES REPETIDAS (NOMBRE, CÓDIGO, ETC. según tu lógica de negocio)


        Organizacion organizacion = OrganizacionMapper.toEntity(organizacionDtoReq);
        organizacionRepository.save(organizacion);
        return organizacion;
    }

    public OrganizacionDtoResponse getOrganizacionByIdAndTransform(Long id) {
        //Obtener organización usando el id pasado en la llamada
        Organizacion organizacion = getOrganizacionById(id);
        OrganizacionDtoResponse dto = OrganizacionMapper.toDtoResponse(organizacion);
        return dto;
    }

    public Organizacion deleteOrganizacionById(Long id) {
        //Obtener organización usando el id pasado en la llamada
        Organizacion organizacion = organizacionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Organización no encontrada con id: " + id));

        organizacionRepository.delete(organizacion);

        //TODO AJUSTAR DEPENDENCIAS DE BORRADO (por ejemplo, planes, miembros, tareas relacionadas)
        return organizacion;
    }

    public OrganizacionDtoResponse patchOrganizacion(OrganizacionDtoUpdateRequest dto) {
        //Obtener organización usando el id pasado en la llamada
        Organizacion organizacion = organizacionRepository.findById(dto.getId())
                .orElseThrow(() -> new NotFoundException("Organización no encontrada con id: " + dto.getId()));

        OrganizacionMapper.updateEntityFromDtoRes(dto, organizacion);
        organizacionRepository.save(organizacion);
        return OrganizacionMapper.toDtoResponse(organizacion);
    }

    public List<Organizacion> findOrganizacionesByMiembroId(Long id) {
        return null;
        //TODO
    }

    //OTROS MÉTODOS
    public Organizacion getOrganizacionById(Long id) {
        //Obtener organización usando el id pasado en la llamada
        Organizacion organizacion = organizacionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Organización no encontrada con id: " + id));

        return organizacion;
    }
}
