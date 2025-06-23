package com.budgetpartner.APP.service;

import com.budgetpartner.APP.dto.estimacion.EstimacionDtoPostRequest;
import com.budgetpartner.APP.dto.estimacion.EstimacionDtoResponse;
import com.budgetpartner.APP.dto.estimacion.EstimacionDtoUpdateRequest;
import com.budgetpartner.APP.entity.Estimacion;
import com.budgetpartner.APP.entity.Organizacion;
import com.budgetpartner.APP.exceptions.NotFoundException;
import com.budgetpartner.APP.repository.EstimacionRepository;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EstimacionService {

    @Autowired
    EstimacionRepository estimacionRepository;

    public void deleteEstimacionById(Long id) {

        Estimacion estimacion = estimacionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Organización no encontrada con id: " + id));

        //Borrado en cascada de organizaciones
        estimacionRepository.delete(estimacion);

    }

    public void patchEstimacion(EstimacionDtoUpdateRequest estimacionDtoUpReq, Long id) {
    }

    public EstimacionDtoResponse getEstimacionDtoById(Long id) {
        return null;
    }

    public void postEstimacion(EstimacionDtoPostRequest estimacionDtoReq) {
    }
}
