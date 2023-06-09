package com.example.footstep.service.impl;

import com.example.footstep.domain.dto.CommunityDetailResponse;
import com.example.footstep.domain.dto.CommunityListResponse;
import com.example.footstep.domain.entity.Community;
import com.example.footstep.domain.entity.Member;
import com.example.footstep.domain.entity.ShareRoom;
import com.example.footstep.domain.form.CommunityCreateForm;
import com.example.footstep.domain.repository.CommunityRepository;
import com.example.footstep.domain.repository.MemberRepository;
import com.example.footstep.domain.repository.ShareRoomRepository;
import com.example.footstep.exception.ErrorCode;
import com.example.footstep.exception.GlobalException;
import com.example.footstep.service.CommunityService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommunityServiceImpl implements CommunityService {

    private final ShareRoomRepository shareRoomRepository;
    private final CommunityRepository communityRepository;
    private final MemberRepository memberRepository;

    @Transactional
    @Override
    public void create(Long memberId, Long shareId, CommunityCreateForm form) {

        // 엔티티 조회(회원, 공유방)
        Member member = findMemberById(memberId);
        ShareRoom shareRoom = findShareRoomById(shareId);

        Community community = form.toEntity();

        community.setMember(member);
        community.setShareRoom(shareRoom);

        communityRepository.save(community);

    }

    @Transactional(readOnly = true)
    @Override
    public CommunityDetailResponse getOne(Long communityId) {

        Community community = findCommunityById(communityId);

        return CommunityDetailResponse.of(community, community.getMember(), community.getShareRoom());

    }

    //- 게시글 제목
    //- 공유방의 일정에 대한 정보(2023.07.01 ~ 2023.07.03)
    //- 작성자 닉네임
    //- 게시글 작성일
    //- 좋아요 수
    @Override
    public CommunityListResponse getAll(Pageable pageable) {
        Slice<Community> communities = communityRepository.findSliceBy(pageable);
        return CommunityListResponse.ofSlice(communities);
    }

    private Community findCommunityById(Long communityId) {
        return communityRepository.findById(communityId)
            .orElseThrow(() -> new GlobalException(ErrorCode.NOT_FIND_COMMUNITY_ID));
    }

    private Member findMemberById(Long memberId) {
        return memberRepository.findById(memberId)
            .orElseThrow(() -> new GlobalException(ErrorCode.NOT_FIND_MEMBER_ID));
    }

    private ShareRoom findShareRoomById(Long shareId) {
        return shareRoomRepository.findById(shareId)
            .orElseThrow(() -> new GlobalException(ErrorCode.NOT_FIND_SHARE_ID));
    }

}
