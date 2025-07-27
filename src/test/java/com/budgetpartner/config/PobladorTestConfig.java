package com.budgetpartner.config;

import com.budgetpartner.APP.admin.PobladorDB;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;

@TestConfiguration
public class PobladorTestConfig {

    @Autowired
    private PobladorDB pobladorDB;

    @PostConstruct
    public void inicializarBaseDeDatos() {
        pobladorDB.borrarTodo();
        pobladorDB.poblarTodo();
    }
}