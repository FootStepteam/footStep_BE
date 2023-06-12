package com.example.footstep.service;

import com.example.footstep.authentication.oauth.OAuthInfoResponse;
import com.example.footstep.authentication.oauth.OAuthLoginParams;
import com.example.footstep.authentication.oauth.kakao.KakaoApiClient;
import com.example.footstep.component.security.AuthTokens;
import com.example.footstep.component.security.AuthTokensGenerator;
import com.example.footstep.component.jwt.RequestOAuthInfoService;
import com.example.footstep.domain.entity.Member;
import com.example.footstep.domain.repository.MemberRepository;
import com.example.footstep.exception.ErrorCode;
import com.example.footstep.exception.GlobalException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KakaoService {

    private final MemberRepository memberRepository;
    private final AuthTokensGenerator authTokensGenerator;
    private final RequestOAuthInfoService requestOAuthInfoService;
    private final KakaoApiClient kakaoApiClient;
    public AuthTokens login(OAuthLoginParams kakaoAccessCode) {
        OAuthInfoResponse oAuthInfoResponse = requestOAuthInfoService. request(kakaoAccessCode);
        Long memberId = findOrCreateMember(oAuthInfoResponse);
        return authTokensGenerator.generate(memberId);
    }

    private Long findOrCreateMember(OAuthInfoResponse oAuthInfoResponse) {
        return memberRepository.findByLoginEmail(oAuthInfoResponse.getEmail())
                .map(Member::getMemberId)
                .orElseGet(() -> newMember(oAuthInfoResponse));
    }

    private Long newMember(OAuthInfoResponse oAuthInfoResponse) {
        Member member = Member.builder()
                .loginEmail(oAuthInfoResponse.getEmail())
                .nickname(oAuthInfoResponse.getNickName())
                .img(oAuthInfoResponse.getImg())
                .gender(oAuthInfoResponse.getGender())
                .memberOAuth(oAuthInfoResponse.getOAuthProvider())
                .build();

        return memberRepository.save(member).getMemberId();
    }

    public void kakaoUnlink(OAuthLoginParams kakaoAccessCode) {
        if(kakaoAccessCode == null) {
            throw new GlobalException(ErrorCode.EMPTY_ACCESS_TOKEN);
        }else{
            String accessToken = requestOAuthInfoService.getAccessToken(kakaoAccessCode);
            kakaoApiClient.kakaoUnlink(accessToken);
        }

    }
}
