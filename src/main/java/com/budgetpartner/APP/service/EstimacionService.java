package com.budgetpartner.APP.service;

import com.budgetpartner.APP.dto.estimacion.EstimacionDtoPostRequest;
import com.budgetpartner.APP.dto.estimacion.EstimacionDtoResponse;
import com.budgetpartner.APP.dto.estimacion.EstimacionDtoUpdateRequest;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;

@Service
public class EstimacionService {
    public void deleteEstimacionById(Long id) {
    }

    public void patchEstimacion(EstimacionDtoUpdateRequest estimacionDtoUpReq, Long id) {
    }

    public EstimacionDtoResponse getEstimacionDtoById(Long id) {
        return null;
    }

    public void postEstimacion(EstimacionDtoPostRequest estimacionDtoReq) {
    }
}
