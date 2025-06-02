package com.budgetpartner.APP.service;

import com.budgetpartner.APP.dto.gasto.GastoDtoUpdateRequest;
import com.budgetpartner.APP.dto.miembro.MiembroDtoPostRequest;
import com.budgetpartner.APP.dto.miembro.MiembroDtoResponse;
import com.budgetpartner.APP.dto.miembro.MiembroDtoUpdateRequest;
import com.budgetpartner.APP.entity.Gasto;
import com.budgetpartner.APP.entity.Miembro;
import com.budgetpartner.APP.entity.Organizacion;
import com.budgetpartner.APP.exceptions.NotFoundException;
import com.budgetpartner.APP.mapper.GastoMapper;
import com.budgetpartner.APP.mapper.MiembroMapper;
import com.budgetpartner.APP.repository.MiembroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MiembroService {

    @Autowired
    private MiembroRepository miembroRepository;
    @Autowired
    private OrganizacionService organizacionService;

    //ENDPOINTS

    public Miembro postMiembro(MiembroDtoPostRequest dto){

        Organizacion organizacion = organizacionService.getOrganizacionById(dto.getOrganizacionOrigenId());

        Miembro miembro = MiembroMapper.toEntity(dto, organizacion);
        miembroRepository.save(miembro);
        return miembro;
    }

    public MiembroDtoResponse getMiembroByIdAndTransform(Long id){
        //Obtener ususario usando el id pasado en la llamada
        Miembro miembro = getMiembroById(id);
        MiembroDtoResponse dto = MiembroMapper.toDtoResponse(miembro);
        return dto;
    }

    public Miembro deleteMiembroById(Long id){
        //Obtener ususario usando el id pasado en la llamada
        Miembro miembro = miembroRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Miembro no encontrado con id: " + id));

        miembroRepository.delete(miembro);

        //TODO AJSUTAR DEPENDENCIAS DE BORRADO
        return miembro;
    }

    public Miembro patchMiembro(MiembroDtoUpdateRequest dto) {

        // Obtener miembro usando el id pasado en la llamada
        Miembro miembro = miembroRepository.findById(dto.getId())
                .orElseThrow(() -> new NotFoundException("Miembro no encontrado con id: " + dto.getId()));

        MiembroMapper.updateEntityFromDtoRes(dto, miembro);
        miembroRepository.save(miembro);
        return miembro;
    }


    public List<Miembro> findMiembrosByUsuarioId(Long id) {
        List<Miembro> miembros = miembroRepository.findByusuarioOrigenId(id);
        return miembros;
    }


    //OTROS MÉTODOS
    public Miembro getMiembroById(Long id){
        //Obtener ususario usando el id pasado en la llamada
        Miembro miembro = miembroRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Miembro no encontrado con id: " + id));

        return miembro;
    }

}
