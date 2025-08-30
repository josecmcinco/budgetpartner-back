package com.budgetpartner.APP.service;

import com.budgetpartner.APP.dto.miembro.MiembroDtoPostRequest;
import com.budgetpartner.APP.dto.miembro.MiembroDtoResponse;
import com.budgetpartner.APP.dto.miembro.MiembroDtoUpdateRequest;
import com.budgetpartner.APP.entity.Miembro;
import com.budgetpartner.APP.entity.Organizacion;
import com.budgetpartner.APP.entity.Rol;
import com.budgetpartner.APP.entity.Usuario;
import com.budgetpartner.APP.exceptions.NotFoundException;
import com.budgetpartner.APP.mapper.MiembroMapper;
import com.budgetpartner.APP.repository.MiembroRepository;
import com.budgetpartner.APP.repository.OrganizacionRepository;
import com.budgetpartner.APP.repository.RolRepository;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * Servicio encargado de la gestión de miembros.
 * Permite crear, consultar, actualizar, eliminar (marcar inactivos)
 * y asociar miembros a usuarios.
 */
@Service
public class MiembroService {

    private final MiembroRepository miembroRepository;
    private final OrganizacionRepository organizacionRepository;
    private final RolRepository rolRepository;
    private final AutorizacionService autorizacionService;

    @Autowired
    public MiembroService(MiembroRepository miembroRepository,
                          OrganizacionRepository organizacionRepository,
                          RolRepository rolRepository,
                          AutorizacionService autorizacionService) {
        this.miembroRepository = miembroRepository;
        this.organizacionRepository = organizacionRepository;
        this.rolRepository = rolRepository;
        this.autorizacionService = autorizacionService;
    }

    /**
     * Crea un nuevo miembro en una organización con un rol determinado.
     *
     * @param dto DTO con los datos del miembro a crear
     * @return DTO del miembro creado
     * @throws NotFoundException si la organización o el rol no existen
     */
    public MiembroDtoResponse postMiembro(MiembroDtoPostRequest dto){

        Organizacion organizacion = organizacionRepository.findById(dto.getOrganizacionId())
                .orElseThrow(() -> new NotFoundException("Organización no encontrada con id: " + (dto.getOrganizacionId())));

        Rol rol = rolRepository.findById(dto.getRolId())
                .orElseThrow(() -> new NotFoundException("Rol no encontrada con id: " + (dto.getRolId())));

        //Crear miembro
        Miembro miembro = MiembroMapper.toEntity(dto, organizacion, rol);
        miembro = miembroRepository.save(miembro);

        return MiembroMapper.toDtoResponse(miembro);
    }

    /**
     * Obtiene un miembro por su ID.
     *
     * @param id ID del miembro
     * @return DTO del miembro
     * @throws NotFoundException si el miembro no existe
     */
    public MiembroDtoResponse getMiembroDtoById(Long id){
        //Obtener ususario usando el id pasado en la llamada
        Miembro miembro = miembroRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Miembro no encontrado con id: " + id));

        MiembroDtoResponse dto = MiembroMapper.toDtoResponse(miembro);
        return dto;
    }

    /**
     * Marca un miembro como inactivo en lugar de eliminarlo de la base de datos.
     *
     * @param id ID del miembro
     * @return entidad Miembro actualizada
     * @throws NotFoundException si el miembro no existe
     */
    @Transactional //Implica usar el muchos a muchos
    public Miembro deleteMiembroById(Long id){
        //Obtener ususario usando el id pasado en la llamada
        Miembro miembro = miembroRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Miembro no encontrado con id: " + id));

        miembro.setActivo(false);
        miembroRepository.save(miembro);

        return miembro;
    }

    /**
     * Actualiza los datos de un miembro existente.
     *
     * @param dto DTO con los datos a actualizar
     * @param id  ID del miembro
     * @return entidad Miembro actualizada
     * @throws NotFoundException si el miembro o el rol no existen
     */
    public Miembro patchMiembro(MiembroDtoUpdateRequest dto, Long id) {

        // Obtener miembro usando el id pasado en la llamada
        Miembro miembro = miembroRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Miembro no encontrado con id: " + id));

        Rol rol = null;
        if (dto.getRolId() != null) {
            rol = rolRepository.findById(dto.getRolId())
                    .orElseThrow(() -> new NotFoundException("Rol no encontrada con id: " + (dto.getRolId())));
        }

        MiembroMapper.updateEntityFromDtoRes(dto, miembro, rol);
        miembroRepository.save(miembro);

        return miembro;
    }

    /**
     * Asocia un miembro a un usuario autenticado.
     *
     * @param miembroId ID del miembro
     * @return DTO del miembro actualizado
     * @throws NotFoundException si el miembro no existe
     */
    public MiembroDtoResponse associateMiembro(Long miembroId) {
        //TODO SOLO si la variable usuario está vacía
        Usuario usuario = autorizacionService.devolverUsuarioAutenticado();
        Miembro miembro = miembroRepository.findById(miembroId)
                .orElseThrow(() -> new NotFoundException("Miembro no encontrado con id: " + miembroId));


        miembro.setUsuario(usuario);
        miembro.setAsociado(true);
        miembro.setFechaIngreso(LocalDateTime.now());

        miembroRepository.save(miembro);

        return MiembroMapper.toDtoResponse(miembro);
    }

    /**
     * Desasocia un miembro de un usuario autenticado.
     *
     * @param miembroId ID del miembro
     * @return DTO del miembro actualizado
     * @throws NotFoundException si el miembro no existe
     */
    public MiembroDtoResponse dissociateMember(Long miembroId) {
        Usuario usuario = autorizacionService.devolverUsuarioAutenticado();
        Miembro miembro = miembroRepository.findById(miembroId)
                .orElseThrow(() -> new NotFoundException("Miembro no encontrado con id: " + miembroId));


        miembro.setUsuario(null);
        miembro.setAsociado(false);
        miembro.setFechaIngreso(null);

        miembroRepository.save(miembro);

        return MiembroMapper.toDtoResponse(miembro);
    }

    /**
     * Obtiene un miembro asociado al usuario autenticado dentro de una organización.
     *
     * @param organizacionId ID de la organización
     * @return DTO del miembro
     * @throws NotFoundException si el miembro no existe
     */
    public MiembroDtoResponse getMiembroPorUsernameYOrganizacion(Long organizacionId){

        Usuario usuario = autorizacionService.devolverUsuarioAutenticado();

        Miembro miembro = miembroRepository.obtenerMiembroPorUsuarioYOrgId(usuario.getId(), organizacionId)
                .orElseThrow(() -> new NotFoundException("Miembro no encontrado con id: " + usuario.getId()));

        return  MiembroMapper.toDtoResponse(miembro);
    }

}
