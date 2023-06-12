package com.example.footstep.controller;

import com.example.footstep.component.security.AuthTokens;
import com.example.footstep.component.security.AuthTokensGenerator;
import com.example.footstep.domain.dto.LoginDto;
import com.example.footstep.domain.form.MemberForm;
import com.example.footstep.domain.entity.Member;
import com.example.footstep.domain.repository.MemberRepository;
import com.example.footstep.service.SignInService;
import com.example.footstep.service.SignUpService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberController {

    private final SignInService signInService;
    private final SignUpService signUpService;

    private final MemberRepository memberRepository;
    private final AuthTokensGenerator authTokensGenerator;

    @GetMapping
    public ResponseEntity<List<Member>> getAllMember() {
        return ResponseEntity.ok(memberRepository.findAll());
    }

    @GetMapping("/{accessToken}") // 엑세스 토큰 확인용
    public ResponseEntity<Member> findByAccessToken(@PathVariable String accessToken) {
        Long memberId = authTokensGenerator.extractMemberId(accessToken);
        return ResponseEntity.ok(memberRepository.findById(memberId).get());
    }

    @PostMapping("/sign-up")
    public ResponseEntity<String> signUpMember(@RequestBody @Valid MemberForm memberForm) {
        return ResponseEntity.ok(signUpService.memberSignup(memberForm));
    }

    @PostMapping("/sign-in")
    public ResponseEntity<AuthTokens> signInMember(@RequestBody @Valid LoginDto loginDto) {
        AuthTokens tokens = signInService.login(loginDto);

        return ResponseEntity.ok(tokens);
    }

}
