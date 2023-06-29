package com.example.footstep.domain.dto.chat;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ChatMessageDto {
    private String content;
    private String sender;
    private MessageType type;

    public enum MessageType{
        ENTER , TALK ,JOIN
    }
}
