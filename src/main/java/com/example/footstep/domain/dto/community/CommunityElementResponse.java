package com.example.footstep.domain.dto.community;

import com.example.footstep.domain.entity.Community;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class CommunityElementResponse {

    //- 게시글 제목
    //- 공유방의 일정에 대한 정보(2023.07.01 ~ 2023.07.03)
    //- 작성자 닉네임
    //- 게시글 작성일
    //- 좋아요 수
    private String communityName;
    private String memberNickname;
    private LocalDateTime createdDate;
    private int likeCount;

    public static CommunityElementResponse from(Community community) {
        return CommunityElementResponse.builder()
            .communityName(community.getCommunityName())
            .memberNickname(community.getMember().getNickname())
            .createdDate(community.getCreateDate())
            .likeCount(community.getLikeCount())
            .build();
    }
}
