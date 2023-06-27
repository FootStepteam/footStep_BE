package com.example.footstep.domain.dto.chat;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ChatMessageDto {
    public enum MessageType{
        ENTER , TALK
    }
    private MessageType type;
    private String roomId;
    private String sender;
    private String message;
}
