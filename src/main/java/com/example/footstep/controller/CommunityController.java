package com.example.footstep.controller;

import com.example.footstep.domain.dto.CommunityDetailResponse;
import com.example.footstep.domain.dto.CommunityListResponse;
import com.example.footstep.domain.form.CommunityCreateForm;
import com.example.footstep.service.CommunityService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CommunityController {

    private final CommunityService communityService;

    // 게시글 생성
    @PostMapping("/community")
    public void create(@RequestBody CommunityCreateForm request) {

        Long memberId = 1L; // 임시 회원 ID

        communityService.create(memberId, request.getShareId(), request);

    }

    // 특정 게시글 조회
    @GetMapping("community/{community-id}")
    public ResponseEntity<CommunityDetailResponse> getOne(
        @PathVariable("community-id") Long communityId) {

        return ResponseEntity.ok(communityService.getOne(communityId));

    }

    // 게시글 목록 조회
    @GetMapping("community")
    public CommunityListResponse getAll(int page, int size, @RequestParam(value = "sort", defaultValue = "like") String sorting) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("likeCount").descending());

        if (sorting.equals("recent")) {
            pageable = PageRequest.of(page, size, Sort.by("createDate").descending());
        }

        return communityService.getAll(pageable);

    }

    // 게시글 수정
    @PutMapping("community/{community-id}")
    public void update(@PathVariable("community-id") Long communityId) {

    }

    // 게시글 삭제
    @DeleteMapping("community/{community-id}")
    public void delete(@PathVariable("community-id") Long communityId) {

    }


}
