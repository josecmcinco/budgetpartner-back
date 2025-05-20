package com.budgetpartner.APP.service;

import com.budgetpartner.APP.dto.request.MiembroDtoRequest;
import com.budgetpartner.APP.dto.response.MiembroDtoResponse;
import com.budgetpartner.APP.entity.Miembro;
import com.budgetpartner.APP.exceptions.NotFoundException;
import com.budgetpartner.APP.mapper.MiembroMapper;
import com.budgetpartner.APP.repository.MiembroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MiembroService {

    @Autowired
    private MiembroRepository miembroRepository;

    public Miembro postMiembro(MiembroDtoRequest MiembroDtoReq){

        Miembro miembro = MiembroMapper.toEntity(MiembroDtoReq);
        miembroRepository.save(miembro);
        return miembro;
    }

    public Miembro getMiembroById(Long id){
        //Obtener ususario usando el id pasado en la llamada
        Miembro miembro = miembroRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Miembro no encontrado con id: " + id));

        return miembro;
    }

    public Miembro deleteMiembroById(Long id){
        //Obtener ususario usando el id pasado en la llamada
        Miembro miembro = miembroRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Miembro no encontrado con id: " + id));

        miembroRepository.delete(miembro);

        //TODO AJSUTAR DEPENDENCIAS DE BORRADO
        return miembro;
    }

    public Miembro actualizarMiembro(MiembroDtoRequest dto, Long id) {

        // Obtener miembro usando el id pasado en la llamada
        Miembro miembro = miembroRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Miembro no encontrado con id: " + id));

        MiembroMapper.updateEntityFromDtoRes(dto, miembro);
        miembroRepository.save(miembro);
        return miembro;
    }


    public List<Miembro> findMiembrosByUsuarioId(Long id) {
        List<Miembro> miembros = miembroRepository.findByusuarioOrigenId(id);
        return miembros;
    }

}
