package com.budgetpartner.APP.admin;

import com.budgetpartner.APP.entity.*;
import com.budgetpartner.APP.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Component
public class PobladorDB {


    //Definición de los servicios
    @Autowired private  UsuarioRepository usuarioRepository;

    @Autowired private  RolRepository rolRepository;

    @Autowired private  OrganizacionRepository organizacionRepository;

    @Autowired private  MiembroRepository miembroRepository;

    @Autowired private  GastoRepository gastoRepository;

    @Autowired private  TareaRepository tareaRepository;

    @Autowired private  PlanRepository planRepository;

    @Autowired private JdbcTemplate jdbc;



    public void borrarTodo(){
        // Borrar entidades dependientes primero
        gastoRepository.deleteAll();
        tareaRepository.deleteAll();
        miembroRepository.deleteAll();

        // Luego las entidades principales
        usuarioRepository.deleteAll();
        planRepository.deleteAll();

        rolRepository.deleteAll();
        organizacionRepository.deleteAll();

        System.out.println("Base de datos limpiada con éxito.");

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

        System.out.println("Identificadores reseteados con éxito.");
    }

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


        List<Gasto> gastos = Arrays.asList(
                new Gasto(tarea1, plan1, 50.0, "Desayuno", miembro1, "Desayuno en cafetería"),
                new Gasto(tarea1, plan1, 120.0, "Transporte", miembro2, "Taxi ida y vuelta"),
                new Gasto(tarea1, plan1, 75.5, "Materiales", miembro3, "Compra de materiales de oficina"),
                new Gasto(tarea1, plan1, 30.0, "Estacionamiento", miembro6, "Pago de estacionamiento"),

                new Gasto(tarea2, plan1, 200.0, "Almuerzo", miembro5, "Comida para el equipo"),
                new Gasto(tarea2, plan1, 45.0, "Bebidas", miembro1, "Compra de botellas de agua"),

                new Gasto(tarea3, plan1, 60.0, "Papelería", miembro2, "Útiles varios"),
                new Gasto(tarea3, plan1, 90.0, "Publicidad", miembro3, "Anuncio en redes sociales"),
                new Gasto(tarea3, plan1, 150.0, "Servicio técnico", miembro4, "Reparación de laptop"),

                new Gasto(tarea4, plan2, 80.0, "Cena", miembro5, "Cena de cierre del proyecto"),
                new Gasto(tarea4, plan2, 20.0, "Postre", miembro1, "Helados para el equipo"),

                new Gasto(tarea4, plan3, 100.0, "Obsequios", miembro7, "Regalos para los participantes")

        );

        gastoRepository.saveAll(gastos);

        System.out.println("Gatos creados");

        return gastos;
    }//poblarGastos

    public List<Miembro> poblarMiembros(List<Usuario> usuarios, List<Organizacion> organizaciones, List<Rol>roles) {

        Organizacion org1 = organizaciones.get(0);
        Organizacion org2 = organizaciones.get(1);

        Rol rolAdmin = roles.get(1);
        Rol rolMiembro = roles.get(2);


        List<Miembro> miembros = new ArrayList<>();

        // Usuario 1 → org1, admin
        Miembro m1 = new Miembro(org1, rolAdmin, "jperez");
        m1.asociarUsuario(usuarios.get(0));
        miembros.add(m1);

        // Usuario 2 → org1, miembro
        Miembro m2 = new Miembro(org1, rolMiembro, "mgomez");
        m2.asociarUsuario(usuarios.get(1));
        miembros.add(m2);

        // Usuario 3 → org1 y org2, admin
        Miembro m3 = new Miembro(org1, rolAdmin, "cmartinez1");
        m3.asociarUsuario(usuarios.get(2));
        miembros.add(m3);

        Miembro m4 = new Miembro(org2, rolAdmin, "cmartinez2");
        m4.asociarUsuario(usuarios.get(2));
        miembros.add(m4);

        // Miembros sin usuario en org1
        Miembro m5 = new Miembro(org1, rolMiembro, "org1_invitado");
        miembros.add(m5);
        Miembro m6 = new Miembro(org1, rolMiembro, "org1_invitado2");
        miembros.add(m6);

        // Miembro sin usuario en org2
        Miembro m7 = new Miembro(org2, rolMiembro, "org2_invitado");
        miembros.add(m7);

        // Usuarios 4 y 5 → sin miembros

        miembroRepository.saveAll(miembros);

        System.out.println("Miembros creados");

        return miembros;

    }//poblarMiembros

    public List<Organizacion>  poblarOrganizaciones() {
        List<Organizacion> organizaciones = Arrays.asList(
            new Organizacion("BudgetCorp", "Gestión de presupuestos familiares."),
            new Organizacion("FinanceFlow", "Automatización de flujos financieros personales.")
        );
        organizacionRepository.saveAll(organizaciones);
        System.out.println("Organizaciones creadas");

        return organizaciones;
    }//poblarOrganizaciones

    public List<Plan> poblarPlanes( List<Organizacion> organizaciones) {

        Organizacion org1 = organizaciones.get(0);
        Organizacion org2 = organizaciones.get(1);

        List<Plan> planes = Arrays.asList(
                new Plan(
                        org1,
                        "Plan A de la Organización 1",
                        "Descripción del Plan A de la Organización 1",
                        LocalDateTime.of(2025, 6, 1, 10, 0),
                        LocalDateTime.of(2025, 6, 30, 10, 0)
                ),

                new Plan(
                        org1,
                        "Plan B de la Organización 1",
                        "Descripción del Plan B de la Organización 1",
                        LocalDateTime.of(2025, 7, 1, 10, 0),
                        LocalDateTime.of(2025, 7, 31, 10, 0)
                ),

                new Plan(
                        org2,
                        "Plan único de la Organización 2",
                        "Descripción del Plan único de la Organización 2",
                        LocalDateTime.of(2025, 6, 1, 10, 0),
                        LocalDateTime.of(2025, 6, 15, 10, 0)
                )
        );

        planRepository.saveAll(planes);
        System.out.println("Planes creados");

        return planes;
    }//poblarPlanes

    public List<Rol> poblarRoles() {
        List<Rol> roles = Arrays.asList(
        new Rol("super", "ALL_PRIVILEGES"),
         new Rol("administrador", "CREATE,READ,UPDATE,DELETE"),
        new Rol("miembro", "READ")
        );
        rolRepository.saveAll(roles);
        System.out.println("Roles creados");
        return roles;

    }//poblarRoles


    public List<Tarea> poblarTareas(List<Plan> planes, List<Miembro> miembros) {
        //Creación de tres tareas
        //Tarea1 tiene plan = 1
        //Tarea2 tiene plan = 1
        //Tarea3 tiene plan = 2
        //Tarea4 tiene plan = 3

        Plan plan1 = planes.get(0);
        Plan plan2 = planes.get(1);
        Plan plan3 = planes.get(2);

        List<Miembro> listaMiembro1 = miembros;
        List<Miembro> listaMiembro2 = miembros;
        List<Miembro> listaMiembro3 = miembros;
        List<Miembro> listaMiembro4 = miembros;


        List<Tarea> tareas = Arrays.asList(
            new Tarea(
                    plan1,
                    "Comprar comida semanal",
                    "Ir al supermercado y comprar alimentos para la semana.",
                    LocalDateTime.of(2025, 5, 18, 18, 0),
                    120.0,
                    "EUR",
                    listaMiembro1
            ),


         new Tarea(
                 plan1,
                "Pagar factura de electricidad",
                "Realizar el pago mensual del servicio eléctrico antes de la fecha límite.",
                LocalDateTime.of(2025, 5, 20, 17, 0),
                75.0,
                "EUR",
                 listaMiembro2
        ),


         new Tarea(
                 plan2,
                "Repostar gasolina del coche",
                "Llenar el depósito del coche familiar para los desplazamientos semanales.",
                LocalDateTime.of(2025, 5, 16, 16, 0),
                60.0,
                "EUR",
                 listaMiembro3
        ),
        new Tarea(
                plan3,
                "Pagar suscripción de internet",
                "Realizar el pago mensual del servicio de internet del hogar.",
                LocalDateTime.of(2025, 5, 19, 12, 0),
                50.0,
                "EUR",
                listaMiembro4
        )
        );
        tareaRepository.saveAll(tareas);

        System.out.println("Tareas creadas");
        return tareas;
    }//poblarTareas

    public List<Usuario> poblarUsuarios() {
        List<Usuario> usuarios = Arrays.asList(
                new Usuario("juan.perez@mail.com", "Juan", "Pérez", "contraseña123"),
                new Usuario("maria.gomez@mail.com", "María", "Gómez", "contraseña456"),
                new Usuario("carlos.martinez@mail.com", "Carlos", "Martínez", "contraseña789"),
                new Usuario("ana.rodriguez@mail.com", "Ana", "Rodríguez", "contraseña012"),
                new Usuario("luis.fernandez@mail.com", "Luis", "Fernández", "contraseña345")
        );

        usuarioRepository.saveAll(usuarios);
        System.out.println("Usuarios creados");
        return usuarios;
    }//poblarUsuarios

}
