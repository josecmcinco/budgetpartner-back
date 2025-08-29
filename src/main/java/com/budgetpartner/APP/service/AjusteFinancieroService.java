package com.budgetpartner.APP.service;

import com.budgetpartner.APP.dto.ajusteFinanciero.AjusteFinancieroDtoPostRequest;
import com.budgetpartner.APP.dto.ajusteFinanciero.AjusteFinancieroDtoResponse;
import com.budgetpartner.APP.entity.AjusteFinanciero;
import com.budgetpartner.APP.entity.Miembro;
import com.budgetpartner.APP.entity.Organizacion;
import com.budgetpartner.APP.entity.Usuario;
import com.budgetpartner.APP.exceptions.BadRequestException;
import com.budgetpartner.APP.exceptions.NotFoundException;
import com.budgetpartner.APP.mapper.AjusteFinancieroMapper;
import com.budgetpartner.APP.repository.AjusteFinancieroRepository;
import com.budgetpartner.APP.repository.MiembroRepository;
import com.budgetpartner.APP.repository.OrganizacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AjusteFinancieroService {

    private final AjusteFinancieroRepository ajusteRepository;
    private final AutorizacionService autorizacionService;
    private final OrganizacionRepository organizacionRepository;
    private final MiembroRepository miembroRepository;

    @Autowired
    public AjusteFinancieroService(AjusteFinancieroRepository ajusteRepository,
                                   AutorizacionService autorizacionService,
                                   OrganizacionRepository organizacionRepository,
                                   MiembroRepository miembroRepository) {
        this.ajusteRepository = ajusteRepository;
        this.autorizacionService = autorizacionService;
        this.organizacionRepository = organizacionRepository;
        this.miembroRepository = miembroRepository;
    }

    public AjusteFinancieroDtoResponse postAjusteFinanciero(AjusteFinancieroDtoPostRequest dto, Long organizacionId){

        // Validar usuario autenticado
        //Usuario usuario = autorizacionService.devolverUsuarioAutenticado();

        // TODO: validar que el usuario pertenece a la organización

        // Recuperar organización
        Organizacion organizacion = organizacionRepository.findById(organizacionId)
                .orElseThrow(() -> new NotFoundException("Organización no encontrada con id: " + organizacionId));

        // Recuperar pagador
        Miembro pagador = miembroRepository.findById(dto.getPagadorId())
                .orElseThrow(() -> new NotFoundException("Pagador no encontrado con id: " + dto.getPagadorId()));

        // Recuperar beneficiario
        Miembro beneficiario = miembroRepository.findById(dto.getBeneficiarioId())
                .orElseThrow(() -> new NotFoundException("Beneficiario no encontrado con id: " + dto.getBeneficiarioId()));

        // Validación extra: que pagador y beneficiario no sean el mismo
        if (pagador.getId().equals(beneficiario.getId())) {
            throw new BadRequestException("El pagador y beneficiario no pueden ser el mismo miembro.");
        }

        // Mapear DTO a entidad y guardar
        AjusteFinanciero ajuste = AjusteFinancieroMapper.toEntity(dto, organizacion, pagador, beneficiario);
        ajuste = ajusteRepository.save(ajuste);

        return AjusteFinancieroMapper.toDtoResponse(ajuste);
    }

}
