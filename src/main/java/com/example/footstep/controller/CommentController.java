package com.example.footstep.controller;

import com.example.footstep.component.security.CurrentMember;
import com.example.footstep.component.security.LoginMember;
import com.example.footstep.model.form.CommentCreateForm;
import com.example.footstep.model.form.CommentUpdateForm;
import com.example.footstep.service.CommentService;
import lombok.RequiredArgsConstructor;
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

    @PostMapping("/community/{communityId}/comments")
    public void createComment(
        @LoginMember CurrentMember loginMember,
        @PathVariable Long communityId,
        @RequestBody CommentCreateForm commentCreateForm) {

        commentService.create(loginMember.getMemberId(), communityId, commentCreateForm);
    }


    @PutMapping("/comments/{commentId}")
    public void updateComment(
        @LoginMember CurrentMember loginMember,
        @PathVariable Long commentId,
        @RequestBody CommentUpdateForm commentUpdateForm) {

        commentService.update(loginMember.getMemberId(), commentId, commentUpdateForm);
    }


    @DeleteMapping("/comments/{commentId}")
    public void deleteComment(
        @LoginMember CurrentMember loginMember,
        @PathVariable Long commentId) {

        commentService.delete(loginMember.getMemberId(), commentId);

    }
}
