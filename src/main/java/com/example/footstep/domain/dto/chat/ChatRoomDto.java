package com.example.footstep.domain.dto.chat;


import lombok.Getter;

import java.util.UUID;

@Getter

public class ChatRoomDto {
    private String roomId;
    private String name;
    public static ChatRoomDto create(String name) {
        ChatRoomDto chatRoom = new ChatRoomDto();
        chatRoom.roomId = UUID.randomUUID().toString();
        chatRoom.name = name;
        return chatRoom;
    }
}
