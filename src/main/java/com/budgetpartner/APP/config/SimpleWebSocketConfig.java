package com.budgetpartner.APP.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.*;
import org.springframework.web.socket.config.annotation.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
/*
@Configuration
@EnableWebSocket
public class SimpleWebSocketConfig implements WebSocketConfigurer {

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(new SimpleChatHandler(), "/simple-chat")
                .setAllowedOriginPatterns("*");
    }

    private static class SimpleChatHandler extends TextWebSocketHandler {
        private final Set<WebSocketSession> sessions = ConcurrentHashMap.newKeySet();

        @Override
        public void afterConnectionEstablished(WebSocketSession session) throws Exception {
            sessions.add(session);
        }

        @Override
        protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
            // Broadcast a todos los clientes conectados
            for (WebSocketSession ws : sessions) {
                if (ws.isOpen()) {
                    ws.sendMessage(message);
                }
            }
        }

        @Override
        public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
            sessions.remove(session);
        }
    }
}*/
