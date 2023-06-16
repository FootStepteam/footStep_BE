package com.example.footstep.domain.dto.comment;

import com.example.footstep.domain.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentResponseDto {

    private Long commentId;
    private String memberNickname;
    private String content;

    public static CommentResponseDto of(Comment comment) {
        return CommentResponseDto.builder()
            .commentId(comment.getCommentId())
            .memberNickname(comment.getMember().getNickname())
            .content(comment.getContent())
            .build();
    }
}
