package com.budgetpartner.APP.aiTools;

import com.budgetpartner.APP.entity.Miembro;
import com.budgetpartner.APP.mapper.MiembroMapper;
import com.budgetpartner.APP.repository.MiembroRepository;
import com.budgetpartner.APP.repository.OrganizacionRepository;
import com.budgetpartner.APP.repository.RolRepository;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MiembroTools {

    @Autowired
    private MiembroRepository miembroRepository;
    @Autowired
    private OrganizacionRepository organizacionRepository;
    @Autowired
    private RolRepository rolRepository;

    @Tool(name = "saludoMiembro", description = "Saluda desde estimacion")
    public String saludoMiembro(@ToolParam(description = "Nombre") String nombre) {
        return "Hola desde MiembroTools, " + nombre;
    }

    /*
    @Tool(name = "crearMiembroDesdeTexto", description = "Crea un miembro para una organización.")
    public String crearMiembroDesdeTexto(@ToolParam(description = "miembro") MiembroLlmCompletionDto dto) {
        try {
            Organizacion organizacion = organizacionRepository.findById(dto.getOrganizacionId()).orElse(null);
            Rol rol = rolRepository.findById(dto.getRolId()).orElse(null);

            Miembro miembro = new Miembro(organizacion, rol, dto.getNick() ); // Asumiendo null para usuario
            miembroRepository.save(miembro);
            return "Miembro creado correctamente con nick: " + dto.getNick();
        } catch (Exception e) {
            return "Error al crear miembro: " + e.getMessage();
        }
    }*/

    @Tool(name = "obtenerMiembroPorId", description = "Obtiene un miembro dado su id.")
    public Object obtenerMiembroPorId(@ToolParam(description = "Organization id") Long id) {
        try {

            //Obtener elemento de la DB
            Miembro miembro = miembroRepository.findById(id).orElse(null);

            return MiembroMapper.toDtoResponse(miembro);


        } catch (Exception e) {
            return "Error al obtener miembros: " + e.getMessage();}
    }

    @Tool(name = "obtenerMiembrosPorOrganizacionId", description = "Obtiene todos los miembros de una organización.")
    public Object obtenerMiembrosPorOrganizacionId(@ToolParam(description = "Organization id") Long id) {
        try {
            List<Miembro> listaMiembros = miembroRepository.obtenerMiembrosPorOrganizacionId(id);

            if (listaMiembros == null  ||  listaMiembros.isEmpty()) {
                return "No se encontró el miembro de id " + id;}


            //Transformar a DTO para la recepción del usuario
            return MiembroMapper.toDtoResponseListMiembro(listaMiembros);
        } catch (Exception e) {
            return "Error al obtener miembros: " + e.getMessage();}
    }



}