package com.example.footstep.service;

import com.example.footstep.component.security.CurrentMember;
import com.example.footstep.model.dto.community.CommunityDetailDto;
import com.example.footstep.model.dto.community.CommunityListDto;
import com.example.footstep.model.entity.Comment;
import com.example.footstep.model.entity.Community;
import com.example.footstep.model.entity.Member;
import com.example.footstep.model.entity.ShareRoom;
import com.example.footstep.model.form.CommunityCreateForm;
import com.example.footstep.model.form.CommunityUpdateForm;
import com.example.footstep.model.repository.CommentRepository;
import com.example.footstep.model.repository.CommunityRepository;
import com.example.footstep.model.repository.LikeRepository;
import com.example.footstep.model.repository.MemberRepository;
import com.example.footstep.model.repository.ShareRoomRepository;
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
    private final LikeRepository likeRepository;


    @Transactional(readOnly = true)
    public CommunityDetailDto getOne(Long communityId, CurrentMember loginMember) {

        // 일반 사용자 조회 or 해당 게시글에 좋아요를 하지 않았을 경우
        boolean isLiked = false;

        if (loginMember != null &&
            likeRepository.existsByCommunity_CommunityIdAndMember_MemberId(
                communityId, loginMember.getMemberId())) {

            isLiked = true;
        }

        Community community = communityRepository.findByIdWithShareRoomAndMember(communityId)
            .orElseThrow(() -> new GlobalException(ErrorCode.NOT_FIND_COMMUNITY_ID));

        List<Comment> comments =
            commentRepository.findAllByCommunityIdWithMember(communityId);

        return CommunityDetailDto.of(
            community, community.getMember(), community.getShareRoom(), comments, isLiked);
    }


    @Transactional(readOnly = true)
    public CommunityListDto getAll(Pageable pageable) {

        Slice<Community> communities = communityRepository.findSliceBy(pageable);

        return CommunityListDto.fromSlice(communities);
    }


    @Transactional(readOnly = true)
    public List<Community> getCommunitiesLikedByMember(Long memberId) {

        List<Long> ids = likeRepository.findCommunityIds(memberId);

        return communityRepository.findCommunitiesLikedBy(ids);
    }


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


    @Transactional
    public void update(Long memberId, Long communityId, CommunityUpdateForm communityUpdateForm) {

        Community community = findByCommunityIdAndMemberId(memberId, communityId);
        community.update(communityUpdateForm.getContent(), communityUpdateForm.getCommunityName());

    }

    @Transactional
    public void delete(Long memberId, Long communityId) {

        Community community = communityRepository.getCommunityById(communityId);

        if (!community.isWrittenBy(memberId)) {
            throw new GlobalException(ErrorCode.UN_MATCHED_MEMBER_AND_COMMUNITY);
        }

        communityRepository.delete(community);
    }

    private Community findByCommunityIdAndMemberId(Long memberId, Long communityId) {

        return communityRepository.findByCommunityIdAndMember_MemberId(communityId, memberId)
            .orElseThrow(() -> new GlobalException(ErrorCode.NOT_FIND_COMMUNITY_ID));
    }
}
