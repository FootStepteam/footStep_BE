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
    private Long memberId;
    private String memberNickname;
    private String content;

    public static CommentResponseDto of(Comment comment) {
        return CommentResponseDto.builder()
            .commentId(comment.getCommentId())
            .memberId(comment.getMember().getMemberId())
            .memberNickname(comment.getMember().getNickname())
            .content(comment.getContent())
            .build();
    }
}
