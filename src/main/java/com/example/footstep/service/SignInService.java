package com.example.footstep.service;

import com.example.footstep.component.security.AuthTokens;
import com.example.footstep.component.security.AuthTokensGenerator;
import com.example.footstep.domain.dto.LoginDto;
import com.example.footstep.domain.entity.Member;
import com.example.footstep.domain.repository.MemberRepository;
import com.example.footstep.exception.ErrorCode;
import com.example.footstep.exception.GlobalException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SignInService{

    private final AuthTokensGenerator authTokensGenerator;
    private final MemberRepository memberRepository;

    private final PasswordEncoder passwordEncoder;

    @Transactional
    public AuthTokens login(LoginDto loginDto) {
        Member member = memberRepository.findByLoginEmail(loginDto.getLoginEmail())
                .orElseThrow(() -> new GlobalException(ErrorCode.NOT_FIND_MEMBER_ID));

        if(!passwordEncoder.matches(loginDto.getPassword(), member.getPassword())){
            throw new GlobalException(ErrorCode.WRONG_MEMBER_PASSWORD);
        };

        return authTokensGenerator.generate(member.getMemberId());
    }
}

