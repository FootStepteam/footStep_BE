package com.example.footstep.service;

import com.example.footstep.component.security.AuthTokens;
import com.example.footstep.component.security.AuthTokensGenerator;
import com.example.footstep.model.dto.member.MemberDto;
import com.example.footstep.model.entity.Member;
import com.example.footstep.model.form.LoginForm;
import com.example.footstep.model.form.MemberForm;
import com.example.footstep.model.form.MemberUpdateForm;
import com.example.footstep.model.repository.MemberRepository;
import com.example.footstep.exception.ErrorCode;
import com.example.footstep.exception.GlobalException;
import java.util.Locale;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthTokensGenerator authTokensGenerator;


    @Transactional
    public String memberSignup(MemberForm memberForm) {

        if (isEmailExist(memberForm.getLoginEmail())) {
            throw new GlobalException(ErrorCode.ALREADY_MEMBER_EMAIL);
        }

        Member member = memberForm.toEntity();
        String password = passwordEncoder.encode(member.getPassword());

        member.setPassword(password);
        memberRepository.save(member);

        return "회원가입 성공";
    }


    @Transactional
    public AuthTokens login(LoginForm loginForm) {

        Member member = memberRepository.findByLoginEmail(loginForm.getLoginEmail())
            .orElseThrow(() -> new GlobalException(ErrorCode.NOT_FIND_MEMBER_ID));

        if (!passwordEncoder.matches(loginForm.getPassword(), member.getPassword())) {
            throw new GlobalException(ErrorCode.WRONG_MEMBER_PASSWORD);
        }

        return authTokensGenerator.generate(member.getMemberId());
    }


    public boolean isEmailExist(String email) {

        return memberRepository.findByLoginEmail(email.toLowerCase(Locale.ROOT)).isPresent();
    }
    public boolean isNickNameExist(String nickname){
        return memberRepository.findByNickname(nickname).isPresent();
    }


    public MemberDto getMemberWithSareRoom(Long memberId) {

        Member member = memberRepository.findByMemberId(memberId).get();
        return MemberDto.from(member);
    }


    @Transactional(readOnly = true)
    public Member getProfile(Long memberId) {

        return memberRepository.getMemberById(memberId);
    }


    @Transactional
    public Member update(Long memberId, MemberUpdateForm memberUpdateForm) {

        Member member = memberRepository.getMemberById(memberId);
        member.updateProfile(
            memberUpdateForm.getNickname(),
            memberUpdateForm.getProfileUrl(),
            memberUpdateForm.getDescription()
        );

        return member;
    }


    @Transactional
    public void changePassword(Long memberId, String password) {

        Member member = memberRepository.getMemberById(memberId);
        String encodedPassword = passwordEncoder.encode(password);

        if (passwordEncoder.matches(member.getPassword(), encodedPassword)) {
            throw new GlobalException(ErrorCode.NOT_CHANGED_PASSWORD);
        }

        member.changePassword(encodedPassword);
    }
}
