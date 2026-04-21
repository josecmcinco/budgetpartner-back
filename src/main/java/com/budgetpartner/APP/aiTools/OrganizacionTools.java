package com.budgetpartner.APP.aiTools;


import com.budgetpartner.APP.dto.organizacion.OrganizacionDtoPostRequest;
import com.budgetpartner.APP.enums.MonedasDisponibles;
import com.budgetpartner.APP.service.AutorizacionService;
import com.budgetpartner.APP.service.OrganizacionService;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;



@Component
public class OrganizacionTools {

    @Autowired
    private OrganizacionService organizacionService;
    @Autowired
    private AutorizacionService autorizacionService;

    @Tool(name = "saludoOrganizacion", description = "Saluda desde organizacion")
    public String saludoOrganizacion(@ToolParam(description = "Nombre") String nombre) {
        return "Hola desde OrganizacionTools, " + nombre;
    }

    @Tool(name = "crearOrganizacionDesdeTexto", description = "Crea una organización.")
    public String crearOrganizacionDesdeTexto(
            @ToolParam(description = "Nombre de la organización") String nombreOrganizacion,
            @ToolParam(description = "Descripción de la organización") String descripcionOrganizacion,
            @ToolParam(description = "Nick del miembro creador") String nickMiembroCreador
    ) {
        try {
            String descripcion;
            String nick;

            if (descripcionOrganizacion == null || descripcionOrganizacion.isEmpty()) {
                descripcion = "";
            }
            else {descripcion = descripcionOrganizacion;}



            if (nickMiembroCreador == null || nickMiembroCreador.isEmpty()) {
                nick = autorizacionService.devolverUsuarioAutenticado()
                        .getEmail()
                        .split("@")[0];
            }
            else {nick = nickMiembroCreador;}

            OrganizacionDtoPostRequest dto = new OrganizacionDtoPostRequest(nombreOrganizacion, descripcion, nick, MonedasDisponibles.EUR);
            organizacionService.postOrganizacion(dto);
            return "Organización creada correctamente";
        } catch (Exception e) {
            return "Error al crear la organización: " + e.getMessage();
        }
    }

    @Tool(name = "obtenerOrganizacionPorId", description = "Obtiene la información completa de una organización: miembros, planes y gastos.")
    public Object obtenerOrganizacionPorId(@ToolParam(description = "Id de la organización") Long id) {
        try {
            return organizacionService.getOrganizacionDtoById(id);
        } catch (Exception e) {
            return "Error al obtener la organización: " + e.getMessage();
        }
    }
}
