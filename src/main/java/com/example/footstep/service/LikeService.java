package com.example.footstep.service;

/*
**
파  일  명 : LikeService
제작  연도 : 2023.06.08
작  성  자 : 김민호
개발 HISTORY
DATE                  AUTHOR                DESCRIPTION
------------------+--------------------+---------------------------------------------------
2023.06.08           김민호                특정 게시글에 대한 좋아요 기능 구현
------------------+--------------------+---------------------------------------------------
*/
public interface LikeService {

    void likeCommunity(Long memberId, Long communityId);

}
