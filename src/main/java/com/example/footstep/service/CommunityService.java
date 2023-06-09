package com.example.footstep.service;

import com.example.footstep.domain.dto.CommunityDetailResponse;
import com.example.footstep.domain.dto.CommunityListResponse;
import com.example.footstep.domain.form.CommunityCreateForm;
import org.springframework.data.domain.Pageable;

/*
**
파  일  명 : CommunityService
제작  연도 : 2023.06.08
작  성  자 : 김민호
개발 HISTORY
DATE                  AUTHOR                DESCRIPTION
------------------+--------------------+---------------------------------------------------
2023.06.08           김민호                게시글 생성, 특정 게시글 조회, 게시글 목록 조회 기능 구현
------------------+--------------------+---------------------------------------------------
*/
public interface CommunityService {

    void create(Long memberId, Long shareId, CommunityCreateForm form);

    CommunityDetailResponse getOne(Long communityId);

    CommunityListResponse getAll(Pageable pageable);
}
