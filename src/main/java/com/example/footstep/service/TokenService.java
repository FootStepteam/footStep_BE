package com.example.footstep.service;

import com.example.footstep.component.jwt.JwtTokenProvider;
import com.example.footstep.model.entity.Member;
import com.example.footstep.model.entity.RefreshToken;
import com.example.footstep.model.repository.MemberRepository;
import com.example.footstep.model.repository.RefreshTokenRepository;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TokenService {

    @Value("${ACCESS_TOKEN_EXPIRE_TIME}")
    private long ACCESS_TOKEN_EXPIRE_TIME;
    private final RefreshTokenService refreshTokenService;
    private final JwtTokenProvider jwtTokenProvider;
    private final MemberRepository memberRepository;
    private final RefreshTokenRepository refreshTokenRepository;


    @Transactional
    public String reIssueAccessToken(String refreshToken) {

        RefreshToken findRefreshToken = refreshTokenService.findToken(refreshToken);

        long now = (new Date()).getTime();
        Date accessTokenExpiredAt = new Date(now + ACCESS_TOKEN_EXPIRE_TIME);

        String newAccessToken = jwtTokenProvider.generate(
            findRefreshToken.getMember().getMemberId().toString(), accessTokenExpiredAt);

        return newAccessToken;
    }


    @Transactional
    public void saveRefreshToken(String refreshToken, String accessToken) {

        String subject = jwtTokenProvider.extractSubject(accessToken);

        Member member = memberRepository.getMemberById(Long.parseLong(subject));

        RefreshToken newRefreshToken = RefreshToken.createRefreshToken(member, refreshToken);

        refreshTokenRepository.save(newRefreshToken);
    }
}
