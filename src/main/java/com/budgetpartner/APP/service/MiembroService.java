package com.budgetpartner.APP.service;

import com.budgetpartner.APP.dto.request.MiembroDtoRequest;
import com.budgetpartner.APP.dto.request.UsuarioDtoRequest;
import com.budgetpartner.APP.dto.response.MiembroDtoResponse;
import com.budgetpartner.APP.dto.response.OrganizacionDtoResponse;
import com.budgetpartner.APP.dto.response.UsuarioDtoResponse;
import com.budgetpartner.APP.entity.Miembro;
import com.budgetpartner.APP.entity.Usuario;
import com.budgetpartner.APP.exceptions.AppExceptions.MiembroNotFoundException;
import com.budgetpartner.APP.exceptions.AppExceptions.UsuarioNotFoundException;
import com.budgetpartner.APP.mapper.MiembroMapper;
import com.budgetpartner.APP.mapper.UsuarioMapper;
import com.budgetpartner.APP.repository.MiembroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class MiembroService {

    @Autowired
    private MiembroRepository miembroRepository;

    public MiembroDtoResponse postMiembro(MiembroDtoRequest MiembroDtoReq){

        Miembro miembro = MiembroMapper.toEntity(MiembroDtoReq);
        miembroRepository.save(miembro);
        return MiembroMapper.toDtoResponse(miembro);
    }

    public MiembroDtoResponse getMiembroById(Long id){
        //Obtener ususario usando el id pasado en la llamada
        Miembro miembro = miembroRepository.findById(id)
                .orElseThrow(() -> new MiembroNotFoundException("Miembro no encontrado con id: " + id));

        return MiembroMapper.toDtoResponse(miembro);
    }

    public MiembroDtoResponse deleteMiembroById(Long id){
        //Obtener ususario usando el id pasado en la llamada
        Miembro miembro = miembroRepository.findById(id)
                .orElseThrow(() -> new MiembroNotFoundException("Miembro no encontrado con id: " + id));

        miembroRepository.delete(miembro);

        //TODO AJSUTAR DEPENDENCIAS DE BORRADO
        return MiembroMapper.toDtoResponse(miembro);
    }

    public MiembroDtoResponse actualizarMiembro(MiembroDtoRequest dto, Long id) {

        // Obtener miembro usando el id pasado en la llamada
        Miembro miembro = miembroRepository.findById(id)
                .orElseThrow(() -> new MiembroNotFoundException("Miembro no encontrado con id: " + id));

        MiembroMapper.updateEntityFromDtoRes(dto, miembro);
        miembroRepository.save(miembro);
        return MiembroMapper.toDtoResponse(miembro);
    }


    public List<MiembroDtoResponse> findMiembrosByUsuarioId(Long id) {
        List<Miembro> miembros = miembroRepository.findByusuarioOrigenId(id);
        List<MiembroDtoResponse> listaMiembrosDtoResp;
        if (miembros.isEmpty()) {
            return null;
        } else {
            listaMiembrosDtoResp = new ArrayList<>();
            for (Miembro miembro : miembros) {
                MiembroDtoResponse miembroDtoResp = MiembroMapper.toDtoResponse(miembro);
                listaMiembrosDtoResp.add(miembroDtoResp);
            }
            return listaMiembrosDtoResp;
        }
    }

}
