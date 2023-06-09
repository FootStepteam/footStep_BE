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

/*
**
파  일  명 : ShareRoomService
제작  연도 : 2023.06.07
작  성  자 : 오동건
개발 HISTORY
DATE                  AUTHOR                DESCRIPTION
------------------+--------------------+--------------------------------------------
2023.06.07            오동건                 개발
------------------+--------------------+--------------------------------------------
*/
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ShareRoomController {

    private final ShareRoomService shareRoomService;


    // 공유방 리스트 조회
    @GetMapping("/share-room")
    public ResponseEntity<List<ShareRoomListDto>> searchListShareRoom(
//        @AuthenticationPrincipal Long memberId,
        @RequestBody @Valid ShareRoomPageForm shareRoomPageForm) {

        return ResponseEntity.ok(shareRoomService.searchListShareRoom(1L, shareRoomPageForm));
    }


    // 공유방 조회
    @GetMapping("/share-room/{shareId}")
    public ResponseEntity<ShareRoomDto> searchShareRoom(
//        @AuthenticationPrincipal Long memberId,
        @PathVariable("shareId") Long shareId) {

        return ResponseEntity.ok(shareRoomService.searchShareRoom(1L, shareId));
    }


    // 공유방 생성
    @PostMapping("/share-room")
    public ResponseEntity<ShareRoomDto> addShareRoom(
//        @AuthenticationPrincipal Long memberId,
        @RequestBody @Valid ShareRoomForm shareRoomForm) {

        return ResponseEntity.ok(shareRoomService.addShareRoom(1L, shareRoomForm));
    }


    // 공유방 수정
    @PutMapping("/share-room/{shareId}")
    public ResponseEntity<ShareRoomDto> modifyShareRoom(
//        @AuthenticationPrincipal Long memberId,
        @PathVariable("shareId") Long shareId,
        @RequestBody @Valid ShareRoomForm shareRoomForm) {

        return ResponseEntity.ok(shareRoomService.modifyShareRoom(
            1L, shareId, shareRoomForm));
    }


    // 공유방 삭제
    @DeleteMapping("/share-room/{shareId}")
    public ResponseEntity<String> deleteShareRoom(
//        @AuthenticationPrincipal Long memberId,
        @PathVariable("shareId") Long shareId) {

        return ResponseEntity.ok(shareRoomService.deleteShareRoom(1L, shareId));
    }
}
