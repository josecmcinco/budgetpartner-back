package com.budgetpartner.APP.service.aiService;

import com.budgetpartner.APP.dto.api.ChatbotQuery;
import com.budgetpartner.APP.dto.api.OllamaAgentInstruction;
import com.budgetpartner.APP.aiTools.ToolRegistry;
import com.budgetpartner.APP.util.MessageAi;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import dev.langchain4j.model.ollama.OllamaChatModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import java.util.ArrayList;
import java.util.List;

@Service
public class OllamaAgentService {


        @Autowired
        private ToolRegistry toolRegistry;

        private static final String BASE_URL_OLLAMA = "http://localhost:11434/v1";
        private static final Logger logger = LoggerFactory.getLogger(OllamaAgentService.class);
        private List<MessageAi> historial = new ArrayList<>();

        public String processUserMessage(ChatbotQuery chatQuery, String modeloOllama) {

            if (chatQuery.isConversacionNueva()) {
                historial.clear();
            }

            String currentMessage = chatQuery.getPrompt();
            currentMessage = currentMessage.replace("\"", "\\\"");

            montarMensaje(currentMessage);

            boolean finished = false;
            String responseToUser = "";

            while (!finished) {
                // 1. Obtener instrucción desde Ollama
                OllamaAgentInstruction instruction = query(modeloOllama);

                if (instruction.isFinished()) {
                    responseToUser = instruction.getFinalResponse();
                    finished = true;
                } else {
                    // 2. Ejecutar herramienta MCP
                    try {
                        Object result = toolRegistry.invokeTool(
                                instruction.getToolName(),
                                instruction.getArguments().toArray()
                        );

                        ObjectMapper mapper = new ObjectMapper();
                        mapper.registerModule(new JavaTimeModule());
                        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

                        currentMessage = mapper.writeValueAsString(result);
                        currentMessage = currentMessage.replace("\"", "\\\"");
                        historial.add(
                                new MessageAi("user",
                                        "Resultado de " + instruction.getToolName() + ": " + currentMessage
                                ));

                    } catch (Exception e) {
                        responseToUser = "Error al ejecutar herramienta: " + e.getMessage();
                        break;
                    }
                }
            }

            return responseToUser;
        }

    private OllamaAgentInstruction query(String modeloOllama) {

        ObjectMapper mapper = new ObjectMapper();

        String messagesJson;
        try {
            messagesJson = mapper.writeValueAsString(historial);
        } catch (Exception e) {
            throw new RuntimeException("Error serializando historial", e);
        }

        String body = """
            {
                "model": "%s",
                "messages": %s,
                "stream": false
            }
            """.formatted(modeloOllama, messagesJson);

        System.out.println(body);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:11434/v1/chat/completions"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .build();

        HttpClient cliente = HttpClient.newHttpClient();

        try {

            HttpResponse<String> response =
                    cliente.send(request, HttpResponse.BodyHandlers.ofString());

            String responseBody = response.body();

            System.out.println(responseBody);

            String content = extractContentFromResponse(responseBody);

            // Elimina el razonamiento de modelos como Qwen
            content = content.replaceAll("(?s)<think>\\s*.*?\\s*</think>", "").trim();

            String cleanJson = stripMarkdownCodeBlock(content);

            System.out.println(cleanJson);

            historial.add(new MessageAi("assistant", cleanJson));

            return OllamaAgentInstruction.fromJson(cleanJson);

        } catch (Exception e) {
            logger.error("Error en la llamada a Ollama", e);
            throw new RuntimeException(e);
        }
    }

    private void montarMensaje(String userMessage) {
        String herramientasTexto = toolRegistry.getToolDescriptionsForPrompt();
        String startPrompt = """
        Eres un agente de una aplicación de gastos que debe elegir qué herramienta usar para cumplir la solicitud del usuario.

        %s
        Las variables que empiezan y acaban con '_' son opcionales.

        Tu tarea es devolver un único JSON con los siguientes campos obligatorios:

        - "toolName": nombre exacto de la herramienta que quieres usar (por ejemplo: "MiembroTools.crearMiembro"). Si no necesitas usar ninguna herramienta más, deja este campo vacío ("").
        - "arguments": una lista de strings con los argumentos que pasarás a la herramienta. Si no aplican argumentos, deja la lista vacía ([]).
        - "finished": true/false. Si ejecutas una herramienta ha de ser false
        - "finalResponse": mensaje para el usuario (solo si finished es true)

        IMPORTANTE: Solo devuelve el JSON, sin ningún texto adicional ni explicaciones.
    """.formatted(herramientasTexto);

        if (historial.isEmpty()) {
            historial.add(new MessageAi("system", startPrompt));
        }

        historial.add(new MessageAi("user", userMessage));
    }

    static String extractContentFromResponse(String responseBody) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(responseBody);

        return root.path("choices").get(0).path("message").path("content").asText();
    }

    static String stripMarkdownCodeBlock(String content) {
        // Normaliza los saltos de línea
        content = content.replace("\r\n", "\n").trim();

        // Detecta si empieza con bloque de markdown tipo ```json o ```
        if (content.startsWith("```")) {
            int firstNewline = content.indexOf("\n");
            int lastBackticks = content.lastIndexOf("```");

            if (firstNewline != -1 && lastBackticks != -1 && lastBackticks > firstNewline) {
                return content.substring(firstNewline + 1, lastBackticks).trim();
            }
        }

        return content;}
}
