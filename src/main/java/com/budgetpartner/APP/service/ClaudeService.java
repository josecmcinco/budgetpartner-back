package com.budgetpartner.APP.service;
/*
import com.budgetpartner.APP.config.OpenAiConfig;
import com.budgetpartner.APP.dto.api.ChatQuery;
import com.budgetpartner.APP.dto.api.ChatResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ClaudeService {

    @Autowired
    private OpenAiConfig openAiConfig;

    private String apiKey = openAiConfig.getApiKey();

    private static final String CLAUDE_URL = "https://api.anthropic.com/v1/messages";

    private final RestTemplate restTemplate = new RestTemplate();

    public ChatResponse enviarMensaje(ChatQuery querry) {

        String prompt = querry.getPrompt();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("x-api-key", apiKey);
        headers.set("anthropic-version", "2023-06-01");

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", "claude-3-opus-20240229");
        requestBody.put("max_tokens", 1000);
        requestBody.put("messages", List.of(
                Map.of("role", "user", "content", prompt)
        ));

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<Map> response = restTemplate.postForEntity(CLAUDE_URL, entity, Map.class);

        Map<String, Object> message = (Map<String, Object>) ((List<?>) response.getBody().get("content")).get(0);
        return new ChatResponse((String) message.get("text")) ;
    }
}*/
