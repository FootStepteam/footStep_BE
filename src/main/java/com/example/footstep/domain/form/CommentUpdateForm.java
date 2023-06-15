package com.example.footstep.domain.form;

import com.example.footstep.domain.entity.Comment;
import com.example.footstep.domain.entity.Community;
import com.example.footstep.domain.entity.Member;
import lombok.Getter;

@Getter
public class CommentUpdateForm {

    private String content;

    public Comment toEntity(Community community, Member member) {
        return Comment.builder()
            .content(this.content)
            .community(community)
            .member(member)
            .build();
    }
}
