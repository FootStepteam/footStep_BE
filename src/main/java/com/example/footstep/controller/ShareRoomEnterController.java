package com.example.footstep.controller;

import com.example.footstep.component.security.CurrentMember;
import com.example.footstep.component.security.LoginMember;
import com.example.footstep.model.dto.chat.MessageDto;
import com.example.footstep.model.dto.chat.ShareRoomEnterDto;
import com.example.footstep.service.ShareRoomEnterService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/share-room")

public class ShareRoomEnterController {

    private final ShareRoomEnterService shareRoomEnterService;


    @GetMapping("/{shareId}/enter")
    public ResponseEntity<List<MessageDto>> getListShareRoomEnterMessage(
        @PathVariable("shareId") Long shareId) {

        return ResponseEntity.ok(shareRoomEnterService.getListShareRoomEnterMessage(shareId));
    }


    @PostMapping("/{shareId}/enter")
    public ResponseEntity<ShareRoomEnterDto> createShareRoomEnter(
        @LoginMember CurrentMember loginMember, @PathVariable("shareId") Long shareId) {

        return ResponseEntity.ok(
            shareRoomEnterService.createShareRoomEnter(loginMember.getMemberId(), shareId));
    }
}
