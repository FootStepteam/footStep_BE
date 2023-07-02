package com.example.footstep.model.form;

import com.example.footstep.model.entity.Community;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommunityCreateForm {

    private String communityName;
    private String content;
    private boolean communityPublicState;
    private Long shareId;


    public Community toEntity() {
        return Community.builder()
            .communityName(communityName)
            .content(content)
            .communityPublicState(communityPublicState)
            .build();
    }
}
