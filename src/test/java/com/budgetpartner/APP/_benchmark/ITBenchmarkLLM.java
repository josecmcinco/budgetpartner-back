package com.budgetpartner.APP._benchmark;

import com.budgetpartner.APP.dto.api.ChatbotQuery;
import com.budgetpartner.APP.service.AiService.OllamaAgentService;

import io.github.cdimascio.dotenv.Dotenv;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Objects;


@SpringBootTest
class ITBenchmarkLLM {

    private static final String FILE_PATH = "target/benchmark/benchmark-results.txt";

    private final OllamaAgentService ollamaAgentService;

    public ITBenchmarkLLM(OllamaAgentService ollamaAgentService) {
        this.ollamaAgentService = ollamaAgentService;
    }

    @BeforeAll
    static void init() throws IOException {
        // Poblamos DB con datos necesarios (organizaciones, roles, usuarios, miembros iniciales…)
        Dotenv dotenv = Dotenv.load();

        System.setProperty("OPENAI_API_KEY", Objects.requireNonNull(dotenv.get("OPENAI_API_KEY")));//Key de OpenAI
        System.setProperty("DEEPSEEK_API_KEY", Objects.requireNonNull(dotenv.get("DEEPSEEK_API_KEY")));//Key de Deepseek

        Path path = Paths.get(FILE_PATH);
        try {
            if (Files.exists(path)) {
                // Si el archivo ya existe, agrega una separación visual
                Files.write(path, "%n-----------%n".getBytes(), StandardOpenOption.APPEND);
            } else {
                // Si no existe, lo crea vacío
                Files.createDirectories(path.getParent()); // Asegura que 'target/benchmark' exista
                Files.createFile(path);
            }
        } catch (IOException e) {
            throw new RuntimeException("Error inicializando archivo de benchmark", e);
        }


    }

    private int countTokens (String text){
        return text.split("\\s+").length;
    }

    @Test
    @Order(1)
    void multipleBenchmark1(){
        gestionPeticion("Devuelveme la informacion del miembro de id 3");

    }

    @Test
    @Order(1)
    void multipleBenchmark2(){
        gestionPeticion("Crea una organizacion llamada Prueba usando como nombre Prueba y con nick Luis");

    }

    void gestionPeticion(String prompt) {
        //LISTA DE MODELOS EN LOCAL
        //"qwen3:8b", "gemma3:12b", "qwen3:4b"
        List<String> models = List.of("qwen3:4b", "gemma3:12b");

        ChatbotQuery query = new ChatbotQuery(prompt, true);

        for (String model : models) {
            long start = System.nanoTime();
            String response = ollamaAgentService.processUserMessage(query, model);
            long end = System.nanoTime();

            double elapsedSeconds = (end - start) / 1_000_000_000.0;
            int tokens = countTokens(response);

            String logLine = String.format(
                    "Modelo: %s | Tiempo: %.2fs | Tokens: %d | Tokens/s: %.2f%n",
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
    void multipleBenchmark1() {
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