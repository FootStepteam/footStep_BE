package com.example.footstep.controller;


import com.example.footstep.component.jwt.AuthTokensGenerator;

import com.example.footstep.domain.entity.Member;

import com.example.footstep.domain.entity.dto.member.MemberDto;
import com.example.footstep.domain.entity.repository.MemberRepository;
import com.example.footstep.service.KakaoService;
import com.example.footstep.service.SignUpService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberController {
    private final KakaoService kakaoService;
    private final SignUpService signUpService;
//    @GetMapping("/kakao")
//    public ResponseEntity<Member> kakaoCallback(@RequestParam String code, HttpServletRequest response) throws IOException {
//        //https://kauth.kakao.com/oauth/authorize?client_id=361fc4d12b75888a392207252d5db496&redirect_uri=http://localhost:8080/api/kakao&response_type=code"
//        //System.out.println(code);
//        //String access_Token = kakaoService.getKaKaoAccessToken(code);
//        //kakaoService.createKakaoUser(access_Token);
//        return ResponseEntity.ok(kakaoService.kakaoLogin(code , response));
//
//    }
    private final MemberRepository memberRepository;
    private final AuthTokensGenerator authTokensGenerator;

    @GetMapping
    public ResponseEntity<List<Member>> findAll() {
        return ResponseEntity.ok(memberRepository.findAll());
    }

    @GetMapping("/{accessToken}")
    public ResponseEntity<Member> findByAccessToken(@PathVariable String accessToken) {
        Long memberId = authTokensGenerator.extractMemberId(accessToken);
        return ResponseEntity.ok(memberRepository.findById(memberId).get());
    }
    @PostMapping("/sign-up")
    public ResponseEntity<String> signUpMember(@RequestBody MemberDto memberDto){
        return ResponseEntity.ok(signUpService.memberSignup(memberDto));
    }
}
