package com.example.footstep.service;

import com.example.footstep.component.jwt.RequestOAuthInfoService;
import com.example.footstep.component.jwt.AuthTokens;
import com.example.footstep.component.jwt.AuthTokensGenerator;
import com.example.footstep.authentication.oauth.OAuthInfoResponse;
import com.example.footstep.authentication.oauth.OAuthLoginParams;
import com.example.footstep.domain.entity.Member;

import com.example.footstep.domain.entity.repository.MemberRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


/*
**
파  일  명 : kakaoService
제작  연도 : 2023.06.07
작  성  자 : 이대호
개발 HISTORY
DATE                  AUTHOR                DESCRIPTION
------------------+--------------------+--------------------------------------------
2023-06-7           이대호                 카카오 API 연동 (AccessToken 발급 , 카카오 계정 정보 조회)
------------------+--------------------+--------------------------------------------
*/
@Service
@AllArgsConstructor
public class KakaoService {
        private final MemberRepository memberRepository;

        private final AuthTokensGenerator authTokensGenerator;
        private final RequestOAuthInfoService requestOAuthInfoService;

        public AuthTokens login(OAuthLoginParams params) {
            OAuthInfoResponse oAuthInfoResponse = requestOAuthInfoService.request(params);
            Long memberId = findOrCreateMember(oAuthInfoResponse);
            return authTokensGenerator.generate(memberId);
        }

        private Long findOrCreateMember(OAuthInfoResponse oAuthInfoResponse) {
            return memberRepository.findByLoginEmail(oAuthInfoResponse.getEmail()).map(Member::getMemberId).orElseGet(() -> newMember(oAuthInfoResponse));
//            if(memberRepository.existsByLoginEmail(oAuthInfoResponse.getEmail()).isPresent()){
//                return memberRepository.findByLoginEmail(oAuthInfoResponse.getEmail()).get().getMemberId();
//            }else{
//                newMember(oAuthInfoResponse);
//            }
//            return null;
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
}
