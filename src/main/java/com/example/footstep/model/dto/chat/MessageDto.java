package com.example.footstep.model.dto.chat;

import com.example.footstep.model.entity.Message;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageDto {

    public enum MessageType {
        ENTER, JOIN, TALK
    }

    private MessageDto.MessageType type;
    private Long shareRoomEnterId;
    private Long shareId;
    private String nickName;
    private String message;


    public static MessageDto from(Message message) {
        return MessageDto.builder()
            .shareId(message.getShareRoomEnter().getShareRoom().getShareId())
            .nickName(message.getShareRoomEnter().getMember().getNickname())
            .message(message.getMessage())
            .build();
    }
}
