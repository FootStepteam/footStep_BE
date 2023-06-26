package com.example.footstep.domain.repository;

import com.example.footstep.domain.dto.chat.ChatRoomDto;
import com.example.footstep.domain.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRepository extends JpaRepository<ChatRoom,String> {
    ChatRoom findChatRoomDtoByRoomId(Long roomId);
}
