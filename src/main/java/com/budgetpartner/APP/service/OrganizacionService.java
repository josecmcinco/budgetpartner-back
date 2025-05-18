package com.budgetpartner.APP.service;

import com.budgetpartner.APP.dto.response.OrganizacionDtoResponse;
import com.budgetpartner.APP.mapper.OrganizacionMapper;
import com.budgetpartner.APP.repository.OrganizacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrganizacionService {

    @Autowired
    private OrganizacionRepository organizacionRepository;

    public List<OrganizacionDtoResponse> findOrganizacionesByMiembroId(Long id) {
        return null;
        //TODO
    }
}
