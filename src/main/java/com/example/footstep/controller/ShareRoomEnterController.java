package com.example.footstep.controller;

import com.example.footstep.component.security.LoginMember;
import com.example.footstep.domain.dto.chat.ShareRoomEnterDto;
import com.example.footstep.service.ShareRoomEnterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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


    @GetMapping("/chat/{shareRoomEnterId}")
    public ResponseEntity<ShareRoomEnterDto> getOneShareRoomEnter(
        @PathVariable("shareRoomEnterId") Long shareRoomEnterId) {

        return ResponseEntity.ok(shareRoomEnterService.getOneShareRoomEnter(shareRoomEnterId));
    }


    @PostMapping("/{shareId}/enter")
    public ResponseEntity<ShareRoomEnterDto> createShareRoomEnter(
        @AuthenticationPrincipal LoginMember loginMember, @PathVariable("shareId") Long shareId) {

        return ResponseEntity.ok(
            shareRoomEnterService.createShareRoomEnter(loginMember.getMemberId(), shareId));
    }
}
