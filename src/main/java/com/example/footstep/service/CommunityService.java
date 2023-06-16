package com.example.footstep.service;

import com.example.footstep.domain.dto.community.CommunityDetailDto;
import com.example.footstep.domain.dto.community.CommunityListDto;
import com.example.footstep.domain.entity.Comment;
import com.example.footstep.domain.entity.Community;
import com.example.footstep.domain.entity.Member;
import com.example.footstep.domain.entity.ShareRoom;
import com.example.footstep.domain.form.CommunityCreateForm;
import com.example.footstep.domain.form.CommunityUpdateForm;
import com.example.footstep.domain.repository.CommentRepository;
import com.example.footstep.domain.repository.CommunityRepository;
import com.example.footstep.domain.repository.MemberRepository;
import com.example.footstep.domain.repository.ShareRoomRepository;
import com.example.footstep.exception.ErrorCode;
import com.example.footstep.exception.GlobalException;
import java.util.List;
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
    private final CommentRepository commentRepository;

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
    public CommunityDetailDto getOne(Long communityId) {

        Community community = communityRepository.findByIdWithShareRoomAndMember(communityId)
            .orElseThrow(() -> new GlobalException(ErrorCode.NOT_FIND_COMMUNITY_ID));

        List<Comment> comments = commentRepository.findAllByCommunityIdWithMember(
            communityId);

        return CommunityDetailDto.of(community, community.getMember(),
            community.getShareRoom(), comments);

    }

    @Transactional(readOnly = true)
    public CommunityListDto getAll(Pageable pageable) {
        Slice<Community> communities = communityRepository.findSliceBy(pageable);
        return CommunityListDto.ofSlice(communities);
    }

    @Transactional
    public void update(Long memberId, Long communityId, CommunityUpdateForm communityUpdateForm) {

        Community community = findByCommunityIdAndMemberId(memberId, communityId);

        community.updateContent(communityUpdateForm.getContent());

    }

    @Transactional
    public void delete(Long memberId, Long communityId) {

        Community community = findByCommunityIdAndMemberId(memberId, communityId);

        communityRepository.delete(community);

    }

    private Community findByCommunityIdAndMemberId(Long memberId, Long communityId) {
        return communityRepository.findByCommunityIdAndMember_MemberId(communityId, memberId)
            .orElseThrow(() -> new GlobalException(ErrorCode.NOT_FIND_COMMUNITY_ID));
    }
}
