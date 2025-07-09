package com.budgetpartner.APP.controller;

import com.budgetpartner.APP.dto.api.ChatQuery;
import com.budgetpartner.APP.dto.api.ChatResponse;

import com.budgetpartner.APP.service.ChatbotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/chatbot")
public class ChatbotController {

    @Autowired
    private ChatbotService chatbotService;


    @PostMapping("/{model}")
    public ResponseEntity<ChatResponse> messageToTheAI(@PathVariable String model, @RequestBody ChatQuery query) {
        ChatResponse response = chatbotService.procesar(model, query);
        return ResponseEntity.ok(response);
    }
}