package com.budgetpartner.APP.aiTools;

import com.budgetpartner.APP.dto.miembro.MiembroDtoPostRequest;
import com.budgetpartner.APP.repository.RolRepository;
import com.budgetpartner.APP.service.MiembroService;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class MiembroTools {

    @Autowired
    private MiembroService miembroService;
    @Autowired
    private RolRepository rolRepository;

    @Tool(name = "saludoMiembro", description = "Saluda desde miembro")
    public String saludoMiembro(@ToolParam(description = "Nombre") String nombre) {
        return "Hola desde MiembroTools, " + nombre;
    }

    @Tool(name = "crearMiembroDesdeTexto", description = "Crea un miembro en una organización.")
    public String crearMiembroDesdeTexto(
            @ToolParam(description = "Id de la organización") Long organizacionId,
            @ToolParam(description = "Id del rol (opcional, por defecto ROLE_MEMBER)") Long _rolId,
            @ToolParam(description = "Nick del miembro") String nick
    ) {
        try {
            Long rolId;
            rolId = Objects.requireNonNullElse(_rolId, 3L);

            MiembroDtoPostRequest dto = new MiembroDtoPostRequest(organizacionId, rolId, nick);
            Long id = miembroService.postMiembro(dto).getId();
            return "Miembro creado correctamente con nick: " + nick + ". ID: " + id;
        } catch (Exception e) {
            return "Error al crear miembro: " + e.getMessage();
        }
    }

    @Tool(name = "obtenerMiembroPorId", description = "Obtiene un miembro dado su id.")
    public Object obtenerMiembroPorId(@ToolParam(description = "Id del miembro") Long id) {
        try {
            return miembroService.getMiembroDtoById(id);
        } catch (Exception e) {
            return "Error al obtener el miembro: " + e.getMessage();
        }
    }
}
