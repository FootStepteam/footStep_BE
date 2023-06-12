package com.example.footstep.controller;

import com.example.footstep.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/community")
public class LikeController {

    private final LikeService likeService;

    @PostMapping("/{id}/like")
    public void likeCommunity(@PathVariable("id") Long communityId) {

        likeService.likeCommunity(1L, communityId);

    }

    @PostMapping("/{id}/un-like")
    public void cancelLike(@PathVariable("id") Long communityId) {

    }
}
