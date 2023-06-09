package com.example.footstep.controller;

import com.example.footstep.component.jwt.AuthTokens;
import com.example.footstep.component.jwt.AuthTokensGenerator;
import com.example.footstep.domain.dto.LoginDto;
import com.example.footstep.domain.dto.MemberDto;
import com.example.footstep.domain.entity.Member;
import com.example.footstep.domain.repository.MemberRepository;
import com.example.footstep.service.SignInService;
import com.example.footstep.service.impl.SignUpServiceimpl;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<String> signUpMember(@RequestBody MemberDto memberDto) {
        return ResponseEntity.ok(signUpService.memberSignup(memberDto));
    }

    @PostMapping("/sign-in")
    public ResponseEntity<AuthTokens> signInMember(@RequestBody LoginDto loginDto) {
        AuthTokens tokens = signInService.login(loginDto);

        return ResponseEntity.ok(tokens);
    }

}
