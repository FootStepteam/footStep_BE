package com.example.footstep.domain.form;

import com.example.footstep.domain.entity.Community;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommunityCreateForm {

    private String communityName;
    private String content;
    private boolean communityPublicState;  // 게시글 공개 여부
    private Long shareId;


    public Community toEntity() {
        return Community.builder()
            .communityName(communityName)
            .content(content)
            .communityPublicState(communityPublicState)
            .build();
    }
}
