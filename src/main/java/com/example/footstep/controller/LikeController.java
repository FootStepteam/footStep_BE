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

    @PostMapping("/community/{id}/like")
    public void like(@PathVariable("id") Long communityId) {

        likeService.likeCommunity(1L, communityId);

    }

    @PostMapping("/community/{id}/un-like")
    public void cancelLike(@PathVariable("id") Long communityId) {

    }
}
