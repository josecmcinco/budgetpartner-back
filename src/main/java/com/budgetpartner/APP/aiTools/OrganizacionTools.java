package com.budgetpartner.APP.aiTools;


import com.budgetpartner.APP.dto.miembro.MiembroDtoResponse;
import com.budgetpartner.APP.dto.organizacion.OrganizacionDtoPostRequest;
import com.budgetpartner.APP.dto.organizacion.OrganizacionDtoResponse;
import com.budgetpartner.APP.entity.Miembro;
import com.budgetpartner.APP.entity.Organizacion;
import com.budgetpartner.APP.entity.Rol;
import com.budgetpartner.APP.entity.Usuario;
import com.budgetpartner.APP.enums.NombreRol;
import com.budgetpartner.APP.exceptions.NotFoundException;
import com.budgetpartner.APP.mapper.MiembroMapper;
import com.budgetpartner.APP.mapper.OrganizacionMapper;
import com.budgetpartner.APP.repository.MiembroRepository;
import com.budgetpartner.APP.repository.OrganizacionRepository;
import com.budgetpartner.APP.repository.RolRepository;
import com.budgetpartner.APP.repository.UsuarioRepository;
import com.budgetpartner.APP.service.MiembroService;
import com.budgetpartner.APP.service.OrganizacionService;
import com.budgetpartner.APP.service.UsuarioService;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.*;


@Component
public class OrganizacionTools {

    @Autowired
    private MiembroRepository miembroRepository;
    @Autowired
    private MiembroService miembroService;
    @Autowired
    private OrganizacionRepository organizacionRepository;
    @Autowired
    private OrganizacionService organizacionService;
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private RolRepository rolRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Tool(name = "saludoOrganizacion", description = "Saluda desde organizacion")
    public String saludoOrganizacion(@ToolParam(description = "Nombre") String nombre) {
        return "Hola desde OrganizacionTools, " + nombre;
    }

    @Tool(name = "crearOrganizacionDesdeTexto", description = "Crea una organización.")
    public String crearOrganizacionDesdeTexto(
            @ToolParam(description = "Nombre de la organización") String nombreOrganizacion,
            @ToolParam(description = "Descripción de la organización") String _descripcionOrganizacion_,
            @ToolParam(description = "Nick del miembro creador") String _nickMiembroCreador_
    ) {
        try {
            String nombre = nombreOrganizacion;
            String descripcion = _descripcionOrganizacion_;
            String nick = _nickMiembroCreador_;

            //Autenticar el miembro
            Usuario usuario = usuarioRepository.findById(2L).orElseThrow(() -> new NotFoundException("ARREGLAR  no encontrado con id"));

            //Confirmar valor de los elementos
            if (descripcion == null || descripcion.isEmpty()){
                descripcion = "";
            }
            if (nick == null || nick.isEmpty()){
                nick = usuario.getEmail().split("@")[0];;
            }

            OrganizacionDtoPostRequest dto = new OrganizacionDtoPostRequest(nombre, descripcion, nick);

            // Guardar la organización
            Organizacion organizacion = OrganizacionMapper.toEntity(dto);
            organizacion = organizacionRepository.save(organizacion);

            // Rol por defecto para creador
            Rol rol = rolRepository.obtenerRolPorNombre(NombreRol.ROLE_ADMIN)
                    .orElseThrow(() -> new NotFoundException("ERROR INTERNO: Rol ROLE_ADMIN no encontrado"));

            // Crear miembro
            Miembro miembro = new Miembro(organizacion, rol, nick);
            miembro.setUsuario(usuario);
            miembro.setAsociado(true);
            miembro.setFechaIngreso(LocalDateTime.now());
            miembro = miembroRepository.save(miembro);

            // Mapear DTOs de respuesta
            MiembroDtoResponse miembroDtoRes = MiembroMapper.toDtoResponse(miembro);
            OrganizacionDtoResponse organizacionDtoResp = OrganizacionMapper.toDtoResponse(organizacion);
            organizacionDtoResp.setMiembros(List.of(miembroDtoRes));
            organizacionDtoResp.setMiembroCreador(miembro.getId());

            return "Organización creada correctamente";

        } catch (Exception e) {
            return "Error al crear la organización: " + e.getMessage();
        }
    }


    @Tool(name = "obtenerOrganizacionPorId", description = "Obtiene todos los miembros de una organización.")
    public Object obtenerOrganizacionPorId(@ToolParam(description = "Organization id") Long id) {
        try {
            return organizacionRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("Organizacion no encontrada con id: " + id));
        } catch (Exception e) {
            return "Error al obtener miembros: " + e.getMessage();}
    }

}
