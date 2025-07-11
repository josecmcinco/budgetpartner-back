package com.budgetpartner.APP.service;

import com.budgetpartner.APP.dto.api.ChatQuery;
import com.budgetpartner.APP.dto.api.ChatResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.ollama.OllamaChatModel;
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

@Service
public class ChatbotService {

    @Autowired
    private OpenAiChatModel openAIChatModel;
    @Autowired
    private OllamaChatModel localChatbotService;


    private static final String DEEPSEEK_API_KEY = System.getProperty("DEEPSEEK_API_KEY");
    private static final String BASE_URL_DEEPSEEK = "https://api.deepseek.com/v1";


    public ChatResponse procesar(String model, ChatQuery query) {
        String message = query.getPrompt();
        String result = "";
        switch (model.toLowerCase()) {
            case "openai":
                result = mensajeOpenAI(message);
                break;

            case "deepseek":

                try {
                    result = mensajeDeepseek(message);}
                catch(Exception e){
                    //System.out.println("Error: " + e);
                    //TODO
                }
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

    private String mensajeDeepseek(String message) throws IOException, InterruptedException {

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


        var request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL_DEEPSEEK + "/chat/completions"))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + DEEPSEEK_API_KEY)
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .build();
        var cliente = HttpClient.newHttpClient();
        var response = cliente.send(request, HttpResponse.BodyHandlers.ofString());
        String responseBody = response.body();


        /*
        if(!responseBody.isBlank()){

            ObjectMapper mapper = new ObjectMapper();
            Object json = mapper.readValue(responseBody, Object.class);
            String prettyJson = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(json);
            System.out.println(prettyJson);
        }*/


        return responseBody;
    }

    public String mensajeLocal(String message){

        return this.localChatbotService.call(message);
    }
}
