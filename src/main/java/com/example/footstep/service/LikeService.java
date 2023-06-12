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

        Member member = memberRepository.getMemberById(memberId);

        Community community = communityRepository.getCommunityById(communityId);

        communityRepository.increaseLikeCount(communityId);

        likeRepository.save(Likes.builder()
            .member(member)
            .community(community)
            .build());
    }

}
