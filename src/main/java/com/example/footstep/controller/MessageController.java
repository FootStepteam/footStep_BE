package com.example.footstep.controller;

import com.example.footstep.model.dto.chat.MessageDto;
import com.example.footstep.model.dto.schedule.DayScheduleDto;
import com.example.footstep.model.dto.schedule.DestinationDto;
import com.example.footstep.service.DestinationService;
import com.example.footstep.service.MessageService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;
    private final DestinationService destinationService;
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


    @MessageMapping("/{shareId}/destination")
    public void createDestination(
        @DestinationVariable("shareId") Long shareId, DestinationDto destinationDto) {

        simpMessageSendingOperations.convertAndSend(
            "/sub/share-room/" + shareId + "/destination", destinationDto);
    }


    @MessageMapping("/{shareId}/destination/{destinationId}")
    public ResponseEntity<List<DayScheduleDto>> deleteDestination(
        @DestinationVariable("shareId") Long shareId,
        @DestinationVariable("destinationId") Long destinationId) {

        List<DayScheduleDto> dayScheduleDto =
            destinationService.deleteDestinationMessage(shareId, destinationId);

        simpMessageSendingOperations.convertAndSend(
            "/sub/share-room/" + shareId + "/destination", dayScheduleDto);

        return ResponseEntity.ok(dayScheduleDto);
    }
}