package com.example.footstep.domain.dto.community;

import com.example.footstep.domain.entity.Community;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class CommunityElementDto {

    private Long communityId;
    private String communityName;
    private String memberNickname;
    private LocalDateTime createdDate;
    private int likeCount;
    private boolean communityPublicState;

    public static CommunityElementDto from(Community community) {
        return CommunityElementDto.builder()
            .communityId(community.getCommunityId())
            .communityName(community.getCommunityName())
            .memberNickname(community.getMember().getNickname())
            .createdDate(community.getCreateDate())
            .likeCount(community.getLikeCount())
            .communityPublicState(community.isCommunityPublicState())
            .build();
    }
}
