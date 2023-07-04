package com.example.footstep.model.dto.member;

import com.example.footstep.model.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberProfileDto {

    private Long memberId;
    private String loginEmail;
    private String nickname;
    private String password;
    private String gender;
    private String img;
    private String description;


    public static MemberProfileDto from(Member member) {
        return MemberProfileDto.builder()
            .memberId(member.getMemberId())
            .nickname(member.getNickname())
            .gender(member.getGender())
            .img(member.getImg())
            .password(member.getPassword())
            .loginEmail(member.getLoginEmail())
            .description(member.getDescription())
            .build();
    }

}
