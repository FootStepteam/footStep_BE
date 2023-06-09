package com.example.footstep.service.impl;



import com.example.footstep.authentication.oauth.OAuthProvider;
import com.example.footstep.domain.entity.Member;

import com.example.footstep.domain.dto.MemberDto;
import com.example.footstep.domain.repository.MemberRepository;
import com.example.footstep.service.SignUpService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SignUpServiceimpl implements SignUpService {
    private final MemberRepository memberRepository;

    @Transactional
    public String memberSignup(MemberDto memberDto){
        if (!memberEmailExist(memberDto.getLoginEmail())){
            // 에러 코드 생성
            throw new RuntimeException(); // 추후 에러 컨트롤 핸들러 생성 후 변경
        }else {
            //회원가입 추가 테스트
            Member member = Member.builder()
                    .loginEmail(memberDto.getLoginEmail())
                    .password(memberDto.getPassword())
                    .nickname(memberDto.getNickname())
                    .gender(memberDto.getGender())
                    .memberOAuth(OAuthProvider.MEMBER)
                    .build();
            memberRepository.save(member);
            return "회원가입 성공";
        }
    }
    private boolean memberEmailExist(String email){
        return memberRepository.existsByLoginEmail(email).isPresent();
    }
}
