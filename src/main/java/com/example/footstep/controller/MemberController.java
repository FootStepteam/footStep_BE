package com.example.footstep.controller;

import com.example.footstep.component.security.AuthTokens;
import com.example.footstep.component.security.AuthTokensGenerator;
import com.example.footstep.domain.dto.LoginDto;
import com.example.footstep.domain.dto.member.MemberDto;
import com.example.footstep.domain.form.MemberForm;
import com.example.footstep.domain.entity.Member;
import com.example.footstep.domain.repository.MemberRepository;

import java.util.List;

import com.example.footstep.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberController {

    private final MemberService memberService;

    private final MemberRepository memberRepository;
    private final AuthTokensGenerator authTokensGenerator;

    @GetMapping
    public ResponseEntity<List<Member>> getAllMember() {
        return ResponseEntity.ok(memberRepository.findAll());
    }

    @GetMapping("/{accessToken}") // 엑세스 토큰 확인용
    public ResponseEntity<MemberDto> findByAccessToken(@PathVariable String accessToken) {
        Long memberId = authTokensGenerator.extractMemberId(accessToken);
        return ResponseEntity.ok( memberService.getMemberWithSareRoom(memberId));
    }

    @PostMapping("/sign-up")
    public ResponseEntity<String> signUpMember(@RequestBody @Valid MemberForm memberForm) {
        return ResponseEntity.ok(memberService.memberSignup(memberForm));
    }

    @PostMapping("/sign-in")
    public ResponseEntity<AuthTokens> signInMember(@RequestBody @Valid LoginDto loginDto) {
        AuthTokens tokens = memberService.login(loginDto);

        return ResponseEntity.ok(tokens);
    }
    // 이메일 존재 여부 컨트롤러
    @PostMapping("/is-email")
    public ResponseEntity<Boolean> getEmailOne(@RequestParam String email){
        return ResponseEntity.ok(memberService.isEmailExist(email));
    }
}
