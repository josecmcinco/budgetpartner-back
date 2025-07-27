package com.budgetpartner.APP.controller;

import com.budgetpartner.APP.dto.api.ChatQuery;
import com.budgetpartner.APP.dto.api.ChatResponse;

import com.budgetpartner.APP.service.AiService.DeepseekAgentService;
import com.budgetpartner.APP.service.AiService.OllamaAgentService;
import com.budgetpartner.APP.service.ChatbotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/chatbot")
public class ChatbotController {

    @Autowired
    private DeepseekAgentService deepseekAgentService;
    @Autowired
    private OllamaAgentService ollamaAgentService;


    @PostMapping("/{model}")
    public ResponseEntity<ChatResponse> processMessageAI(@PathVariable String model, @RequestBody ChatQuery query) {
        String result = "";
        switch (model.toLowerCase()) {
            case "openai":
                result = "Modelo de chatbot no reconocido.";
                break;

            case "deepseek":
                result = deepseekAgentService.processUserMessage(query);
                break;

            case "gemma3":
                result = ollamaAgentService.processUserMessage(query, "gemma3:12b");
                break;
            case "qwen3":
                result = ollamaAgentService.processUserMessage(query, "qwen3:4b");
                break;
            default:
                result = "Modelo de chatbot no reconocido.";
        }
        ChatResponse response = new ChatResponse(result);
        return ResponseEntity.ok(response);
    }
}