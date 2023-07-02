package com.example.footstep.controller;

import com.example.footstep.component.security.CurrentMember;
import com.example.footstep.component.security.LoginMember;
import com.example.footstep.model.dto.community.CommunityDetailDto;
import com.example.footstep.model.dto.community.CommunityLikedMemberDto;
import com.example.footstep.model.dto.community.CommunityListDto;
import com.example.footstep.model.form.CommunityCreateForm;
import com.example.footstep.model.form.CommunityUpdateForm;
import com.example.footstep.service.CommunityService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/community")
public class CommunityController {

    private final CommunityService communityService;


    @PostMapping
    public void createCommunity(
        @LoginMember CurrentMember loginMember,
        @RequestBody CommunityCreateForm communityCreateForm) {

        communityService.create(loginMember.getMemberId(),
            communityCreateForm.getShareId(),
            communityCreateForm);
    }

    @GetMapping("/{communityId}")
    public ResponseEntity<CommunityDetailDto> getOneCommunity(
        @LoginMember CurrentMember loginMember,
        @PathVariable Long communityId) {

        return ResponseEntity.ok(communityService.getOne(communityId, loginMember));
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


    @GetMapping("likes")
    public ResponseEntity<List<CommunityLikedMemberDto>> getCommunitiesLiked(
        @LoginMember CurrentMember loginMember) {

        return ResponseEntity.ok(
            communityService.getCommunitiesLikedByMember(loginMember.getMemberId())
                .stream()
                .map(CommunityLikedMemberDto::from)
                .collect(Collectors.toList())
        );
    }


    @PutMapping("/{communityId}")
    public void updateCommunity(
        @LoginMember CurrentMember loginMember,
        @PathVariable Long communityId,
        @RequestBody CommunityUpdateForm communityUpdateForm
    ) {

        communityService.update(loginMember.getMemberId(), communityId, communityUpdateForm);
    }


    @DeleteMapping("/{communityId}")
    public void deleteCommunity(
        @LoginMember CurrentMember loginMember,
        @PathVariable Long communityId) {

        communityService.delete(loginMember.getMemberId(), communityId);
    }
}
