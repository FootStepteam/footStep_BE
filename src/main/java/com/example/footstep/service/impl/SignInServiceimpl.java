package com.example.footstep.service.impl;


import com.example.footstep.component.jwt.AuthTokens;
import com.example.footstep.component.jwt.AuthTokensGenerator;
import com.example.footstep.domain.entity.Member;
import com.example.footstep.domain.dto.LoginDto;
import com.example.footstep.domain.repository.MemberRepository;
import com.example.footstep.service.SignInService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
