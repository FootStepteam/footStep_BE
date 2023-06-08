package com.example.footstep.service;



import com.example.footstep.domain.entity.Member;

import com.example.footstep.domain.entity.dto.member.MemberDto;
import com.example.footstep.domain.entity.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SignUpService {
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
                    .build();
            memberRepository.save(member);
            return "회원가입 성공";
        }
    }
    public boolean memberEmailExist(String email){
        return memberRepository.existsByLoginEmail(email).isPresent();
    }
}
