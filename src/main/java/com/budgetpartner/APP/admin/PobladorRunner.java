package com.budgetpartner.APP.admin;

import com.budgetpartner.APP.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Arrays;

@Component
public class PobladorRunner implements CommandLineRunner {

    private final PobladorDB pobladorDB;

    public PobladorRunner(PobladorDB pobladorDB) {
        this.pobladorDB = pobladorDB;
    }

    @Override
    public void run(String... args) {
        List<String> argumentos = Arrays.asList(args);

        boolean borrar = argumentos.contains("--borrar");
        boolean poblar = argumentos.contains("--poblar");

        if (!borrar && !poblar) {
            System.out.println("No se pasó ningún argumento (--borrar o --poblar). Nada que hacer.");
            return;
        }

        if (borrar) {
            System.out.println("Borrando la base de datos...");
            pobladorDB.borrarTodo();
        }

        if (poblar) {
            System.out.println("Poblando la base de datos...");
            pobladorDB.poblarTodo();
        }
    }
}
