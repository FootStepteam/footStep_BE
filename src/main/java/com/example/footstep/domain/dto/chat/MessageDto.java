package com.example.footstep.domain.dto.chat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MessageDto {

    public enum MessageType {
        ENTER, JOIN, TALK
    }

    private MessageDto.MessageType type;
    private String shareId;
    private String nickName;
    private String message;
}
