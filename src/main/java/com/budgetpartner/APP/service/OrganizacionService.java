package com.budgetpartner.APP.service;

import com.budgetpartner.APP.dto.organizacion.OrganizacionDtoPostRequest;
import com.budgetpartner.APP.dto.organizacion.OrganizacionDtoResponse;
import com.budgetpartner.APP.dto.organizacion.OrganizacionDtoUpdateRequest;
import com.budgetpartner.APP.entity.Miembro;
import com.budgetpartner.APP.entity.Organizacion;
import com.budgetpartner.APP.entity.Usuario;
import com.budgetpartner.APP.mapper.OrganizacionMapper;
import com.budgetpartner.APP.repository.MiembroRepository;
import com.budgetpartner.APP.repository.OrganizacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.budgetpartner.APP.exceptions.NotFoundException;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrganizacionService {

    @Autowired
    private OrganizacionRepository organizacionRepository;
    @Autowired
    private MiembroRepository miembroRepository;
    @Autowired
    private UsuarioService usuarioService;

    //ENDPOINTS

    //Llamada para Endpoint
    //Crea una Entidad usando el DTO recibido por el usuario
    public Organizacion postOrganizacion(OrganizacionDtoPostRequest organizacionDtoReq) {
        //TODO VARIABLES REPETIDAS (NOMBRE, CÓDIGO, ETC. según tu lógica de negocio)

        Organizacion organizacion = OrganizacionMapper.toEntity(organizacionDtoReq);
        organizacionRepository.save(organizacion);
        return organizacion;
    }

    //Llamada para Endpoint
    //Obtiene una Entidad usando el id recibido por el usuario
        /*DEVUELVE AL USUARIO:
        organizacion
            |-
            |-numeroPlanes : number
        numeroTareas : number
        actividadReciente : array de objetos
    */
    public OrganizacionDtoResponse getOrganizacionDtoById(Long id) {
        //Obtener organización usando el id pasado en la llamada
        Organizacion organizacion = organizacionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Miembro no encontrado con id: " + id));;
        OrganizacionDtoResponse dto = OrganizacionMapper.toDtoResponse(organizacion);
        return dto;
    }

    //Llamada para Endpoint
    //Elimina una Entidad usando el id recibido por el usuario
    public Organizacion deleteOrganizacionById(Long id) {
        //Obtener organización usando el id pasado en la llamada
        Organizacion organizacion = organizacionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Organización no encontrada con id: " + id));

        organizacionRepository.delete(organizacion);

        //TODO AJUSTAR DEPENDENCIAS DE BORRADO (por ejemplo, planes, miembros, tareas relacionadas)
        return organizacion;
    }

    //Llamada para Endpoint
    //Actualiza una Entidad usando el id recibido por el usuario
    public OrganizacionDtoResponse patchOrganizacion(OrganizacionDtoUpdateRequest dto) {
        //Obtener organización usando el id pasado en la llamada
        Organizacion organizacion = organizacionRepository.findById(dto.getId())
                .orElseThrow(() -> new NotFoundException("Organización no encontrada con id: " + dto.getId()));

        OrganizacionMapper.updateEntityFromDtoRes(dto, organizacion);
        organizacionRepository.save(organizacion);
        return OrganizacionMapper.toDtoResponse(organizacion);
    }

    //Llamada para Endpoint
    //Obtiene una lista de Entidades usando el id recibido por el usuario
    /*DEVUELVE:
            |-OrganizacionDto
                |-numeroMiembros
     */
    public List<OrganizacionDtoResponse> getOrganizacionesByUsuarioId() {

        Usuario usuario = usuarioService.devolverUsuarioAutenticado();
        //List<Miembro> miembrosDelUsuario = miembroRepository.obtenerMiembrosPorUsuarioId(usuario.getId());
        List<Organizacion> organizaciones = organizacionRepository.obtenerOrganizacionesPorUsuarioId(usuario.getId());

        List<OrganizacionDtoResponse> DtosOrganizaciones = OrganizacionMapper.toDtoResponseListOrganizacion(organizaciones);

        //Introducir número de mimebros de cada organización en el DTO correspondiente
        for (OrganizacionDtoResponse organizacionDto : DtosOrganizaciones) {
            int numeroMiembros = miembroRepository.contarMiembrosPorOrganizacionId(organizacionDto.getId());
            organizacionDto.setNumeroMiembros(numeroMiembros);
        }
        return  DtosOrganizaciones;
    }

    //OTROS MÉTODOS

}
