package com.example.footstep.controller;

import com.example.footstep.model.dto.chat.MessageDto;
import com.example.footstep.model.dto.schedule.DayScheduleDto;
import com.example.footstep.model.dto.schedule.DestinationDto;
import com.example.footstep.model.form.DestinationForm;
import com.example.footstep.service.DestinationService;
import com.example.footstep.service.MessageService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
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
        @DestinationVariable("shareId") Long shareId, DestinationForm destinationForm) {

        log.error("error!!!!!!!");
        log.info(
            "------------------------------------------------- 메시지 시작 --------------------------------------------------");

        log.info(
            "------------------------------------------------- destinationForm --------------------------------------------------");

        log.info("여행일자 : " + destinationForm.getPlanDate());
        log.info("목적지코드 : " + destinationForm.getDestinationCategoryCode());
        log.info("목적지명 : " + destinationForm.getDestinationName());
        log.info("목적지주소 : " + destinationForm.getDestinationAddress());
        log.info("경도 : " + destinationForm.getLng());
        log.info("위도 : " + destinationForm.getLat());
        log.info("순서 : " + destinationForm.getSeq());

        DestinationDto destinationDto = destinationService.createDestination(shareId,
            destinationForm);

        simpMessageSendingOperations.convertAndSend(
            "/sub/share-room/" + shareId + "/destination", destinationDto);

        log.info(
            "------------------------------------------------- 메시지 종료 --------------------------------------------------");
        log.error("error!!!!!!!");
    }


    @MessageMapping("/{shareId}/destination/{destinationId}")
    public ResponseEntity<List<DayScheduleDto>> deleteDestination(
        @DestinationVariable("shareId") Long shareId, @DestinationVariable("destinationId") Long destinationId) {

        List<DayScheduleDto> dayScheduleDto =
            destinationService.deleteDestinationMessage(shareId, destinationId);

        simpMessageSendingOperations.convertAndSend(
            "/sub/share-room/" + shareId + "/destination", dayScheduleDto);

        return ResponseEntity.ok(dayScheduleDto);
    }
}