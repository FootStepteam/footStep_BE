package com.example.footstep.controller;

import com.example.footstep.authentication.oauth.kakao.KakaoLoginParams;
import com.example.footstep.service.KakaoService;
import com.example.footstep.service.UploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class FileUploadController {

    private final UploadService uploadService;
    private final KakaoService kakaoService;


    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(
        @RequestParam("file") MultipartFile file, @RequestParam("shareRoomId") Long shareRoomId) {

        return ResponseEntity.ok(uploadService.uploadS3File(file, shareRoomId));
    }
    @PostMapping("/sendme/{shareRoomId}")
    public ResponseEntity<String> sendMeKakaoImage(
        @RequestBody KakaoLoginParams kakaoAccessCode, @PathVariable Long shareRoomId) {

        return ResponseEntity.ok(kakaoService.sendMeKakaoImage(kakaoAccessCode, shareRoomId));
    }
}