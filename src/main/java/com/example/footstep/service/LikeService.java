package com.example.footstep.service;

import com.example.footstep.model.entity.Community;
import com.example.footstep.model.entity.Likes;
import com.example.footstep.model.entity.Member;
import com.example.footstep.model.repository.CommunityRepository;
import com.example.footstep.model.repository.LikeRepository;
import com.example.footstep.model.repository.MemberRepository;
import com.example.footstep.exception.ErrorCode;
import com.example.footstep.exception.GlobalException;
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


    @Transactional
    public void unLikeCommunity(Long memberId, Long communityId) {

        // 좋아요를 안했는데 좋아요 취소를 눌렀을 경우 예외 처리도 해야하나
        Likes likes = likeRepository.findByCommunity_CommunityIdAndMember_MemberId(communityId,
                memberId)
            .orElseThrow(() -> new GlobalException(ErrorCode.NOT_FIND_LIKE_COMMUNITY));
        communityRepository.decreaseLikeCount(communityId);
        likeRepository.deleteById(likes.getLikesId());

    }
}
