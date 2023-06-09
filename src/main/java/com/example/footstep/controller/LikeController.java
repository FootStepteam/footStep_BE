package com.example.footstep.controller;

import com.example.footstep.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/*
**
파  일  명 : LikeController
제작  연도 : 2023.06.08
작  성  자 : 김민호
개발 HISTORY
DATE                  AUTHOR                DESCRIPTION
------------------+--------------------+---------------------------------------------------
2023.06.08           김민호                특정 게시글에 대한 좋아요 기능 구현
------------------+--------------------+---------------------------------------------------
*/
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
