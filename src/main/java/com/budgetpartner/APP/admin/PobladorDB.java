package com.budgetpartner.APP.admin;

import com.budgetpartner.APP.domain.Usuario;
import com.budgetpartner.APP.repository.UsuarioRepository;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;


@Component
public class PobladorDB {

    private final UsuarioRepository usuarioRepository;

    public PobladorDB(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public void borrarTodo(){


    }

    public void poblarTodo(){
        poblarGasto();
        poblarMiembros();
        poblarOrganizacion();
        poblarPlan();
        poblarRol();
        poblarTarea();
        poblarUsuarios();

    }

    public void poblarGasto() {

    }//poblarGasto

    public void poblarMiembros() {

    }//poblarMiembros

    public void poblarOrganizacion() {

    }//poblarOrganizacion

    public void poblarPlan() {

    }//poblarPlan


    public void poblarRol() {

    }//poblarRol


    public void poblarTarea() {

    }//poblarTarea

    public void poblarUsuarios() {
        System.out.println("Usuarios devueltos");
        List<Usuario> usuarios = Arrays.asList(
                new Usuario(1L, "juan.perez@mail.com", "Juan", "Pérez", "contraseña123"),
                new Usuario(2L, "maria.gomez@mail.com", "María", "Gómez", "contraseña456"),
                new Usuario(3L, "carlos.martinez@mail.com", "Carlos", "Martínez", "contraseña789"),
                new Usuario(4L, "ana.rodriguez@mail.com", "Ana", "Rodríguez", "contraseña012"),
                new Usuario(5L, "luis.fernandez@mail.com", "Luis", "Fernández", "contraseña345")
        );
    }//poblarUsuarios

}
