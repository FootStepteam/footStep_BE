package com.example.footstep.service;

import static com.example.footstep.exception.ErrorCode.NOT_FIND_SHARE_ENTER_ID;

import com.example.footstep.model.dto.chat.MessageDto;
import com.example.footstep.model.entity.Message;
import com.example.footstep.model.entity.ShareRoomEnter;
import com.example.footstep.model.repository.MessageRepository;
import com.example.footstep.model.repository.ShareRoomEnterRepository;
import com.example.footstep.exception.GlobalException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final ShareRoomEnterRepository shareRoomEnterRepository;
    private final MessageRepository messageRepository;


    @Transactional
    public void createMessage(MessageDto messageDto) {

        ShareRoomEnter shareRoomEnter =
            shareRoomEnterRepository.findByShareRoomEnterId(messageDto.getShareRoomEnterId())
                .orElseThrow(() -> new GlobalException(NOT_FIND_SHARE_ENTER_ID));

        messageRepository.save(
            Message.builder()
                .message(messageDto.getMessage())
                .shareRoomEnter(shareRoomEnter)
                .build());
    }
}
