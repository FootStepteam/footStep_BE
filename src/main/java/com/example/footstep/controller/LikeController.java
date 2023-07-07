package com.example.footstep.controller;

import com.example.footstep.component.security.CurrentMember;
import com.example.footstep.component.security.LoginMember;
import com.example.footstep.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/community/{communityId}")
public class LikeController {

    private final LikeService likeService;


    @PostMapping("/like")
    public void likeCommunity(
        @LoginMember CurrentMember loginMember,
        @PathVariable("communityId") Long communityId) {

        likeService.likeCommunity(loginMember.getMemberId(), communityId);
    }


    @PostMapping("/un-like")
    public void cancelLike(
        @LoginMember CurrentMember loginMember,
        @PathVariable("communityId") Long communityId) {

        likeService.unLikeCommunity(loginMember.getMemberId(), communityId);
    }
}
