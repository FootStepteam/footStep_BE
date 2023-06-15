package com.example.footstep.domain.dto.member;

import com.example.footstep.domain.entity.Member;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberDto {
    private Long memberId;
    private String loginEmail;
    private String nickname;
    private String password;
    private String gender;
    private String img;

    public static MemberDto of(Member member){
        return MemberDto.builder()
                .memberId(member.getMemberId())
                .nickname(member.getNickname())
                .gender(member.getGender())
                .img(member.getImg())
                .password(member.getPassword())
                .loginEmail(member.getLoginEmail())
                .build();
    }

}
