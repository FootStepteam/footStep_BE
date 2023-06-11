package com.example.footstep.controller;

import com.example.footstep.domain.dto.ShareRoomDto;
import com.example.footstep.domain.dto.ShareRoomListDto;
import com.example.footstep.domain.form.ShareRoomForm;
import com.example.footstep.domain.form.ShareRoomPageForm;
import com.example.footstep.service.ShareRoomService;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/share-room")
public class ShareRoomController {

    private final ShareRoomService shareRoomService;


    @GetMapping
    public ResponseEntity<List<ShareRoomListDto>> searchListShareRoom(
//        @AuthenticationPrincipal Long memberId,
        @RequestBody @Valid ShareRoomPageForm shareRoomPageForm) {

        return ResponseEntity.ok(shareRoomService.searchListShareRoom(1L, shareRoomPageForm));
    }


    @GetMapping("/{shareId}")
    public ResponseEntity<ShareRoomDto> searchShareRoom(
//        @AuthenticationPrincipal Long memberId,
        @PathVariable("shareId") Long shareId) {

        return ResponseEntity.ok(shareRoomService.searchShareRoom(1L, shareId));
    }


    @PostMapping
    public ResponseEntity<ShareRoomDto> addShareRoom(
//        @AuthenticationPrincipal Long memberId,
        @RequestBody @Valid ShareRoomForm shareRoomForm) {

        return ResponseEntity.ok(shareRoomService.addShareRoom(1L, shareRoomForm));
    }


    @PutMapping("/{shareId}")
    public ResponseEntity<ShareRoomDto> modifyShareRoom(
//        @AuthenticationPrincipal Long memberId,
        @PathVariable("shareId") Long shareId,
        @RequestBody @Valid ShareRoomForm shareRoomForm) {

        return ResponseEntity.ok(shareRoomService.modifyShareRoom(
            1L, shareId, shareRoomForm));
    }


    @DeleteMapping("/{shareId}")
    public ResponseEntity<String> deleteShareRoom(
//        @AuthenticationPrincipal Long memberId,
        @PathVariable("shareId") Long shareId) {

        return ResponseEntity.ok(shareRoomService.deleteShareRoom(1L, shareId));
    }
}
