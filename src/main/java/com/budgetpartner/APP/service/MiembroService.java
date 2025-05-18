package com.budgetpartner.APP.service;

import com.budgetpartner.APP.dto.response.MiembroDtoResponse;
import com.budgetpartner.APP.dto.response.OrganizacionDtoResponse;
import com.budgetpartner.APP.entity.Miembro;
import com.budgetpartner.APP.mapper.MiembroMapper;
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


    public List<MiembroDtoResponse> findMiembroByUsuarioId(Long id) {
        List<Long> miembrosId = miembroRepository.findByusuarioOrigen_id(id);
        List<Miembro> miembros = new ArrayList<>();
        List<MiembroDtoResponse> listaMiembrosDtoResp;
        if (miembros.isEmpty()) {
            return null;
        } else {
            listaMiembrosDtoResp = new ArrayList<>();
            for (Miembro miembro : miembros) {
                MiembroDtoResponse miembroDtoResp = MiembroMapper.toDto(miembro);
                listaMiembrosDtoResp.add(miembroDtoResp);
            }
            return listaMiembrosDtoResp;
        }
    }

}
