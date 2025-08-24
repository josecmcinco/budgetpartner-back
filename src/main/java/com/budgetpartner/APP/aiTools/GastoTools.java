package com.budgetpartner.APP.aiTools;

import com.budgetpartner.APP.entity.*;
import com.budgetpartner.APP.repository.GastoRepository;
import com.budgetpartner.APP.repository.MiembroRepository;
import com.budgetpartner.APP.repository.PlanRepository;
import com.budgetpartner.APP.repository.TareaRepository;
import com.budgetpartner.APP.service.GastoService;
import com.budgetpartner.APP.service.OrganizacionService;
import com.budgetpartner.APP.service.UsuarioService;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GastoTools {

    @Autowired
    private GastoService gastoService;
    @Autowired
    private OrganizacionService organizacionService;
    @Autowired
    private GastoRepository gastoRepository;
    @Autowired
    private PlanRepository planRepository;
    @Autowired
    private TareaRepository tareaRepository;
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private MiembroRepository miembroRepository;

/*
    @Tool(name = "crearGastoDesdeTexto", description = "Crea un gasto con la información proporcionada en lenguaje natural.")
    public String crearGastoDesdeTexto(@ToolParam(description = "gasto") GastoLlmCompletionDto gastoLlm) {
        try {
            //GastoDtoPostRequest request = gastoMapper.mapToPostRequest(gasto, usuario);

            Plan plan = planRepository.findById(1L).orElse(null);
            Tarea tarea = tareaRepository.findById(1L).orElse(null);
            Miembro miembro = miembroRepository.findById(1L).orElse(null);
            Miembro miembro2 = miembroRepository.findById(3L).orElse(null);
            List<Miembro> miembroList = Arrays.asList(miembro, miembro2);

            Gasto gasto = new Gasto(tarea, plan, gastoLlm.getCantidad(), gastoLlm.getNombre(), miembro, "");
            gasto.setMiembrosEndeudados(miembroList);
            gastoRepository.save(gasto);
            return "Gasto creado correctamente: " + gasto.getNombre() + " por " + gasto.getCantidad() + "€.";
        } catch (Exception e) {
            return "No se pudo crear el gasto: " + e.getMessage();
        }
    }*/



    @Tool(name = "consultarGastosMes")
    public List<Gasto> consultarGastosMes(@ToolParam(description = "organizacion") String nombreOrg,
                                          @ToolParam(description = "mes") String mes,
                                          @ToolParam(description = "anio") String anio)
                                          //@ToolContext Usuario usuario)
    {
        //if (!organizacionService.usuarioPuedeAcceder(nombreOrg, usuario)) {
        //    throw new AccessDeniedException("Sin permisos");
        //}
        return gastoRepository.obtenerGastosPorPlanId(1L);
    }

    /*
    @Tool(name = "crearGasto")
    public GastoDto crearGasto(@ToolParam("datos") GastoDto gastoDto,
                               @ToolContext Usuario usuario) {
        // Validaciones y lógica
        return gastoService.crearGasto(gastoDto, usuario);
    }*/

    // Otras funciones relacionadas con gastos...
}
