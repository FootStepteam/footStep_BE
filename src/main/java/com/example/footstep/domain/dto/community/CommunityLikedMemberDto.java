package com.example.footstep.domain.dto.community;

import com.example.footstep.domain.entity.Community;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommunityLikedMemberDto {

    private Long communityId;
    private String communityName;
    private String travelStartDate;
    private String travelEndDate;

    public static CommunityLikedMemberDto from(Community community) {
        return CommunityLikedMemberDto.builder()
            .communityId(community.getCommunityId())
            .communityName(community.getCommunityName())
            .travelStartDate(community.getShareRoom().getTravelStartDate())
            .travelEndDate(community.getShareRoom().getTravelEndDate())
            .build();
    }
}
