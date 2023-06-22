package com.example.footstep.domain.dto.member;

import com.example.footstep.domain.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberUpdateResponse {

    private String nickname;
    private String imgUrl;

    public static MemberUpdateResponse from(Member member) {
        return MemberUpdateResponse.builder()
            .nickname(member.getNickname())
            .imgUrl(member.getImg())
            .build();
    }
}
