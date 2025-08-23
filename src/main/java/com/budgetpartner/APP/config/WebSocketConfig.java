package com.budgetpartner.APP.config;


import com.budgetpartner.APP.util.MessageSocket;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.security.config.annotation.web.socket.EnableWebSocketSecurity;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry){
        registry.enableSimpleBroker("/topic");
        registry.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry){
        registry.addEndpoint("/plan-chat")
                .setAllowedOriginPatterns("*")  // Ajusta para seguridad CORS en producción
                //.withSockJS() // Habilita fallback SockJS si WebSocket no está disponible
;
    }
}

/*
/plan-chat	Conexión WebSocket inicial (handshake)
/app/plan/{id}/chat	Enviar mensajes desde cliente al servidor (entrada)
/topic/plan/{id}/chat	Suscripción para recibir mensajes desde servidor (salida)

Endpoint /plan-chat es la antena donde los oyentes se conectan para sintonizar.

Prefijo /app es la cabina del DJ, donde las canciones (mensajes) entran para ser transmitidas.

Broker /topic es la señal de radio que se transmite a todos los oyentes suscritos.
 */
