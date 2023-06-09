package com.example.footstep.controller;

import com.example.footstep.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LikeController {

    private final LikeService likeService;

    // 게시글 좋아요
    @PostMapping("/community/{id}/like")
    public void like(@PathVariable("id") Long communityId) {

        likeService.likeCommunity(1L, communityId);

    }

    // 게시글 좋아요 취소
    @PostMapping("/community/{id}/un-like")
    public void cancelLike(@PathVariable("id") Long communityId) {

    }
}
