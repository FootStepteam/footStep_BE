package com.example.footstep.service;

import com.example.footstep.domain.dto.community.CommunityDetailResponse;
import com.example.footstep.domain.dto.community.CommunityListResponse;
import com.example.footstep.domain.entity.Community;
import com.example.footstep.domain.entity.Member;
import com.example.footstep.domain.entity.ShareRoom;
import com.example.footstep.domain.form.CommunityCreateForm;
import com.example.footstep.domain.repository.CommunityRepository;
import com.example.footstep.domain.repository.MemberRepository;
import com.example.footstep.domain.repository.ShareRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommunityService {

    private final ShareRoomRepository shareRoomRepository;
    private final CommunityRepository communityRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public void create(Long memberId, Long shareId, CommunityCreateForm communityCreateForm) {

        // 엔티티 조회(회원, 공유방)
        Member member = memberRepository.getMemberById(memberId);
        ShareRoom shareRoom = shareRoomRepository.getShareById(shareId);

        Community community = communityCreateForm.toEntity();

        community.setMember(member);
        community.setShareRoom(shareRoom);

        communityRepository.save(community);

    }

    @Transactional(readOnly = true)
    public CommunityDetailResponse getOne(Long communityId) {

        Community community = communityRepository.getCommunityById(communityId);

        return CommunityDetailResponse.of(community, community.getMember(),
            community.getShareRoom());

    }

    @Transactional(readOnly = true)
    public CommunityListResponse getAll(Pageable pageable) {
        Slice<Community> communities = communityRepository.findSliceBy(pageable);
        return CommunityListResponse.ofSlice(communities);
    }

}
