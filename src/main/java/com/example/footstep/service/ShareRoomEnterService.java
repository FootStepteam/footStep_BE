package com.example.footstep.service;

import com.example.footstep.domain.dto.chat.ShareRoomEnterDto;
import com.example.footstep.domain.entity.Member;
import com.example.footstep.domain.entity.ShareRoom;
import com.example.footstep.domain.entity.ShareRoomEnter;
import com.example.footstep.domain.repository.MemberRepository;
import com.example.footstep.domain.repository.ShareRoomEnterRepository;
import com.example.footstep.domain.repository.ShareRoomRepository;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ShareRoomEnterService {

    private final MemberRepository memberRepository;
    private final ShareRoomRepository shareRoomRepository;
    private final ShareRoomEnterRepository shareRoomEnterRepository;

    private Map<Long, ShareRoomEnterDto> chatRooms;


    @PostConstruct
    private void init() {
        chatRooms = new LinkedHashMap<>();
    }


    //채팅방 하나 불러오기
    public ShareRoomEnterDto getOneShareRoomEnter(Long shareRoomEnterId) {

        return chatRooms.get(shareRoomEnterId);
    }


    @Transactional
    public ShareRoomEnterDto createShareRoomEnter(Long memberId, Long shareId) {

        Member member = memberRepository.getMemberById(memberId);

        ShareRoom shareRoom = shareRoomRepository.getShareById(shareId);

        ShareRoomEnter shareRoomEnter =
            shareRoomEnterRepository.findByShareRoom_ShareIdAndMember_MemberId(
                shareRoom.getShareId(), member.getMemberId()).orElseGet(() ->
                shareRoomEnterRepository.save(
                    ShareRoomEnter.builder().member(member).shareRoom(shareRoom).build()));

        ShareRoomEnterDto shareRoomEnterDto = ShareRoomEnterDto.of(
            shareRoomEnter.getShareRoom().getShareId(),
            shareRoomEnter.getShareRoom().getShareName());

        chatRooms.put(shareRoomEnterDto.getShareId(), shareRoomEnterDto);

        return shareRoomEnterDto;
    }
}
