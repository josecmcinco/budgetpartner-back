package com.budgetpartner.APP._benchmark;

import com.budgetpartner.APP.admin.PobladorDB;
import com.budgetpartner.APP.dto.api.ChatQuery;
import com.budgetpartner.APP.dto.dashborard.DashboardDtoResponse;
import com.budgetpartner.APP.repository.UsuarioRepository;
import com.budgetpartner.APP.service.AiService.OllamaAgentService;

import com.budgetpartner.APP.service.JwtService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.cdimascio.dotenv.Dotenv;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

@SpringBootTest
public class BenchmarkLLM {

    private static final String FILE_PATH = "target/benchmark/benchmark-results.txt";

    @BeforeAll
    static void init() throws IOException, URISyntaxException {
        // Poblamos DB con datos necesarios (organizaciones, roles, usuarios, miembros iniciales…)
        Dotenv dotenv = Dotenv.load();

        System.setProperty("OPENAI_API_KEY", dotenv.get("OPENAI_API_KEY"));//Key de OpenAI
        System.setProperty("DEEPSEEK_API_KEY",dotenv.get("DEEPSEEK_API_KEY"));//Key de Deepseek

        Path path = Paths.get(FILE_PATH);
        try {
            if (Files.exists(path)) {
                // Si el archivo ya existe, agrega una separación visual
                Files.write(path, "\n-----------\n".getBytes(), StandardOpenOption.APPEND);
            } else {
                // Si no existe, lo crea vacío
                Files.createDirectories(path.getParent()); // Asegura que 'target/benchmark' exista
                Files.createFile(path);
            }
        } catch (IOException e) {
            throw new RuntimeException("Error inicializando archivo de benchmark", e);
        }


    }

    @Autowired
    private OllamaAgentService ollamaAgentService;

    private int countTokens (String text){
        return text.split("\\s+").length;
    }

    @Test
    @Order(1)
    public void multipleBenchmark1(){
        gestionPeticion("Devuelveme la informacion del miembro de id 3");

    }

    @Test
    @Order(1)
    public void multipleBenchmark2(){
        gestionPeticion("Crea una organizacion llamada Prueba usando como nombre Prueba y con nick Luis");

    }

    public void gestionPeticion(String prompt) {
        //LISTA DE MODELOS EN LOCAL
        //"qwen3:8b", "gemma3:12b", "qwen3:4b"
        List<String> models = List.of("qwen3:4b", "gemma3:12b");

        ChatQuery query = new ChatQuery(prompt, true);

        for (String model : models) {
            long start = System.nanoTime();
            String response = ollamaAgentService.processUserMessage(query, model);
            long end = System.nanoTime();

            double elapsedSeconds = (end - start) / 1_000_000_000.0;
            int tokens = countTokens(response);

            String logLine = String.format(
                    "Modelo: %s | Tiempo: %.2fs | Tokens: %d | Tokens/s: %.2f\n",
                    model, elapsedSeconds, tokens, tokens / elapsedSeconds
            );
            // Escribir al archivo
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
                writer.write(logLine);
            } catch (IOException e) {
                e.printStackTrace();
            }

            System.out.println(response);
            System.out.print(logLine);
        }
    }


    /*
    @Test
    public void multipleBenchmark1() {
        //LISTA DE MODELOS EN LOCAL
        //"qwen3:8b", "gemma3:12b", "qwen3:4b"
        List<String> models = List.of("gemma3:12b", "qwen3:4b");

        String prompt = "Devuelveme la informacion del miembro de id 2";
        ChatQuery query = new ChatQuery(prompt, true);

        for (String model : models) {
            long start = System.nanoTime();
            String response = ollamaAgentService.processUserMessage(query, model);
            long end = System.nanoTime();

            double elapsedSeconds = (end - start) / 1_000_000_000.0;
            int tokens = countTokens(response);

            //logger.info(response);
            logger.info(String.format(
                    "Modelo: %s | Tiempo: %.2fs | Tokens: %d | Tokens/s: %.2f",
                    model, elapsedSeconds, tokens, tokens / elapsedSeconds));


            System.out.println(response);
            System.out.printf("Modelo: %s\nTiempo: %.2fs\nTokens: %d\nTokens/s: %.2f\n\n", model, elapsedSeconds, tokens, tokens / elapsedSeconds);

        }


    }*/
}