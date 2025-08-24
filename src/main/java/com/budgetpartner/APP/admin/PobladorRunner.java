package com.budgetpartner.APP.admin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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


    private static final Logger logger = LoggerFactory.getLogger(PobladorRunner.class);

    @Override
    public void run(String... args) {
        List<String> argumentos = Arrays.asList(args);

        boolean borrar = argumentos.contains("--borrar");
        boolean poblar = argumentos.contains("--poblar");

        if (!borrar && !poblar) {
            logger.info("No se pasó ningún argumento (--borrar o --poblar). Nada que hacer.");
            return;
        }

        if (borrar) {
            logger.info("Borrando la base de datos...");
            pobladorDB.borrarTodo();
        }

        if (poblar) {
            logger.info("Poblando la base de datos...");
            pobladorDB.poblarTodo();
        }
    }
}
