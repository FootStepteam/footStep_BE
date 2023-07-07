package com.example.footstep.model.dto.community;

import com.example.footstep.model.entity.Community;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class CommunityElementDto {

    private Long communityId;
    private Long memberId;
    private String communityName;
    private String memberNickname;
    private LocalDateTime createdDate;
    private int likeCount;
    private boolean communityPublicState;


    public static CommunityElementDto from(Community community) {
        return CommunityElementDto.builder()
            .communityId(community.getCommunityId())
            .communityName(community.getCommunityName())
            .memberId(community.getMember().getMemberId())
            .memberNickname(community.getMember().getNickname())
            .createdDate(community.getCreateDate())
            .likeCount(community.getLikeCount())
            .communityPublicState(community.isCommunityPublicState())
            .build();
    }
}
