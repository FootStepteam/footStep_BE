package com.example.footstep.controller;

import com.example.footstep.domain.dto.chat.MessageDto;
import com.example.footstep.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;
    private final SimpMessageSendingOperations simpMessageSendingOperations;


    @MessageMapping("/chat/message")
    public void enter(MessageDto messageDto) {

        if (MessageDto.MessageType.ENTER.equals(messageDto.getType())) {
            messageDto.setMessage(messageDto.getNickName() + "님이 입장하였습니다.");
        }

        simpMessageSendingOperations.convertAndSend(
            "/sub/share-room/" + messageDto.getShareId(), messageDto);

        messageService.createMessage(messageDto);
    }
}