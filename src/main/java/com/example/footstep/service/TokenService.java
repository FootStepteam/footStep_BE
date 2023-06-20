package com.example.footstep.service;

import com.example.footstep.component.jwt.JwtTokenProvider;
import com.example.footstep.domain.entity.Member;
import com.example.footstep.domain.entity.RefreshToken;
import com.example.footstep.domain.repository.MemberRepository;
import com.example.footstep.domain.repository.RefreshTokenRepository;
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

        // 1) refresh-token 유효성 검사
        // 2) 유효하면 refresh-token 만료 일자 추출 (나중에 refresh token도 재발급할 때 활용)
        // 3) DB에서 refresh token 존재 유무 확인
        RefreshToken findRefreshToken = refreshTokenService.findToken(refreshToken);

        // 4-1) 존재하면 새로운 엑세스 토큰 발급(사용자 정보 (id) 포함)
        long now = (new Date()).getTime();
        Date accessTokenExpiredAt = new Date(now + ACCESS_TOKEN_EXPIRE_TIME);

        String newAccessToken = jwtTokenProvider.generate(
            findRefreshToken.getMember().getMemberId().toString(), accessTokenExpiredAt);
        // 4-2) 존재하지 않으면 예외 발생(ControllerAdvice 에서 예외처리)
        //

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
