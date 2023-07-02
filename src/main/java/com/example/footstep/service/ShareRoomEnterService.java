package com.example.footstep.service;

import com.example.footstep.model.dto.chat.MessageDto;
import com.example.footstep.model.dto.chat.ShareRoomEnterDto;
import com.example.footstep.model.entity.Member;
import com.example.footstep.model.entity.Message;
import com.example.footstep.model.entity.ShareRoom;
import com.example.footstep.model.entity.ShareRoomEnter;
import com.example.footstep.model.repository.MemberRepository;
import com.example.footstep.model.repository.MessageRepository;
import com.example.footstep.model.repository.ShareRoomEnterRepository;
import com.example.footstep.model.repository.ShareRoomRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ShareRoomEnterService {

    private final MemberRepository memberRepository;
    private final ShareRoomRepository shareRoomRepository;
    private final ShareRoomEnterRepository shareRoomEnterRepository;
    private final MessageRepository messageRepository;


    public List<MessageDto> getListShareRoomEnterMessage(Long shareId) {

        List<Message> messageList =
            messageRepository.findByShareRoomEnter_ShareRoom_ShareIdOrderByCreateDate(shareId);

        List<MessageDto> MessageDtoList = new ArrayList<>();

        for (Message message : messageList) {

            MessageDto messageDto = MessageDto.from(message);
            MessageDtoList.add(messageDto);
        }

        return MessageDtoList;
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

        return ShareRoomEnterDto.from(shareRoomEnter);
    }
}
