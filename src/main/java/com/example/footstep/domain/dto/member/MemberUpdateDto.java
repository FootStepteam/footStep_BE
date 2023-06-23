package com.example.footstep.domain.dto.member;

import com.example.footstep.domain.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberUpdateDto {

    private String nickname;
    private String imgUrl;

    public static MemberUpdateDto from(Member member) {
        return MemberUpdateDto.builder()
            .nickname(member.getNickname())
            .imgUrl(member.getImg())
            .build();
    }
}
