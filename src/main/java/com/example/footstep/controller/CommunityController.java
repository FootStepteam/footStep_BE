package com.example.footstep.controller;

import com.example.footstep.domain.dto.CommunityDetailDto;
import com.example.footstep.domain.dto.CommunityListDto;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/community")
public class CommunityController {

    private final CommunityService communityService;

    @PostMapping
    public void create(@RequestBody CommunityCreateForm request) {

        Long memberId = 1L; // 임시 회원 ID

        communityService.create(memberId, request.getShareId(), request);

    }

    @GetMapping("/{community-id}")
    public ResponseEntity<CommunityDetailDto> getOneCommunity(
        @PathVariable("community-id") Long communityId) {

        return ResponseEntity.ok(communityService.getOne(communityId));

    }

    @GetMapping
    public CommunityListDto getAllCommunity(int page, int size,
                                   @RequestParam(value = "sort", defaultValue = "like") String sorting) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("likeCount").descending());

        if (sorting.equals("recent")) {
            pageable = PageRequest.of(page, size, Sort.by("createDate").descending());
        }

        return communityService.getAll(pageable);

    }

    @PutMapping("/{community-id}")
    public void update(@PathVariable("community-id") Long communityId) {

    }

    @DeleteMapping("/{community-id}")
    public void delete(@PathVariable("community-id") Long communityId) {

    }
}
