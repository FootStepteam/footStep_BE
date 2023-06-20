package com.example.footstep.controller;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.example.footstep.authentication.oauth.kakao.KakaoLoginParams;
import com.example.footstep.service.KakaoService;
import com.example.footstep.service.UploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class FileUploadController {

    private final UploadService uploadService;
    private final KakaoService kakaoService;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file
            , @RequestParam("shareRoomId") Long shareRoomId) {
        return ResponseEntity.ok(uploadService.uploadFile(file,shareRoomId));
    }
//    @PostMapping("/uuids")
//    public ResponseEntity<List<String>> getFriendUUIDs(@RequestBody KakaoLoginParams kakoAccessCode) {
//
//        List<String> friendUUIDs = kakaoService.getFriendUUIDs(kakoAccessCode);
//        return ResponseEntity.ok(friendUUIDs);
//
//    }
    @PostMapping("/sendme/{shareRoomId}")
    public ResponseEntity<String> sendMeKakaoImage(@RequestBody KakaoLoginParams kakaoAccessCode,@PathVariable Long shareRoomId){
        return ResponseEntity.ok(kakaoService.sendMeKakaoImage(kakaoAccessCode,shareRoomId));
    }
}