package com.example.footstep.domain.dto.community;

import com.example.footstep.domain.dto.comment.CommentResponseDto;
import com.example.footstep.domain.entity.Comment;
import com.example.footstep.domain.entity.Community;
import com.example.footstep.domain.entity.Member;
import com.example.footstep.domain.entity.ShareRoom;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
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
public class CommunityDetailDto {

    private String memberNickname;

    private Long communityId;
    private String communityName;
    private int likeCount;
    private LocalDateTime createdDate;
    private String content;

    private String travelStartDate;
    private String travelEndDate;

    private int commentCount;
    private List<CommentResponseDto> comments = new ArrayList<>();

    public static CommunityDetailDto of(Community community, Member member,
                                        ShareRoom shareRoom, List<Comment> comments) {

        return CommunityDetailDto.builder()
            .communityId(community.getCommunityId())
            .communityName(community.getCommunityName())
            .likeCount(community.getLikeCount())
            .createdDate(community.getCreateDate())
            .content(community.getContent())
            .memberNickname(member.getNickname())
            .travelStartDate(shareRoom.getTravelStartDate())
            .travelEndDate(shareRoom.getTravelEndDate())
            .commentCount(comments.size())
            .comments(comments.stream()
                .map(CommentResponseDto::of)
                .collect(Collectors.toList())
            )
            .build();

    }
}
