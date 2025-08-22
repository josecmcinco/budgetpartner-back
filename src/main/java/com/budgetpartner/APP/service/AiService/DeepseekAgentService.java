package com.budgetpartner.APP.service.AiService;


import com.budgetpartner.APP.dto.api.ChatbotQuery;
import com.budgetpartner.APP.dto.api.DeepseekAgentInstruction;
import com.budgetpartner.APP.mcp.ToolRegistry;
import com.budgetpartner.APP.util.MessageAi;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;


@Service
public class DeepseekAgentService {

    @Autowired
    private ToolRegistry toolRegistry;

    //private final OpenAiChatModel deepseekModel; // Inyectado en configuración como un ChatModel

    private static final String DEEPSEEK_API_KEY = System.getProperty("DEEPSEEK_API_KEY");
    private static final String BASE_URL_DEEPSEEK = "https://api.deepseek.com/v1";
    private static final Logger logger = LoggerFactory.getLogger(DeepseekAgentService.class);

    private List<MessageAi> historial = new ArrayList<>();

    public String processUserMessage(ChatbotQuery chatQuery) {

        String currentMessage = chatQuery.getPrompt();
        currentMessage = currentMessage.replace("\"", "\\\""); //Quitar carácteres especiales

        montarMensaje(currentMessage);

        //Valores por defecto de query inicial
        boolean finished = false;
        String responseToUser = "";

        while (!finished) {
            // 1. Obtener instrucción desde Deepseek
            DeepseekAgentInstruction instruction = querry(currentMessage);

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
                    // Convertir el resultado a JSON legible por el modelo
                    ObjectMapper mapper = new ObjectMapper();
                    mapper.registerModule(new JavaTimeModule());
                    mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

                    //currentMessage = "  \"tool_call_id\": \"call_1\"," + mapper.writeValueAsString(result);
                    currentMessage = currentMessage.replace("\"", "\\\"");
                    historial.add(new MessageAi("tool", currentMessage, "call_1"));

                } catch (Exception e) {
                    responseToUser = "Error al ejecutar herramienta: " + e.getMessage();
                    break;
                }
            }
        }

        return responseToUser;
    }

    private DeepseekAgentInstruction querry(String context) {

        ObjectMapper mapper = new ObjectMapper();

        //Obtiene el historial de mensajes
        String messagesJson = "";
        try {
            messagesJson = mapper.writeValueAsString(historial);
        } catch (Exception e) {
            throw new RuntimeException("Error serializando historial", e);}

        String body = """
                {
                    "model": "deepseek-chat",
                    "messages":
                         %s
                    , "stream": false
                }
                """.formatted(messagesJson);

        System.out.println(body);
        var request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL_DEEPSEEK + "/chat/completions"))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + DEEPSEEK_API_KEY)
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .build();
        var cliente = HttpClient.newHttpClient();

        try{
            var response = cliente.send(request, HttpResponse.BodyHandlers.ofString());
            String responseBody = response.body();

            System.out.println(responseBody);

            // Parsear JSON a DeepseekAgentInstruction
            String content = extractContentFromResponse(response.body());
            String cleanJson = stripMarkdownCodeBlock(content);

            System.out.println(cleanJson);

            historial.add(new MessageAi("assistant", cleanJson));
            return DeepseekAgentInstruction.fromJson(cleanJson);

        }
        catch(Exception e){
            logger.info("Error en la llamada a Deepseek");
            throw  new RuntimeException(e);}

    }


    private void montarMensaje(String userMessage){
        String herramientasTexto = toolRegistry.getToolDescriptionsForPrompt();
        String startPrompt = """ 
            Eres un agente de una aplicación de gastos que debe elegir qué herramienta usar para cumplir la solicitud del usuario.
    
            Dispones de estas herramientas:
            %s
            Las variables que empiezan y acaban con '_' son opcionales.
    
            Tu tarea es devolver un único JSON con los siguientes campos obligatorios:
    
            - "toolName": nombre exacto de la herramienta que quieres usar (por ejemplo: "MiembroTools.crearMiembro"). Si no necesitas usar ninguna herramienta más, deja este campo vacío ("").
                    - "arguments": una lista de strings con los argumentos que pasarás a la herramienta. Si no aplican argumentos, deja la lista vacía ([]).
            - "finished": un booleano (true/false). Debes poner "true" si la conversación ha terminado y no se requiere ninguna acción adicional. Pon "false" si aún necesitas usar alguna herramienta para continuar.
            - "finalResponse": un mensaje de texto que será la respuesta final al usuario, solo si "finished" es true. Si "finished" es false, este campo debe ser una cadena vacía (""). Da una respuesta clara y amable para el usuario
    
                    Ejemplo de JSON para continuar:
            {
                "toolName": "MiembroTools.buscarMiembro",
                    "arguments": ["Juan Pérez"],
                "finished": false,
                    "finalResponse": ""
            }
    
            Ejemplo de JSON para finalizar:
            {
                "toolName": "",
                    "arguments": [],
                "finished": true,
                    "finalResponse": "He encontrado el miembro y registrado el gasto correctamente."
            }
    
            IMPORTANTE: Solo devuelve el JSON, sin ningún texto adicional, explicación o comentario.
        """.formatted(herramientasTexto);
        historial.add(new MessageAi("system", startPrompt));

        // Agregar el mensaje nuevo del usuario
        historial.add(new MessageAi("user", userMessage));

    }

    static String extractContentFromResponse(String responseBody) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(responseBody);

        return root.path("choices")
                .get(0)
                .path("message")
                .path("content")
                .asText();
    }

    static String stripMarkdownCodeBlock(String content) {
        if (content.startsWith("```")) {
            int firstNewline = content.indexOf("\n");
            int lastBackticks = content.lastIndexOf("```");

            if (firstNewline != -1 && lastBackticks != -1 && lastBackticks > firstNewline) {
                return content.substring(firstNewline + 1, lastBackticks).trim();
            }
        }
        return content.trim(); // Por si no viene con markdown
    }
}


