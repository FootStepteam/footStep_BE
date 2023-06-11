package com.example.footstep.domain.dto.community;

import com.example.footstep.domain.entity.Community;
import com.example.footstep.domain.entity.Member;
import com.example.footstep.domain.entity.ShareRoom;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class CommunityDetailResponse {

    // 게시글 이름(제목), 작성자 닉네임, 좋아요 수, 게시글 작성일, 공유방 일정, 게시글 내용
    private String communityName;
    private String memberNickname;
    private int likeCount;
    private LocalDateTime createdDatetime;
    private String content;

    public static CommunityDetailResponse of(Community community, Member member,
        ShareRoom shareRoom) {

        return CommunityDetailResponse.builder()
            .communityName(community.getCommunityName())
            .memberNickname(member.getNickname())
            .likeCount(community.getLikeCount())
            .createdDatetime(community.getCreateDate())
            .content(community.getContent())
            .build();

    }
}
