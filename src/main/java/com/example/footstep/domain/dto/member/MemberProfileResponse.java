package com.example.footstep.domain.dto.member;

import com.example.footstep.domain.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberProfileResponse {

    private Long memberId;
    private String nickname;
    private String imgUrl;

    public static MemberProfileResponse from(Member member) {

        return MemberProfileResponse.builder()
            .memberId(member.getMemberId())
            .nickname(member.getNickname())
            .imgUrl(member.getImg())
            .build();

    }

}
