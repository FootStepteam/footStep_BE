package com.example.footstep.controller;


import com.example.footstep.authentication.oauth.kakao.KakaoApiClient;
import com.example.footstep.component.jwt.AuthTokens;
import com.example.footstep.component.jwt.AuthTokensGenerator;

import com.example.footstep.domain.entity.Member;

import com.example.footstep.domain.dto.LoginDto;
import com.example.footstep.domain.dto.MemberDto;

import com.example.footstep.domain.repository.MemberRepository;
import com.example.footstep.service.SignInService;
import com.example.footstep.service.impl.SignUpServiceimpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberController {
    private final SignInService signInService;
    private final SignUpServiceimpl signUpService;

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
    @PostMapping("/sign-in")
    public ResponseEntity<AuthTokens> signInMember(@RequestBody LoginDto loginDto){
        AuthTokens tokens = signInService.login(loginDto);

        return ResponseEntity.ok(tokens);
    }

}
