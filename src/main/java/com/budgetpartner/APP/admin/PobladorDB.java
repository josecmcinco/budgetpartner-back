package com.budgetpartner.APP.admin;

import com.budgetpartner.APP.entity.*;
import com.budgetpartner.APP.enums.ModoPlan;
import com.budgetpartner.APP.enums.MonedasDisponibles;
import com.budgetpartner.APP.enums.NombreRol;
import com.budgetpartner.APP.enums.TipoEstimacion;
import com.budgetpartner.APP.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class PobladorDB {

    @Autowired
    private  UsuarioRepository usuarioRepository;

    @Autowired
    private  RolRepository rolRepository;

    @Autowired
    private  OrganizacionRepository organizacionRepository;

    @Autowired
    private  MiembroRepository miembroRepository;

    @Autowired
    private  GastoRepository gastoRepository;

    @Autowired
    private  TareaRepository tareaRepository;

    @Autowired
    private  PlanRepository planRepository;

    @Autowired
    private JdbcTemplate jdbc;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EstimacionRepository estimacionRepository;

    @Autowired
    private RepartoGastoRepository repartoGastoRepository;

    @Autowired
    private  RepartoTareaRepository repartoTareaRepository;

    //Guardar mensajes de control
    private static final Logger logger = LoggerFactory.getLogger(PobladorDB.class);


    @Transactional
    public void borrarTodo(){
        //Empezar con las tablas que representan las relaciones muchos a muchos
        repartoTareaRepository.deleteAll();
        repartoGastoRepository.deleteAll();

        // Borrar entidades dependientes después
        estimacionRepository.deleteAll();
        miembroRepository.deleteAll();
        gastoRepository.deleteAll();
        tareaRepository.deleteAll();


        // Luego las entidades principales
        usuarioRepository.deleteAll();
        planRepository.deleteAll();

        rolRepository.deleteAll();
        organizacionRepository.deleteAll();


        logger.info("Base de datos limpiada con éxito.");

        //reiniciar Ids
        resetIds();

    }

    public void resetIds(){
        jdbc.execute("ALTER SEQUENCE usuario_seq RESTART WITH 1");
        jdbc.execute("ALTER SEQUENCE miembro_seq RESTART WITH 1");
        jdbc.execute("ALTER SEQUENCE organizacion_seq RESTART WITH 1");
        jdbc.execute("ALTER SEQUENCE plan_seq RESTART WITH 1");
        jdbc.execute("ALTER SEQUENCE rol_seq RESTART WITH 1");
        jdbc.execute("ALTER SEQUENCE tarea_seq RESTART WITH 1");
        jdbc.execute("ALTER SEQUENCE gasto_seq RESTART WITH 1");
        jdbc.execute("ALTER SEQUENCE estimacion_seq RESTART WITH 1");

        logger.info("Identificadores reseteados con éxito.");
    }

    @Transactional
    public void poblarTodo(){

        //PARA EL CORRECTO POBLADO DE LA DB ES NECESARIO CREAR
        //LOS ELEMENTOS EN UN ORDEN CONCRETO

        //Paso 1 del poblado
        List<Usuario> usuarios = poblarUsuarios();
        List<Organizacion> organizaciones = poblarOrganizaciones();
        List<Rol> roles = poblarRoles();

        //Paso 2
        List<Miembro> miembros = poblarMiembros(usuarios, organizaciones, roles);
        List<Plan> planes =poblarPlanes(organizaciones);

        //Paso 3
        List<Tarea> tareas = poblarTareas(planes, miembros);

        //Paso 4
        List<Gasto> gastos = poblarGastos(tareas, planes, miembros);

        //Paso 5
        List<Estimacion> estimaciones = poblarEstimaciones(planes, tareas, miembros, gastos);

        logger.info("Base de datos limpiada con éxito.");
    }

    public  List<Estimacion> poblarEstimaciones(List<Plan> planes, List<Tarea> tareas, List<Miembro> miembros, List<Gasto> gastos){

        //Estimacion1 estima a gasto1
        //Estimacion3 estima a gasto11
        Gasto gasto1 = gastos.get(0);
        Gasto gasto11 = gastos.get(10);

        //Estimacion1 tiene tarea = 2
        //Estimacion1 tiene tarea = 4
        Tarea tarea2 = tareas.get(1);
        Tarea tarea4 = tareas.get(3);

        //Tarea2 tiene plan = 1 por lo que: Estimacion1 tiene plan = 2
        //Tarea4 tiene plan = 1 por lo que: Estimacion1 tiene plan = 2
        Plan plan2 = planes.get(1);
        Plan plan3 = planes.get(2);

        //Miembros de organizacion 1 (y plan1)
        Miembro miembro1= miembros.get(0);
        Miembro miembro2= miembros.get(1);

        //Miembros de organizacion 1 (y plan3)
        Miembro miembro7= miembros.get(6);

        //Miembros de organizacion 1 y 2
        Miembro miembro3= miembros.get(1); //Tambien es organizacion1

        List<Estimacion> estimaciones = Arrays.asList(

                new Estimacion(plan2, tarea2, miembro1, 24.52, TipoEstimacion.ESTIMACION_TAREA, MonedasDisponibles.EUR, "Estimacion de tipo Tarea con pagador", miembro2, gasto1),

                new Estimacion(plan2, tarea4, miembro3, 56.80, TipoEstimacion.ESTIMACION_TAREA, MonedasDisponibles.EUR, "Estimacion de tipo Tarea con pagador", null, gasto1),

                new Estimacion(plan3, null, miembro7, 219.99, TipoEstimacion.ESTIMACION_PLAN, MonedasDisponibles.EUR, "Estimacion de tipo Tarea con pagador", miembro2, gasto1)
        );

        estimaciones = estimacionRepository.saveAll(estimaciones);

        return estimaciones;

    }

    public List<Gasto> poblarGastos(List<Tarea> tareas, List<Plan> planes, List<Miembro> miembros) {

        Tarea tarea1 = tareas.get(0);
        Tarea tarea2 = tareas.get(1);
        Tarea tarea3 = tareas.get(2);
        Tarea tarea4 = tareas.get(3);

        Plan plan1 = planes.get(0);
        Plan plan2 = planes.get(1);
        Plan plan3 = planes.get(2);

        Miembro miembro1= miembros.get(0);
        Miembro miembro2= miembros.get(1);
        Miembro miembro3= miembros.get(2);
        Miembro miembro4= miembros.get(3);
        Miembro miembro5= miembros.get(4);
        Miembro miembro6= miembros.get(5);
        Miembro miembro7= miembros.get(6);

        List<Miembro> listaEndeudados1 = Arrays.asList(miembro1, miembro2, miembro3);
        List<Miembro> listaEndeudados2 = Arrays.asList(miembro1, miembro2, miembro3, miembro5);

        List<Miembro> listaEndeudados3 = Arrays.asList(miembro4, miembro6);


        //Los gastos de plan1 y plan3 no lleva tarea


        List<Gasto> gastos = Arrays.asList(
                new Gasto(null, plan1, 50.0, "Desayuno", miembro1, "Desayuno en cafetería"),
                new Gasto(null, plan1, 120.0, "Transporte", miembro2, "Taxi ida y vuelta"),
                new Gasto(null, plan1, 75.5, "Materiales", miembro3, "Compra de materiales de oficina"),
                new Gasto(null, plan1, 200.0, "Almuerzo", miembro5, "Comida para el equipo"),
                new Gasto(null, plan1, 45.0, "Bebidas", miembro1, "Compra de botellas de agua"),

                new Gasto(null, plan2, 60.0, "Papelería", miembro2, "Útiles varios"),
                new Gasto(null, plan2, 90.0, "Publicidad", miembro3, "Anuncio en redes sociales"),
                new Gasto(tarea1, plan2, 150.0, "Servicio técnico", miembro4, "Reparación de laptop"),
                new Gasto(tarea2, plan2, 80.0, "Cena", miembro5, "Cena de cierre del proyecto"),
                new Gasto(tarea3, plan2, 20.0, "Postre", miembro1, "Helados para el equipo"),

                new Gasto(tarea4, plan3, 100.0, "Obsequios", miembro7, "Regalos para los participantes"),
                new Gasto(null, plan3, 30.0, "Estacionamiento", miembro4, "Pago de estacionamiento")

        );

        gastos.get(0).setMiembrosEndeudados(listaEndeudados1);
        gastos.get(1).setMiembrosEndeudados(listaEndeudados1);
        gastos.get(2).setMiembrosEndeudados(listaEndeudados1);
        gastos.get(3).setMiembrosEndeudados(listaEndeudados1);
        gastos.get(4).setMiembrosEndeudados(listaEndeudados1);

        gastos.get(5).setMiembrosEndeudados(listaEndeudados2);
        gastos.get(6).setMiembrosEndeudados(listaEndeudados1);
        gastos.get(7).setMiembrosEndeudados(listaEndeudados1);
        gastos.get(8).setMiembrosEndeudados(listaEndeudados1);
        gastos.get(9).setMiembrosEndeudados(listaEndeudados2);

        gastos.get(10).setMiembrosEndeudados(listaEndeudados3);
        gastos.get(11).setMiembrosEndeudados(listaEndeudados3);

        gastos = gastoRepository.saveAll(gastos);

        return gastos;
    }//poblarGastos

    public List<Miembro> poblarMiembros(List<Usuario> usuarios, List<Organizacion> organizaciones, List<Rol>roles) {

        Organizacion org1 = organizaciones.get(0);
        Organizacion org2 = organizaciones.get(1);

        Rol rolAdmin = roles.get(1);
        Rol rolMiembro = roles.get(2);


        List<Miembro> miembros = new ArrayList<>();

        // Usuario 1 -> org1, admin
        Miembro m1 = new Miembro(org1, rolAdmin, "jperez");
        m1.setUsuario(usuarios.get(0));
        m1.setAsociado(true);
        m1.setFechaIngreso(LocalDateTime.now());

        miembros.add(m1);

        // Usuario 2 -> org1, miembro
        Miembro m2 = new Miembro(org1, rolMiembro, "mgomez");
        m2.setUsuario(usuarios.get(1));
        m2.setAsociado(true);
        m2.setFechaIngreso(LocalDateTime.now());

        miembros.add(m2);

        // Usuario 3 -> org1 y org2, admin
        Miembro m3 = new Miembro(org1, rolAdmin, "cmartinez1");
        m3.setUsuario(usuarios.get(2));
        m3.setAsociado(true);
        m3.setFechaIngreso(LocalDateTime.now());

        miembros.add(m3);


        Miembro m4 = new Miembro(org2, rolAdmin, "cmartinez2");
        m4.setUsuario(usuarios.get(2));
        m4.setAsociado(true);
        m4.setFechaIngreso(LocalDateTime.now());

        miembros.add(m4);

        // Miembros sin usuario en org1
        Miembro m5 = new Miembro(org1, rolMiembro, "org1_invitado");
        miembros.add(m5);
        Miembro m6 = new Miembro(org1, rolMiembro, "org1_invitado2");
        miembros.add(m6);

        // Miembro sin usuario en org2
        Miembro m7 = new Miembro(org2, rolMiembro, "org2_invitado");
        miembros.add(m7);

        // Usuarios 4 y 5 sin miembros

        miembros = miembroRepository.saveAll(miembros);

        return miembros;

    }//poblarMiembros

    public List<Organizacion>  poblarOrganizaciones() {
        List<Organizacion> organizaciones = Arrays.asList(
            new Organizacion("BudgetCorp", "Gestión de presupuestos familiares."),
            new Organizacion("FinanceFlow", "Automatización de flujos financieros personales.")
        );
        organizaciones = organizacionRepository.saveAll(organizaciones);

        return organizaciones;
    }//poblarOrganizaciones

    public List<Plan> poblarPlanes( List<Organizacion> organizaciones) {

        Organizacion org1 = organizaciones.get(0);
        Organizacion org2 = organizaciones.get(1);

        List<Plan> planes = Arrays.asList(
                new Plan(
                        org1,
                        "Plan A de la Organización 1",
                        "Descripción del Plan A de la Organización. 1 PLAN SIMPLE",
                        LocalDateTime.of(2025, 6, 1, 10, 0),
                        LocalDateTime.of(2025, 6, 30, 10, 0),
                        ModoPlan.simple
                ),

                new Plan(
                        org1,
                        "Plan B de la Organización 1",
                        "Descripción del Plan B de la Organización 1. PLAN ESTRUCTURADO",
                        LocalDateTime.of(2025, 7, 1, 10, 0),
                        LocalDateTime.of(2025, 7, 31, 10, 0),
                        ModoPlan.estructurado
                ),

                new Plan(
                        org2,
                        "Plan único de la Organización 2",
                        "Descripción del Plan único de la Organización 2. PLAN SIMPLE",
                        LocalDateTime.of(2025, 6, 1, 10, 0),
                        LocalDateTime.of(2025, 6, 15, 10, 0),
                        ModoPlan.estructurado
                )
        );

        planes = planRepository.saveAll(planes);

        return planes;
    }//poblarPlanes

    public List<Rol> poblarRoles() {
        List<Rol> roles = Arrays.asList(
        new Rol(NombreRol.ROLE_SUPER),
        new Rol(NombreRol.ROLE_ADMIN),
        new Rol(NombreRol.ROLE_MEMBER)
        );
        roles = rolRepository.saveAll(roles);
        return roles;

    }//poblarRoles


    public List<Tarea> poblarTareas(List<Plan> planes, List<Miembro> miembros) {

        //Plan 1 es simple -> no tiene tareas

        //Tareas de Plan 2
        //Tarea1
        //Tarea2
        //Tarea3

        //Tareas de Plan 3
        //Tarea4

        Plan plan2 = planes.get(1);
        Plan plan3 = planes.get(2);

        List<Miembro> listaMiembro1 = Arrays.asList(miembros.get(0));
        List<Miembro> listaMiembro2 = Arrays.asList(miembros.get(1), miembros.get(2));
        List<Miembro> listaMiembro3 = Arrays.asList(miembros.get(0), miembros.get(1));
        List<Miembro> listaMiembro4 = Arrays.asList(miembros.get(3), miembros.get(5));

        List<Tarea> tareas = Arrays.asList(
            new Tarea(
                    plan2,
                    "Comprar comida semanal",
                    "Ir al supermercado y comprar alimentos para la semana.",
                    LocalDateTime.of(2025, 5, 18, 18, 0),
                    120.0,
                    MonedasDisponibles.EUR
            ),


         new Tarea(
                 plan2,
                "Pagar factura de electricidad",
                "Realizar el pago mensual del servicio eléctrico antes de la fecha límite.",
                LocalDateTime.of(2025, 5, 20, 17, 0),
                75.0,
                 MonedasDisponibles.EUR
        ),


         new Tarea(
                 plan2,
                "Repostar gasolina del coche",
                "Llenar el depósito del coche familiar para los desplazamientos semanales.",
                LocalDateTime.of(2025, 5, 16, 16, 0),
                60.0,
                 MonedasDisponibles.EUR
        ),
        new Tarea(
                plan3,
                "Pagar suscripción de internet",
                "Realizar el pago mensual del servicio de internet del hogar.",
                LocalDateTime.of(2025, 5, 19, 12, 0),
                50.0,
                MonedasDisponibles.EUR
        )
        );

        tareas.get(0).setMiembros(listaMiembro1);
        tareas.get(1).setMiembros(listaMiembro2);
        tareas.get(2).setMiembros(listaMiembro3);
        tareas.get(3).setMiembros(listaMiembro4);

        tareas = tareaRepository.saveAll(tareas);

        return tareas;

    }//poblarTareas

    public List<Usuario> poblarUsuarios() {
        List<Usuario> usuarios = Arrays.asList(
                new Usuario("juan.perez@mail.com", "Juan", "Pérez", passwordEncoder.encode("contraseña123")),
                new Usuario("maria.gomez@mail.com", "María", "Gómez", passwordEncoder.encode("contraseña456")),
                new Usuario("carlos.martinez@mail.com", "Carlos", "Martínez", passwordEncoder.encode("contraseña789")),
                new Usuario("ana.rodriguez@mail.com", "Ana", "Rodríguez", passwordEncoder.encode("contraseña012")),
                new Usuario("luis.fernandez@mail.com", "Luis", "Fernández", passwordEncoder.encode("contraseña345"))
        );

        usuarios = usuarioRepository.saveAll(usuarios);
        return usuarios;
    }//poblarUsuarios

}
