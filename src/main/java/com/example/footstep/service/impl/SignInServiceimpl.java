package com.example.footstep.service.impl;


import com.example.footstep.component.jwt.AuthTokens;
import com.example.footstep.component.jwt.AuthTokensGenerator;
import com.example.footstep.domain.entity.Member;
import com.example.footstep.domain.entity.dto.member.LoginDto;
import com.example.footstep.domain.entity.repository.MemberRepository;
import com.example.footstep.service.SignInService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SignInServiceimpl implements SignInService {
    private final AuthTokensGenerator authTokensGenerator;
    private final MemberRepository memberRepository;


    @Override
    public AuthTokens login(LoginDto loginDto) {
        Long memberId = memberRepository.findByLoginEmail(loginDto.getLoginEmail())
                .map(Member::getMemberId)
                .orElseThrow(() -> new RuntimeException());
        return authTokensGenerator.generate(memberId);
    }
}
