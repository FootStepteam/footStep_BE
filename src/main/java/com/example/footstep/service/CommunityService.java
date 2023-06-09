package com.example.footstep.service;

import com.example.footstep.domain.dto.CommunityDetailResponse;
import com.example.footstep.domain.dto.CommunityListResponse;
import com.example.footstep.domain.form.CommunityCreateForm;
import org.springframework.data.domain.Pageable;

public interface CommunityService {

    void create(Long memberId, Long shareId, CommunityCreateForm form);

    CommunityDetailResponse getOne(Long communityId);

    CommunityListResponse getAll(Pageable pageable);
}
