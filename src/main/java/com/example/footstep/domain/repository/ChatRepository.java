package com.example.footstep.domain.repository;

import com.example.footstep.domain.dto.chat.ChatRoomDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRepository extends JpaRepository<ChatRoomDto ,String> {
    ChatRoomDto findChatRoomDtoByRoomId(String roomId);
}
