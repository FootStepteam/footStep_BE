package com.example.footstep.service;

import com.example.footstep.domain.entity.Community;
import com.example.footstep.domain.entity.Likes;
import com.example.footstep.domain.entity.Member;
import com.example.footstep.domain.repository.CommunityRepository;
import com.example.footstep.domain.repository.LikeRepository;
import com.example.footstep.domain.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final LikeRepository likeRepository;
    private final MemberRepository memberRepository;
    private final CommunityRepository communityRepository;

    @Transactional
    public void likeCommunity(Long memberId, Long communityId) {

        Member member = memberRepository.findById(memberId)
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않은 회원입니다."));
        Community community = communityRepository.findById(communityId)
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않은 게시글입니다."));

        communityRepository.increaseLikeCount(communityId);

        likeRepository.save(Likes.builder()
            .member(member)
            .community(community)
            .build());
    }

}
