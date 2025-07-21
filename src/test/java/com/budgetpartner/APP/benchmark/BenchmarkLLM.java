package com.budgetpartner.APP.benchmark;

import com.budgetpartner.APP.admin.PobladorDB;
import com.budgetpartner.APP.dto.dashborard.DashboardDtoResponse;
import com.budgetpartner.APP.repository.UsuarioRepository;
import com.budgetpartner.APP.service.ChatbotService;
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

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

@SpringBootTest
public class BenchmarkLLM {

    private static final Logger logger = Logger.getLogger(BenchmarkLLM.class.getName());
    private static final String LOG_FILE = "benchmark.log";

    @BeforeAll
    static void init() throws IOException, URISyntaxException {
        // Poblamos DB con datos necesarios (organizaciones, roles, usuarios, miembros iniciales…)
        Dotenv dotenv = Dotenv.load();

        System.setProperty("OPENAI_API_KEY", dotenv.get("OPENAI_API_KEY"));//Key de OpenAI
        System.setProperty("DEEPSEEK_API_KEY",dotenv.get("DEEPSEEK_API_KEY"));//Key de Deepseek


        //Configuración log
        // 1) Localizar el .class de esta misma clase
        URL classUrl = Thread.currentThread()
                .getContextClassLoader()
                .getResource("com/budgetpartner/APP/benchmark/BenchmarkLLM.class");
        System.out.println("classUrl = " + classUrl);
        Path classPath = Paths.get(classUrl.toURI());

        // 2) Obtener su carpeta padre
        Path testDir = classPath.getParent();

        // 3) Definir la ruta completa al log dentro de esa carpeta
        Path logPath = testDir.resolve(LOG_FILE);
        System.out.println("logPath = " + logPath);

        // 4) Borrar log anterior (si existía)
        Files.deleteIfExists(logPath);

        // 5) Configurar FileHandler apuntando a esa ruta, con append=false
        FileHandler fileHandler = new FileHandler(logPath.toString(), /* append = */ false);
        fileHandler.setFormatter(new SimpleFormatter());

        // 6) Desactivar handlers por defecto y añadir sólo éste
        logger.setUseParentHandlers(false);
        logger.addHandler(fileHandler);
        logger.setLevel(Level.INFO);
    }

    @Autowired
    private ChatbotService chatbotService;

    private int countTokens (String text){
        return text.split("\\s+").length;
    }

    @Test
    public void multipleBenchmark1() {
        List<String> models = List.of("qwen3:4b");
        //"qwen3:8b", "gemma3:12b", "qwen3:4b"
        String prompt = "Devuelveme todos los gastos de este mes";

        for (String model : models) {
            long start = System.nanoTime();
            String response = chatbotService.obtenerInformacionQuey(prompt, model);
            long end = System.nanoTime();

            double elapsedSeconds = (end - start) / 1_000_000_000.0;
            int tokens = countTokens(response);

            //logger.info(response);
            logger.info(String.format(
                    "Modelo: %s | Tiempo: %.2fs | Tokens: %d | Tokens/s: %.2f",
                    model, elapsedSeconds, tokens, tokens / elapsedSeconds));


            //System.out.println(response);
            //System.out.printf("Modelo: %s\nTiempo: %.2fs\nTokens: %d\nTokens/s: %.2f\n\n", model, elapsedSeconds, tokens, tokens / elapsedSeconds);

        }

    }
}