package com.example.footstep.domain.dto.chat;


import lombok.Getter;

import java.util.UUID;

@Getter

public class ChatRoomDto {
    private String roomId;
    private String name;
    public static ChatRoomDto create(String name,Long shareId) {
        ChatRoomDto chatRoom = new ChatRoomDto();
        chatRoom.roomId = String.valueOf(shareId);
        chatRoom.name = name;
        return chatRoom;
    }
}
