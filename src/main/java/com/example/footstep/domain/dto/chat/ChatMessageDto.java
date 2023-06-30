package com.example.footstep.domain.dto.chat;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ChatMessageDto {
    private Long roomId;
    private String message;
    private String sender;
    private MessageType type;

    public enum MessageType {
        CHAT, LEAVE, JOIN
    }
}

