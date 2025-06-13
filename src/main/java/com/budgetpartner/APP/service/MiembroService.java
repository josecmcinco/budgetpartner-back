package com.budgetpartner.APP.service;

import com.budgetpartner.APP.dto.miembro.MiembroDtoPostRequest;
import com.budgetpartner.APP.dto.miembro.MiembroDtoResponse;
import com.budgetpartner.APP.dto.miembro.MiembroDtoUpdateRequest;
import com.budgetpartner.APP.entity.Miembro;
import com.budgetpartner.APP.entity.Organizacion;
import com.budgetpartner.APP.exceptions.NotFoundException;
import com.budgetpartner.APP.mapper.MiembroMapper;
import com.budgetpartner.APP.repository.MiembroRepository;
import com.budgetpartner.APP.repository.OrganizacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MiembroService {

    @Autowired
    private MiembroRepository miembroRepository;
    @Autowired
    private OrganizacionRepository organizacionRepository;

    //ENDPOINTS

    //Llamada para Endpoint
    //Crea una Entidad usando el DTO recibido por el usuario
    public Miembro postMiembro(MiembroDtoPostRequest dto){

        Organizacion organizacion = organizacionRepository.findById(dto.getOrganizacionOrigenId())
                .orElseThrow(() -> new NotFoundException("Organización no encontrada con id: " + (dto.getOrganizacionOrigenId())));

        Miembro miembro = MiembroMapper.toEntity(dto, organizacion);
        miembroRepository.save(miembro);
        return miembro;
    }

    //Llamada para Endpoint
    //Obtiene una Entidad usando el id recibido por el usuario
        /*DEVUELVE AL USUARIO:
    */
    public MiembroDtoResponse getMiembroDtoById(Long id){
        //Obtener ususario usando el id pasado en la llamada
        Miembro miembro = miembroRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Miembro no encontrado con id: " + id));

        MiembroDtoResponse dto = MiembroMapper.toDtoResponse(miembro);
        return dto;
    }

    //Llamada para Endpoint
    //Elimina una Entidad usando el id recibido por el usuario
    public Miembro deleteMiembroById(Long id){
        //Obtener ususario usando el id pasado en la llamada
        Miembro miembro = miembroRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Miembro no encontrado con id: " + id));

        miembroRepository.delete(miembro);

        //TODO AJSUTAR DEPENDENCIAS DE BORRADO
        return miembro;
    }

    //Llamada para Endpoint
    //Modifica una Entidad usando el id recibido por el usuario
    public Miembro patchMiembro(MiembroDtoUpdateRequest dto) {

        // Obtener miembro usando el id pasado en la llamada
        Miembro miembro = miembroRepository.findById(dto.getId())
                .orElseThrow(() -> new NotFoundException("Miembro no encontrado con id: " + dto.getId()));

        MiembroMapper.updateEntityFromDtoRes(dto, miembro);
        miembroRepository.save(miembro);
        return miembro;
    }

    //OTROS MÉTODOS

}
