package com.example.footstep.controller;

import com.example.footstep.domain.dto.chat.MessageDto;
import com.example.footstep.domain.dto.chat.redis.RedisPublisher;
import com.example.footstep.service.ShareRoomEnterService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ChattingMessageController {

    private final SimpMessageSendingOperations simpMessageSendingOperations;
    private final RedisPublisher redisPublisher;
    private final ShareRoomEnterService shareRoomEnterService;

    @MessageMapping("/chat/message")
    public void enter(MessageDto messageDto) {

        if (MessageDto.MessageType.ENTER.equals(messageDto.getType())) {
            //shareRoomEnterService.enterChatRoom(messageDto.getShareId());
            shareRoomEnterService.enterChatRoom(47L);
            messageDto.setMessage(messageDto.getNickName() + "님이 입장하였습니다.");
        }

//        simpMessageSendingOperations.convertAndSend(
//            "/sub/share-room/" + messageDto.getShareId(), messageDto);
        redisPublisher.publish(shareRoomEnterService.getTopic(messageDto.getShareId()),messageDto);
    }
}
