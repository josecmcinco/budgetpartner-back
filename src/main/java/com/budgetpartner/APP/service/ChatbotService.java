package com.budgetpartner.APP.service;

import com.budgetpartner.APP.admin.PobladorDB;
import com.budgetpartner.APP.dto.api.ChatQuery;
import com.budgetpartner.APP.dto.api.ChatResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.prompt.Prompt;
//import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.lang.runtime.ObjectMethods;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;

import dev.langchain4j.model.ollama.OllamaChatModel;

@Service
public class ChatbotService {

    @Autowired
    private OpenAiChatModel openAIChatModel;


    private static final String intermediateChatbotService = "qwen3:4b" ;

    private static final Logger logger = LoggerFactory.getLogger(PobladorDB.class);

    private static final String DEEPSEEK_API_KEY = System.getProperty("DEEPSEEK_API_KEY");
    private static final String BASE_URL_DEEPSEEK = "https://api.deepseek.com/v1";


    //Procesa el mensaje del usuario
    //Extrae la acción a realizar y con que utilizando un primer modelo de IA más ligero
    //Usa la respuesta para que una IA de mayor capacidad sepa que hacer

    public ChatResponse processMessage(String model, ChatQuery query) {

        //Obtener información que se quiere procesar

        String informacionQuey  = obtenerInformacionQuey(query.getPrompt(), intermediateChatbotService);



        String message = query.getPrompt();
        String result = "";


        // Procesar la petición
        switch (model.toLowerCase()) {
            case "openai":
                result = mensajeOpenAI(message);
                break;

            case "deepseek":
                result = mensajeDeepseek(message);
                break;
            case "local":
                result = mensajeLocal(message);
                break;

            default:
                result = "Modelo de chatbot no reconocido.";
        }

        ChatResponse response = new ChatResponse();
        response.setResult(result);
        return response;
    }

    private String mensajeOpenAI(String message){

        Prompt prompt = new Prompt(message);
        return openAIChatModel.call(prompt).getResult().getOutput().getText();

    }

    private String mensajeDeepseek(String message) {

        String escapedMessage = message.replace("\"", "\\\"");

        String body = """
                {
                    "model": "deepseek-chat",
                    "messages": [
                        {
                            "role": "system",
                            "content": "Eres un asistente una app de gastos llamada BudgetPartner"
                        },
                        {
                            "role": "user",
                            "content": "%s"
                        }
                    ], "stream": false
                }
                """.formatted(escapedMessage);


        //Preparar y enviar la petición a deepsee
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
            return responseBody;

        }
        catch(Exception e){
            logger.info("Base de datos limpiada con éxito.");
            throw  new RuntimeException(e);}



        /*
        if(!responseBody.isBlank()){

            ObjectMapper mapper = new ObjectMapper();
            Object json = mapper.readValue(responseBody, Object.class);
            String prettyJson = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(json);
            System.out.println(prettyJson);
        }*/

    }

    public String mensajeLocal(String message){
        //return this.localChatbotService.call(message);
        OllamaChatModel model = OllamaChatModel.builder()
                .baseUrl("http://localhost:11434")
                .modelName(intermediateChatbotService)
                .build();
        return model.chat(message);
    }

    public String obtenerInformacionQuey(String message, String nombreModelo){
        String peticion = """
        Analiza el siguiente mensaje de un usuario. Devuelve un JSON con:

        - `operacion`: uno de ["Create", "Read", "Update", "Delete", "indeterminado"]
        - `entidades`: una lista con los nombres de entidades en singular (como Usuario, Producto, Pedido, etc.)


        Mensaje del usuario:

        %s
        """.formatted(message);

        //- `atributos`: opcional, una lista de posibles campos mencionados (como "nombre", "precio", "email")
        //        - `identificadores`: opcional, una lista de valores únicos detectados (como ID numérico, email, username, etc.)

        OllamaChatModel model = OllamaChatModel.builder()
                .baseUrl("http://localhost:11434")
                .modelName(nombreModelo)
                .build();
        return model.chat(peticion);
        }
}
