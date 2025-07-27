package com.budgetpartner.APP.controller;

import com.budgetpartner.APP.util.Information;
import com.budgetpartner.APP.util.MessageSocket;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/plan/{numeroPlan}/chat")
public class ChatController {

    @MessageMapping()
    @SendTo("/topic/plan/{numeroPlan}/chat")
    public Information getMessage(@DestinationVariable String numeroPlan, MessageSocket messageSocket){
            System.out.println("Mensaje: "+ messageSocket);
            return new Information(messageSocket.body());
    }


}
