package com.example.footstep.controller;

import com.example.footstep.model.dto.chat.MessageDto;
import com.example.footstep.model.dto.schedule.DayScheduleDto;
import com.example.footstep.model.dto.schedule.DestinationSocketDto;
import com.example.footstep.model.dto.share_room.ShareRoomDto;
import com.example.footstep.model.dto.share_room.ShareRoomSocketDto;
import com.example.footstep.service.MessageService;
import com.example.footstep.service.ScheduleService;
import com.example.footstep.service.ShareRoomService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MessageController {

    private final ShareRoomService shareRoomService;
    private final ScheduleService scheduleService;
    private final MessageService messageService;
    private final SimpMessageSendingOperations simpMessageSendingOperations;


    @MessageMapping("/chat/message")
    public void enter(MessageDto messageDto) {

        if (MessageDto.MessageType.ENTER.equals(messageDto.getType())) {
            messageDto.setMessage(messageDto.getNickName() + "님이 입장하였습니다.");
        }

        simpMessageSendingOperations.convertAndSend(
            "/sub/share-room/" + messageDto.getShareId(), messageDto);

        messageService.createMessage(messageDto);
    }


    @MessageMapping("share-room/{shareId}")
    public void shareRoom(@DestinationVariable("shareId") Long shareId) {

        ShareRoomDto shareRoomDto = shareRoomService.getOneShareRoomMessage(shareId);

        simpMessageSendingOperations.convertAndSend(
            "/sub/share-room/" + shareId + "/destination", ShareRoomSocketDto.from(shareRoomDto));
    }


    @MessageMapping("share-room/{shareId}/destination")
    public void destination(@DestinationVariable("shareId") Long shareId) {

        List<DayScheduleDto> dayScheduleDto = scheduleService.getAllListSchedule(shareId);

        simpMessageSendingOperations.convertAndSend(
            "/sub/share-room/" + shareId + "/destination", DestinationSocketDto.from(dayScheduleDto));
    }
}