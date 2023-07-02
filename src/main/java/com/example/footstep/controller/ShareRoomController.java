package com.example.footstep.controller;

import com.example.footstep.authentication.oauth.tour.TourApiClient;
import com.example.footstep.component.security.CurrentMember;
import com.example.footstep.component.security.LoginMember;
import com.example.footstep.model.dto.share_room.RecommendDto;
import com.example.footstep.model.dto.share_room.ShareRoomDto;
import com.example.footstep.model.form.ShareRoomForm;
import com.example.footstep.service.ShareRoomService;
import java.io.IOException;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
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
    private final TourApiClient tourApiService;


    @GetMapping
    public ResponseEntity<List<ShareRoomDto>> getAllListShareRoom(
        @LoginMember CurrentMember loginMember,
        @RequestParam("page") int page, @RequestParam("size") int size) {

        Pageable pageable = PageRequest.of(page, size);

        return ResponseEntity.ok(
            shareRoomService.getAllListShareRoom(loginMember.getMemberId(), pageable));
    }


    @GetMapping("/{shareId}")
    public ResponseEntity<ShareRoomDto> getOneShareRoom(
        @LoginMember CurrentMember loginMember, @PathVariable("shareId") Long shareId) {

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
        @LoginMember CurrentMember loginMember,
        @RequestBody @Valid ShareRoomForm shareRoomForm) {

        return ResponseEntity.ok(
            shareRoomService.createShareRoom(loginMember.getMemberId(), shareRoomForm));
    }


    @PutMapping("/{shareId}")
    public ResponseEntity<ShareRoomDto> updateShareRoom(
        @LoginMember CurrentMember loginMember,
        @PathVariable("shareId") Long shareId,
        @RequestBody @Valid ShareRoomForm shareRoomForm) {

        return ResponseEntity.ok(
            shareRoomService.updateShareRoom(loginMember.getMemberId(), shareId, shareRoomForm));
    }


    @DeleteMapping("/{shareId}")
    public void deleteShareRoom(
        @LoginMember CurrentMember loginMember,
        @PathVariable("shareId") Long shareId) {

        shareRoomService.deleteShareRoom(loginMember.getMemberId(), shareId);
    }


    @GetMapping("/recommend")
    public ResponseEntity<RecommendDto> getAllImageTourList(
        @RequestParam String keyword) throws IOException {
        return ResponseEntity.ok(tourApiService.searchTourImageKeyword(keyword));
    }
}
