package com.budgetpartner.APP.mcp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class ToolRegistrar implements CommandLineRunner {
    @Autowired
    private ToolRegistry toolRegistry;

    @Autowired
    private EstimacionTools estimacionTools;

    @Autowired
    private GastoTools gastoTools;

    @Autowired
    private MiembroTools miembroTools;

    @Autowired
    private OrganizacionTools organizacionTools;

    @Autowired
    private PlanTools planTools;

    @Autowired
    private TareaTools tareaTools;

    @Override
    public void run(String... args) throws Exception {
        toolRegistry.registerTools(estimacionTools);
        toolRegistry.registerTools(gastoTools);
        toolRegistry.registerTools(miembroTools);
        toolRegistry.registerTools(organizacionTools);
        toolRegistry.registerTools(planTools);
        toolRegistry.registerTools(tareaTools);

        System.out.println("Herramientas registradas: " + toolRegistry.getRegisteredTools());
    }
}
