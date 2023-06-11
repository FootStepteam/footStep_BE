package com.example.footstep.controller;

import com.example.footstep.authentication.oauth.kakao.KakaoLoginParams;
import com.example.footstep.component.security.AuthTokens;
import com.example.footstep.service.KakaoService;
import io.swagger.models.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AuthController {

    private final KakaoService kakaoService;

    //https://kauth.kakao.com/oauth/authorize?client_id=361fc4d12b75888a392207252d5db496&redirect_uri=http://localhost:8080/api/kakao/callback&response_type=code
    //https://kauth.kakao.com/oauth/logout?client_id=361fc4d12b75888a392207252d5db496&logout_redirect_uri=http://localhost:8080/api/kakao/logout
    @PostMapping("/auth/kakao")
    public ResponseEntity<AuthTokens> loginKakao(@RequestBody KakaoLoginParams params) {
        return ResponseEntity.ok(kakaoService.login(params));
    }

    @ResponseBody
    @GetMapping("/kakao/callback")
    public ResponseEntity<String> kakaoCallback(@RequestParam String code) {
        return ResponseEntity.ok(code);
    }

    @PostMapping("/kakao/unlink")
    public ResponseEntity<String> kakaoUnlink(@RequestBody  KakaoLoginParams params){
        kakaoService.kakaoUnlink(params);
        return ResponseEntity.ok("회원 탈퇴 성공");
    }
    @GetMapping("/kakao/logout")
    public ResponseEntity<String> kakaoLogout(){
        return ResponseEntity.ok("로그아웃");
    }
}
