package com.budgetpartner.APP.controller;

import com.budgetpartner.APP.dto.api.ChatbotQuery;
import com.budgetpartner.APP.dto.api.ChatResponse;

import com.budgetpartner.APP.service.AiService.DeepseekAgentService;
import com.budgetpartner.APP.service.AiService.OllamaAgentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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

    @Operation(
            summary = "Procesar mensaje con IA",
            description = "Procesa un mensaje enviado por el usuario utilizando el modelo de IA especificado. Los modelos disponibles son: `openai`, `deepseek`, `gemma3:12b` y `qwen3:4b`.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Respuesta generada correctamente por el modelo de IA"
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Parámetros inválidos o modelo no reconocido"
                    )
            }
    )
    @PostMapping("/{model}")
    public ResponseEntity<ChatResponse> processMessageAI(@PathVariable String model, @RequestBody ChatbotQuery query) {
        String result = "";
        switch (model.toLowerCase()) {
            case "openai":
                result = "Modelo de chatbot no implementado.";
                break;

            case "deepseek":
                result = deepseekAgentService.processUserMessage(query);
                break;

            case "gemma3:12b":
                result = ollamaAgentService.processUserMessage(query, "gemma3:12b");
                break;
            case "qwen3:4b":
                result = ollamaAgentService.processUserMessage(query, "qwen3:4b");
                break;
            default:
                result = "Modelo de chatbot no reconocido.";
        }
        ChatResponse response = new ChatResponse(result);
        return ResponseEntity.ok(response);
    }
}