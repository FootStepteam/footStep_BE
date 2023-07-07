package com.example.footstep.model.form;

import com.example.footstep.model.entity.Comment;
import com.example.footstep.model.entity.Community;
import com.example.footstep.model.entity.Member;
import lombok.Getter;

@Getter
public class CommentUpdateForm {

    private String content;

    public Comment toEntity(Community community, Member member) {
        return Comment.builder()
            .content(content)
            .community(community)
            .member(member)
            .build();
    }
}
