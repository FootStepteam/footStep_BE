package com.example.footstep.controller;

import com.example.footstep.component.security.AuthTokens;
import com.example.footstep.component.security.AuthTokensGenerator;
import com.example.footstep.component.security.CurrentMember;
import com.example.footstep.component.security.LoginMember;
import com.example.footstep.model.dto.member.MemberDto;
import com.example.footstep.model.dto.member.MemberProfileDto;
import com.example.footstep.model.dto.member.MemberUpdateDto;
import com.example.footstep.model.form.ChangePasswordForm;
import com.example.footstep.model.form.LoginForm;
import com.example.footstep.model.form.MemberForm;
import com.example.footstep.model.form.MemberUpdateForm;
import com.example.footstep.service.MemberService;
import com.example.footstep.service.TokenService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
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
@RequestMapping("/api/members")
public class MemberController {

    private final MemberService memberService;
    private final AuthTokensGenerator authTokensGenerator;
    private final TokenService tokenService;


    @GetMapping("/{accessToken}") // 엑세스 토큰 확인용
    public ResponseEntity<MemberDto> findByAccessToken(@PathVariable String accessToken) {

        Long memberId = authTokensGenerator.extractMemberId(accessToken);

        return ResponseEntity.ok(memberService.getMemberWithSareRoom(memberId));
    }


    @PostMapping("/sign-up")
    public ResponseEntity<String> signUpMember(@RequestBody @Valid MemberForm memberForm) {

        return ResponseEntity.ok(memberService.memberSignup(memberForm));
    }


    @PostMapping("/sign-in")
    public ResponseEntity<AuthTokens> signInMember(@RequestBody @Valid LoginForm loginForm) {

        AuthTokens tokens = memberService.login(loginForm);
        tokenService.saveRefreshToken(tokens.getRefreshToken(), tokens.getJwtAccessToken());

        return ResponseEntity.ok(tokens);
    }


    @PostMapping("/check-email")
    public ResponseEntity<Boolean> getEmailOne(@RequestParam String email) {

        return ResponseEntity.ok(memberService.isEmailExist(email));
    }
    @PostMapping("/check-nickname")
    public ResponseEntity<Boolean> getNickNameOne(@RequestParam String nickname){
        return ResponseEntity.ok(memberService.isNickNameExist(nickname));
    }


    @GetMapping("/profile")
    public ResponseEntity<MemberProfileDto> getProfile(
        @LoginMember CurrentMember loginMember) {

        return ResponseEntity.ok(
            MemberProfileDto.from(memberService.getProfile(loginMember.getMemberId()))
        );
    }


    @PutMapping
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

    @DeleteMapping
    public void deleteMember(
        @LoginMember CurrentMember currentMember) {

        memberService.delete(currentMember);

    }
}
