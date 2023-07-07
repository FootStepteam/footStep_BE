package com.example.footstep.controller;

import com.example.footstep.component.security.CurrentMember;
import com.example.footstep.component.security.LoginMember;
import com.example.footstep.model.dto.community.CommunityDetailDto;
import com.example.footstep.model.dto.community.CommunityLikedMemberDto;
import com.example.footstep.model.dto.community.CommunityListDto;
import com.example.footstep.model.dto.community.SearchCondition;
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
        @LoginMember(required = false) CurrentMember loginMember,
        @PathVariable Long communityId) {

        return ResponseEntity.ok(communityService.getOne(communityId, loginMember));
    }


    @GetMapping
    public CommunityListDto searchCommunity(
        SearchCondition searchCondition,
        @RequestParam(value = "sort", defaultValue = "like") String sort,
        @RequestParam(value = "page", defaultValue = "1") Integer page,
        @RequestParam(value = "size", defaultValue = "5") Integer size) {

        Pageable pageable = createPageable(matchPage(page), size, sort);

        return communityService.search(searchCondition, pageable);

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


    private Pageable createPageable(Integer page, Integer size, String sort) {

        if (sort.equals("recent")) {
            return PageRequest.of(page, size, Sort.by("createDate").descending());
        }

        return PageRequest.of(page, size, Sort.by("likeCount").descending());
    }


    private int matchPage(Integer page) {
        return (page == 0) ? 0 : (page - 1);
    }

}
