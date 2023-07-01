package com.example.footstep.domain.dto.chat;

import com.example.footstep.domain.entity.Message;
import com.example.footstep.domain.entity.ShareRoomEnter;
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
            .shareRoomEnterId(message.getShareRoomEnter().getShareRoomEnterId())
            .shareId(message.getShareRoomEnter().getShareRoom().getShareId())
            .nickName(message.getShareRoomEnter().getShareRoom().getMember().getNickname())
            .message(message.getMessage())
            .build();
    }
}
