package com.example.footstep.service;

import com.example.footstep.domain.dto.chat.ShareRoomEnterDto;
import com.example.footstep.domain.entity.Member;
import com.example.footstep.domain.entity.ShareRoom;
import com.example.footstep.domain.entity.ShareRoomEnter;
import com.example.footstep.domain.repository.MemberRepository;
import com.example.footstep.domain.repository.ShareRoomEnterRepository;
import com.example.footstep.domain.repository.ShareRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ShareRoomEnterService {

    private final MemberRepository memberRepository;
    private final ShareRoomRepository shareRoomRepository;
    private final ShareRoomEnterRepository shareRoomEnterRepository;

//    public ShareRoomEnterDto getOneShareRoomEnter(Long shareId) {
//
//        return chatRooms.get(shareId);
//    }


    @Transactional
    public ShareRoomEnterDto createShareRoomEnter(Long memberId, Long shareId) {

        Member member = memberRepository.getMemberById(memberId);

        ShareRoom shareRoom = shareRoomRepository.getShareById(shareId);

        ShareRoomEnter shareRoomEnter =
            shareRoomEnterRepository.findByShareRoom_ShareIdAndMember_MemberId(
                shareRoom.getShareId(), member.getMemberId()).orElseGet(() ->
                shareRoomEnterRepository.save(
                    ShareRoomEnter.builder().member(member).shareRoom(shareRoom).build()));

        return ShareRoomEnterDto.from(shareRoomEnter);
    }
}
