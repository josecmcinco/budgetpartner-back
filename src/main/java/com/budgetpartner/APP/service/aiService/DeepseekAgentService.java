package com.budgetpartner.APP.service.aiService;


import com.budgetpartner.APP.dto.api.ChatbotQuery;
import com.budgetpartner.APP.dto.api.DeepseekAgentInstruction;
import com.budgetpartner.APP.aiTools.ToolRegistry;
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

        if (chatQuery.isConversacionNueva()) {
            historial.clear();
        }

        String currentMessage = chatQuery.getPrompt();
        currentMessage = currentMessage.replace("\"", "\\\"");

        montarMensaje(currentMessage);

        boolean finished = false;
        String responseToUser = "";

        while (!finished) {
            DeepseekAgentInstruction instruction = querry(currentMessage);

            if (instruction.isFinished()) {
                responseToUser = instruction.getFinalResponse();
                finished = true;
            } else {
                try {
                    Object resultado = toolRegistry.invokeTool(
                            instruction.getToolName(),
                            instruction.getArguments().toArray()
                    );
                    ObjectMapper mapper = new ObjectMapper();
                    mapper.registerModule(new JavaTimeModule());
                    mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
                    String resultadoJson = mapper.writeValueAsString(resultado);
                    historial.add(new MessageAi("user",
                            "Resultado de " + instruction.getToolName() + ": " + resultadoJson));
                } catch (Exception e) {
                    responseToUser = "Error al ejecutar herramienta: " + e.getMessage();
                    break;
                }
            }
        }

        imprimirHistorial();
        return responseToUser;
    }

    private void imprimirHistorial() {
        logger.info("---HISTORIAL DE CONVERSACIÓN");
        for (int i = 0; i < historial.size(); i++) {
            MessageAi msg = historial.get(i);
            if ("system".equals(msg.getRole())) continue;
            logger.info("[{}] {}: {}", i, msg.getRole().toUpperCase(), msg.getContent());
        }
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
            Eres un agente de una aplicación de gastos que ejecuta tareas paso a paso usando herramientas.

            Dispones de estas herramientas:
            %s
            Las variables que empiezan y acaban con '_' son opcionales; pásalas como cadena vacía "" si no aplican.

            FLUJO DE TRABAJO:
            - Ejecuta una herramienta por turno. Después de cada llamada recibirás su resultado en un mensaje con rol "tool".
            - Usa el resultado del turno anterior para decidir el siguiente paso. Los resultados incluyen los IDs de los recursos creados — úsalos en llamadas posteriores que los necesiten.
            - Si una tarea requiere múltiples pasos (crear org, luego miembros, luego plan...), ejecútalos en orden, uno por turno.
            - Los argumentos deben pasarse en el orden exacto en que aparecen en la firma de la herramienta.
            - Cuando hayas completado todos los pasos, pon "finished": true y resume lo que has hecho en "finalResponse".

            Devuelve siempre un único JSON con estos campos:
            - "toolName": nombre exacto de la herramienta (ej: "MiembroTools.crearMiembroDesdeTexto"). Vacío ("") si no necesitas más herramientas.
            - "arguments": lista de strings con los argumentos en orden. Vacío ([]) si no aplican.
            - "finished": true si has completado la tarea, false si aún necesitas ejecutar más herramientas.
            - "finalResponse": respuesta clara y amable para el usuario, solo si "finished" es true. Vacío ("") en caso contrario.

            - IMPORTANTE: NO LE PASES IDS DE ELEMENTOS AL USUARIO EN FINAL_RESPONSE
            
            Ejemplo — paso intermedio:
            {
                "toolName": "MiembroTools.crearMiembroDesdeTexto",
                "arguments": ["12", "", "juan"],
                "finished": false,
                "finalResponse": ""
            }

            Ejemplo — paso final:
            {
                "toolName": "",
                "arguments": [],
                "finished": true,
                "finalResponse": "He creado la organización y los tres miembros correctamente."
            }

            IMPORTANTE: Solo devuelve el JSON, sin texto adicional, explicación ni comentarios.
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


