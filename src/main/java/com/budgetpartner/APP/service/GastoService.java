package com.budgetpartner.APP.service;

import com.budgetpartner.APP.dto.request.GastoDtoRequest;
import com.budgetpartner.APP.dto.response.GastoDtoResponse;
import com.budgetpartner.APP.entity.Gasto;
import com.budgetpartner.APP.exceptions.NotFoundException;
import com.budgetpartner.APP.mapper.GastoMapper;
import com.budgetpartner.APP.repository.GastoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GastoService {

    @Autowired
    private GastoRepository gastoRepository;

    //ESTRUCTURA GENERAL DE LA LÓGICA DE LOS CONTROLADORES
    //Pasar de DtoRequest a Entity-> Insertar en DB->Pasar de Entity a DtoRequest->Return
    public Gasto postGasto(GastoDtoRequest gastoDtoReq) {
        //TODO VALIDAR CAMPOS REPETIDOS (DESCRIPCIÓN, MONTO, FECHA, ETC.)
        Gasto gasto = GastoMapper.toEntity(gastoDtoReq);
        gastoRepository.save(gasto);
        return gasto;
    }

    public Gasto getGastoById(Long id) {
        //Obtener gasto usando el id pasado en la llamada
        Gasto gasto = gastoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Gasto no encontrado con id: " + id));
        return gasto;

    }

    public Gasto deleteGastoById(Long id) {
        //Obtener gasto usando el id pasado en la llamada
        Gasto gasto = gastoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Gasto no encontrado con id: " + id));

        gastoRepository.delete(gasto);

        //TODO AJUSTAR DEPENDENCIAS DE BORRADO SI EXISTEN (referencias desde otras entidades)
        return gasto;
    }

    public Gasto patchGasto(GastoDtoRequest dto, Long id) {
        //Obtener gasto usando el id pasado en la llamada
        Gasto gasto = gastoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Gasto no encontrado con id: " + id));

        GastoMapper.updateEntityFromDtoRes(dto, gasto);
        gastoRepository.save(gasto);
        return gasto;
    }

}
