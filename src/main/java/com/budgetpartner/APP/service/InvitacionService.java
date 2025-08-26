package com.budgetpartner.APP.service;

import com.budgetpartner.APP.dto.TokenResponse;
import com.budgetpartner.APP.dto.miembro.MiembroDtoResponse;
import com.budgetpartner.APP.entity.Invitacion;
import com.budgetpartner.APP.entity.Miembro;
import com.budgetpartner.APP.entity.Organizacion;
import com.budgetpartner.APP.exceptions.NotFoundException;
import com.budgetpartner.APP.mapper.MiembroMapper;
import com.budgetpartner.APP.repository.InvitacionRepository;
import com.budgetpartner.APP.repository.MiembroRepository;
import com.budgetpartner.APP.repository.OrganizacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

/**
 * Servicio encargado de la gestión de invitaciones a organizaciones.
 * Permite obtener miembros no adjuntos y generar/recuperar tokens de invitación.
 */
@Service
public class InvitacionService {

    private final InvitacionRepository invitacionRepository;
    private final OrganizacionRepository organizacionRepository;
    private final MiembroRepository miembroRepository;

    @Autowired
    public InvitacionService(InvitacionRepository invitacionRepository,
                             OrganizacionRepository organizacionRepository,
                             MiembroRepository miembroRepository) {
        this.invitacionRepository = invitacionRepository;
        this.organizacionRepository = organizacionRepository;
        this.miembroRepository = miembroRepository;
    }

    /**
     * Obtiene los miembros inactivos de una organización asociados a un token de invitación.
     *
     * @param token token de invitación
     * @return lista de DTOs de miembros inactivos
     * @throws NotFoundException si la invitación no existe o está desactivada
     */
    public List<MiembroDtoResponse> getMiembrosNoAdjuntosPorToken(String token) {

        Invitacion invitacion = invitacionRepository.findById(token)
                .orElseThrow(() -> new NotFoundException("Invitación no encontrada"));

        if (!invitacion.isActiva()){
            throw new NotFoundException("Invitación no encontrada");
        }

        // Obtener miembros inactivos de la organización
        List<Miembro> miembros = miembroRepository
                .obtenerMiembrosInactivosIdPorOrganizacionId(invitacion.getOrganizacion().getId());

        return MiembroMapper.toDtoResponseListMiembro(miembros);
    }

    /**
     * Genera o recupera un token de invitación para una organización.
     *
     * @param organizacionId ID de la organización
     * @return DTO con el token generado o existente
     * @throws NotFoundException si la organización no existe
     */
    public TokenResponse  obtenerToken(Long organizacionId) {

        // Verificar si ya existe un token activo en la DB
        Invitacion invitacion = invitacionRepository.obtenerInvitacionPorOrganizacionId(organizacionId)
                .orElse(null); //TODO

        //Si hay un token en la DB, devolver token guardado
        if(invitacion != null && invitacion.isActiva()){
            return new TokenResponse(invitacion.getToken());
        }

        String token;
        do {
            token = UUID.randomUUID().toString();
        } while (invitacionRepository.findById(token).isPresent());

        // Guardar invitación en la DB
        Organizacion organizacion = organizacionRepository.findById(organizacionId)
                .orElseThrow(() -> new NotFoundException("Organización no encontrada con id: " + organizacionId));

        invitacion = new Invitacion(token, organizacion);
        invitacionRepository.save(invitacion);

        return new TokenResponse(token);
    }
}
