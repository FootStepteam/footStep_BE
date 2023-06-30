package com.example.footstep.service;

import static com.example.footstep.exception.ErrorCode.NOT_FIND_SHARE_ID;
import static com.example.footstep.exception.ErrorCode.NOT_MATCH_CREATE_MEMBER;

import com.example.footstep.domain.dto.share_room.ShareRoomDto;
import com.example.footstep.domain.dto.share_room.ShareRoomListDto;
import com.example.footstep.domain.entity.Member;
import com.example.footstep.domain.entity.ShareRoom;
import com.example.footstep.domain.entity.ShareRoomEnter;
import com.example.footstep.domain.form.ShareRoomForm;
import com.example.footstep.domain.repository.MemberRepository;
import com.example.footstep.domain.repository.ShareRoomEnterRepository;
import com.example.footstep.domain.repository.ShareRoomRepository;
import com.example.footstep.exception.GlobalException;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ShareRoomService {

    private final MemberRepository memberRepository;
    private final ShareRoomRepository shareRoomRepository;
    private final ShareRoomEnterRepository shareRoomEnterRepository;


    @Transactional(readOnly = true)
    public List<ShareRoomDto> getAllListShareRoom(Long memberId, Pageable pageable) {

        Member member = memberRepository.getMemberById(memberId);

        List<ShareRoomEnter> ShareRoomEnterList =
            shareRoomEnterRepository.findByMember_MemberId(member.getMemberId(), pageable);

        List<ShareRoomDto> shareRoomDtoList = new ArrayList<>();

        for (ShareRoomEnter shareRoomEnter : ShareRoomEnterList) {

            boolean hostFlag =
                member.getMemberId().equals(
                    shareRoomEnter.getShareRoom().getMember().getMemberId());

            ShareRoomDto shareRoomDto = ShareRoomDto.of(shareRoomEnter.getShareRoom(), hostFlag);

            shareRoomDtoList.add(shareRoomDto);
        }

        return shareRoomDtoList;
    }


    @Transactional(readOnly = true)
    public ShareRoomDto getOneShareRoom(Long memberId, Long shareId) {

        Member member = memberRepository.getMemberById(memberId);
        ShareRoom shareRoom = shareRoomRepository.getShareById(shareId);

        boolean hostFlag = member.getMemberId().equals(shareRoom.getMember().getMemberId());

        return ShareRoomDto.of(shareRoom, hostFlag);
    }


    @Transactional(readOnly = true)
    public ShareRoomDto getOneGuestShareRoom(String shareCode) {

        ShareRoom shareRoom = shareRoomRepository.findByShareCode(shareCode)
            .orElseThrow(() -> new GlobalException(NOT_FIND_SHARE_ID));

        return ShareRoomDto.from(shareRoom);
    }


    @Transactional
    public ShareRoomDto createShareRoom(Long memberId, ShareRoomForm shareRoomForm) {

        Member member = memberRepository.getMemberById(memberId);

        String shareCode = "";

        // 생성된 공유코드가 다른 방에 없으면 while 문 종료
        do {
            shareCode = RandomStringUtils.randomAlphanumeric(8);
        } while (shareRoomRepository.existsByShareCode(shareCode));

        ShareRoom shareRoom = shareRoomRepository.save(shareRoomForm.toEntity(shareCode, member));

        shareRoomEnterRepository.save(
            ShareRoomEnter.builder().member(member).shareRoom(shareRoom).build());

        return ShareRoomDto.from(shareRoom);
    }


    @Transactional
    public ShareRoomDto updateShareRoom(Long memberId, Long shareId, ShareRoomForm shareRoomForm) {

        Member member = memberRepository.getMemberById(memberId);
        ShareRoom shareRoom = shareRoomRepository.getShareById(shareId);

        if (!member.getMemberId().equals(shareRoom.getMember().getMemberId())) {
            throw new GlobalException(NOT_MATCH_CREATE_MEMBER);
        }

        shareRoom.setShareName(shareRoomForm.getShareName());
        shareRoom.setTravelStartDate(shareRoomForm.getTravelStartDate());
        shareRoom.setTravelEndDate(shareRoomForm.getTravelEndDate());
        shareRoom.setImageUrl(shareRoomForm.getImageUrl());

        return ShareRoomDto.from(shareRoom);
    }


    @Transactional
    public void deleteShareRoom(Long memberId, Long shareId) {

        Member member = memberRepository.getMemberById(memberId);
        ShareRoom shareRoom = shareRoomRepository.getShareById(shareId);

        if (!member.getMemberId().equals(shareRoom.getMember().getMemberId())) {
            throw new GlobalException(NOT_MATCH_CREATE_MEMBER);
        }

        shareRoomRepository.delete(shareRoom);
    }
}
