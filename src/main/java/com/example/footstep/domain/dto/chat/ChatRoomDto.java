package com.example.footstep.domain.dto.chat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatRoomDto{
    private String roomId;
    private String name;

    public static ChatRoomDto create(String name,Long shareRoomId) {
        ChatRoomDto chatRoom = new ChatRoomDto();
        chatRoom.roomId = String.valueOf(shareRoomId);
        chatRoom.name = name;
        return chatRoom;
    }
}
