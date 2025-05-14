package com.budgetpartner.APP.admin;

import com.budgetpartner.APP.domain.*;
import com.budgetpartner.APP.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Component
public class PobladorDB {


    //Definición de los servicios
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
    private  TareaRepository planRepository;

    @Autowired private JdbcTemplate jdbc;



    public void borrarTodo(){
        // Borrar entidades dependientes primero
        gastoRepository.deleteAll();
        tareaRepository.deleteAll();
        miembroRepository.deleteAll();

        // Luego las entidades principales
        usuarioRepository.deleteAll();
        organizacionRepository.deleteAll();
        planRepository.deleteAll();
        rolRepository.deleteAll();

        System.out.println("Base de datos limpiada con éxito.");

        //reiniciar Ids
        resetIds();

    }

    public void resetIds(){
        jdbc.execute("ALTER SEQUENCE usuario_seq RESTART WITH 1");
        jdbc.execute("ALTER SEQUENCE miembro_seq RESTART WITH 1");
        jdbc.execute("ALTER SEQUENCE organizacion_seq RESTART WITH 1");
        //jdbc.execute("ALTER SEQUENCE plan_seq RESTART WITH 1");
        jdbc.execute("ALTER SEQUENCE rol_seq RESTART WITH 1");
        //jdbc.execute("ALTER SEQUENCE tarea_seq RESTART WITH 1");
        //jdbc.execute("ALTER SEQUENCE gasto_seq RESTART WITH 1");

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
        poblarMiembros(usuarios, organizaciones, roles);

        poblarGastos();
        poblarPlanes();
        poblarTareas();


    }

    public List<Gasto> poblarGastos() {
        return null;
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

        // Miembro sin usuario en org1
        Miembro m5 = new Miembro(org1, rolMiembro, "org1_invitado");
        miembros.add(m5);

        // Miembro sin usuario en org2
        Miembro m6 = new Miembro(org2, rolMiembro, "org2_invitado");
        miembros.add(m6);

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

    public List<Plan> poblarPlanes() {
        return null;
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


    public List<Tarea> poblarTareas() {
        return null;
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
