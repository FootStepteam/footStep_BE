package com.example.footstep.controller;

import com.example.footstep.component.security.AuthTokens;
import com.example.footstep.component.security.AuthTokensGenerator;
import com.example.footstep.component.security.CurrentMember;
import com.example.footstep.component.security.LoginMember;
import com.example.footstep.domain.form.ChangePasswordForm;
import com.example.footstep.domain.dto.LoginDto;
import com.example.footstep.domain.dto.member.MemberDto;
import com.example.footstep.domain.dto.member.MemberProfileDto;
import com.example.footstep.domain.dto.member.MemberUpdateDto;
import com.example.footstep.domain.form.MemberForm;
import com.example.footstep.domain.form.MemberUpdateForm;
import com.example.footstep.service.MemberService;
import com.example.footstep.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberController {

    private final MemberService memberService;

    private final AuthTokensGenerator authTokensGenerator;
    private final TokenService tokenService;

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
        tokenService.saveRefreshToken(tokens.getRefreshToken(), tokens.getJwtAccessToken());
        return ResponseEntity.ok(tokens);
    }
    // 이메일 존재 여부 컨트롤러
    @PostMapping("/is-email")
    public ResponseEntity<Boolean> getEmailOne(@RequestParam String email){
        return ResponseEntity.ok(memberService.isEmailExist(email));
    }

    @GetMapping("/profile")
    public ResponseEntity<MemberProfileDto> getProfile(
        @LoginMember CurrentMember loginMember) {

        return ResponseEntity.ok(
            MemberProfileDto.from(memberService.getProfile(loginMember.getMemberId()))
        );
    }

    @PutMapping("")
    public ResponseEntity<MemberUpdateDto> updateMemberProfile(
        @LoginMember CurrentMember loginMember,
        @RequestBody MemberUpdateForm memberUpdateForm) {
        MemberUpdateDto updateDto = MemberUpdateDto.from(
            memberService.update(loginMember.getMemberId(), memberUpdateForm));
        return ResponseEntity.ok(updateDto);
    }

    @PostMapping("/password")
    public void changePassword(
        @LoginMember CurrentMember loginMember,
        @RequestBody ChangePasswordForm changePasswordForm) {

        memberService.changePassword(loginMember.getMemberId(), changePasswordForm.getPassword());

    }

}
