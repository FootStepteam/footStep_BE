package com.example.footstep.controller;

import com.example.footstep.component.security.LoginMember;
import com.example.footstep.domain.dto.share_room.ShareRoomDto;
import com.example.footstep.domain.dto.share_room.ShareRoomListDto;
import com.example.footstep.domain.form.ShareRoomForm;
import com.example.footstep.service.ShareRoomService;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/share-room")
public class ShareRoomController {

    private final ShareRoomService shareRoomService;


    @GetMapping
    public ResponseEntity<List<ShareRoomListDto>> getAllListShareRoom(
        @AuthenticationPrincipal LoginMember loginMember,
        @RequestParam("page") int page, @RequestParam("size") int size) {

        return ResponseEntity.ok(
            shareRoomService.getAllListShareRoom(loginMember.getMemberId(), page, size));
    }


    @GetMapping("/{shareId}")
    public ResponseEntity<ShareRoomDto> getOneShareRoom(
        @AuthenticationPrincipal LoginMember loginMember,
        @PathVariable("shareId") Long shareId) {

        return ResponseEntity.ok(
            shareRoomService.getOneShareRoom(loginMember.getMemberId(), shareId));
    }


    @GetMapping("/find")
    public ResponseEntity<ShareRoomDto> getOneGuestShareRoom(
        @RequestParam("q") String shareCode) {

        return ResponseEntity.ok(shareRoomService.getOneGuestShareRoom(shareCode));
    }


    @PostMapping
    public ResponseEntity<ShareRoomDto> createShareRoom(
        @AuthenticationPrincipal LoginMember loginMember,
        @RequestBody @Valid ShareRoomForm shareRoomForm) {

        return ResponseEntity.ok(
            shareRoomService.createShareRoom(loginMember.getMemberId(), shareRoomForm));
    }


    @PutMapping("/{shareId}")
    public ResponseEntity<ShareRoomDto> updateShareRoom(
        @AuthenticationPrincipal LoginMember loginMember,
        @PathVariable("shareId") Long shareId,
        @RequestBody @Valid ShareRoomForm shareRoomForm) {

        return ResponseEntity.ok(shareRoomService.updateShareRoom(
            loginMember.getMemberId(), shareId, shareRoomForm));
    }


    @DeleteMapping("/{shareId}")
    public ResponseEntity<String> deleteShareRoom(
        @AuthenticationPrincipal LoginMember loginMember,
        @PathVariable("shareId") Long shareId) {

        return ResponseEntity.ok(
            shareRoomService.deleteShareRoom(loginMember.getMemberId(), shareId));
    }
}
