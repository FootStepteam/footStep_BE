package com.example.footstep.domain.dto.member;

import com.example.footstep.domain.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberUpdateDto {

    private String nickname;
    private String imgUrl;
    private String description;

    public static MemberUpdateDto from(Member member) {
        return MemberUpdateDto.builder()
            .nickname(member.getNickname())
            .imgUrl(member.getImg())
            .description(member.getDescription())
            .build();
    }
}
