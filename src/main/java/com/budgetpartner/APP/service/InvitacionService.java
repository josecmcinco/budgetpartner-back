package com.budgetpartner.APP.service;

import com.budgetpartner.APP.dto.TokenResponse;
import com.budgetpartner.APP.dto.miembro.MiembroDtoResponse;
import com.budgetpartner.APP.entity.Invitacion;
import com.budgetpartner.APP.entity.Miembro;
import com.budgetpartner.APP.entity.Organizacion;
import com.budgetpartner.APP.entity.Usuario;
import com.budgetpartner.APP.exceptions.NotFoundException;
import com.budgetpartner.APP.mapper.MiembroMapper;
import com.budgetpartner.APP.repository.InvitacionRepository;
import com.budgetpartner.APP.repository.MiembroRepository;
import com.budgetpartner.APP.repository.OrganizacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class InvitacionService {

    @Autowired
    private InvitacionRepository invitacionRepository;
    @Autowired
    private OrganizacionService organizacionService;
    @Autowired
    private OrganizacionRepository organizacionRepository;
    @Autowired
    private MiembroRepository miembroRepository;

    public List<MiembroDtoResponse> getMiembrosNoAdjuntosPorToken(String token) {

        Invitacion invitacion = invitacionRepository.findById(token)
                .orElseThrow(() -> new NotFoundException("Invitación no encontrada"));

        if (!invitacion.isActiva()){
            throw new NotFoundException("Invitación no encontrada");
        }

        List<Miembro> miembros = miembroRepository.obtenerMiembrosInactivosIdPorOrganizacionId(invitacion.getOrganizacion().getId());

        return MiembroMapper.toDtoResponseListMiembro(miembros);
    }

    public TokenResponse  obtenerToken(Long organizacionId) {

        Invitacion invitacion = invitacionRepository.obtenerInvitacionPorOrganizacionId(organizacionId).orElse(null);

        if(invitacion != null){
            // Retornar token nuevo
            return new TokenResponse(invitacion.getToken());
        }

            Organizacion organizacion = organizacionRepository.findById(organizacionId)
                    .orElseThrow(() -> new NotFoundException("Organización no encontrada con id :" + organizacionId));

        //Generar token nuevo
        String token = UUID.randomUUID().toString();

        invitacion = new Invitacion(token, organizacion);
        invitacionRepository.save(invitacion);

        // Retornar token nuevo
        return new TokenResponse(token);
    }
}
