package com.example.footstep.controller;

import com.example.footstep.component.security.LoginMember;
import com.example.footstep.domain.form.CommentCreateForm;
import com.example.footstep.domain.form.CommentUpdateForm;
import com.example.footstep.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CommentController {

    private final CommentService commentService;

//    @GetMapping("/articles/{articleId}/comments")
//    public ResponseEntity<List<CommentResponse>> getComments(@LoginMember Member member, @PathVariable Long articleId) {
//        List<CommentResponse> commentResponses = commentService.getComment(member, articleId);
//        return ResponseEntity.ok(commentResponses);
//    }

    @PostMapping("/community/{communityId}/comments")
    public void createComment(
        @AuthenticationPrincipal LoginMember loginMember,
        @PathVariable Long communityId,
        @RequestBody CommentCreateForm commentCreateForm) {
        commentService.create(loginMember.getMemberId(), communityId, commentCreateForm);
    }

    @PutMapping("/comments/{commentId}")
    public void updateComment(
        @AuthenticationPrincipal LoginMember loginMember,
        @PathVariable Long commentId,
        @RequestBody CommentUpdateForm commentUpdateForm) {

        commentService.update(loginMember.getMemberId(), commentId, commentUpdateForm);

    }

    @DeleteMapping("/comments/{commentId}")
    public void deleteComment(
        @AuthenticationPrincipal LoginMember loginMember,
        @PathVariable Long commentId) {

        commentService.delete(loginMember.getMemberId(), commentId);

    }

}
