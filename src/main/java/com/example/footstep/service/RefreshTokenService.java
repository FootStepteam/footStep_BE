package com.example.footstep.service;

import com.example.footstep.model.entity.RefreshToken;
import com.example.footstep.model.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    public RefreshToken findToken(String refreshToken) {

        return refreshTokenRepository.findByTokenValueWithMember(refreshToken)
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 토큰입니다."));
    }
}
