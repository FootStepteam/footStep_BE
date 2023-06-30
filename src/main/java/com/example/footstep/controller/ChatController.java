package com.example.footstep.controller;


import com.example.footstep.domain.dto.chat.ChatMessageDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ChatController {
    private final SimpMessageSendingOperations messagingTemplate;
    @MessageMapping("/chat/enter")
    @SendTo("/sub/public")
    public ChatMessageDto register(@Payload ChatMessageDto chatMessage, SimpMessageHeaderAccessor headerAccessor) {
        headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
        //headerAccessor.getSessionAttributes().put("roomId", 1L);
        return chatMessage;
    }
    @MessageMapping("/chat/message")
    @SendTo("/sub/public")
    public ChatMessageDto sendMessage(@Payload ChatMessageDto chatMessage) {
        return chatMessage;
    }
}
