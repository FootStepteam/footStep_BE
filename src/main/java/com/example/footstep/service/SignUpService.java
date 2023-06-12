package com.example.footstep.service;

import com.example.footstep.domain.form.MemberForm;
import com.example.footstep.domain.entity.Member;
import com.example.footstep.domain.repository.MemberRepository;
import com.example.footstep.exception.ErrorCode;
import com.example.footstep.exception.GlobalException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Locale;

@Service
@RequiredArgsConstructor
public class SignUpService{

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public String memberSignup(MemberForm memberForm) {
        if (isEmailExist(memberForm.getLoginEmail())) {
            throw new GlobalException(ErrorCode.ALREADY_MEMBER_EMAIL);
        }
        Member member = memberForm.from(memberForm);
        String password = passwordEncoder.encode(member.getPassword());
        member.setPassword(password);
        memberRepository.save(member);
        return "회원가입 성공";
    }
    private boolean isEmailExist(String email) {
        return memberRepository.findByLoginEmail(email.toLowerCase(Locale.ROOT)).isPresent();
    }
}