package com.example.footstep.model.repository;

import com.example.footstep.model.entity.Message;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    List<Message> findByShareRoomEnter_ShareRoom_ShareIdOrderByCreateDate(Long shareId);
}
