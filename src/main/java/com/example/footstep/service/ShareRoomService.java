package com.example.footstep.service;

import static com.example.footstep.exception.ErrorCode.NOT_MATCH_CREATE_MEMBER;

import com.example.footstep.domain.dto.share_room.ShareRoomDto;
import com.example.footstep.domain.dto.share_room.ShareRoomListDto;
import com.example.footstep.domain.entity.Member;
import com.example.footstep.domain.entity.ShareRoom;
import com.example.footstep.domain.form.ShareRoomForm;
import com.example.footstep.domain.form.ShareRoomPageForm;
import com.example.footstep.domain.repository.MemberRepository;
import com.example.footstep.domain.repository.ShareRoomRepository;
import com.example.footstep.exception.GlobalException;
import java.security.SecureRandom;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ShareRoomService {

    private final MemberRepository memberRepository;
    private final ShareRoomRepository shareRoomRepository;


    // 공유방 코드에 사용되는 문자
    private static final String SHARE_USE_CODE =
        "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";


    @Transactional(readOnly = true)
    public List<ShareRoomListDto> getAllListShareRoom(Long memberId,
        ShareRoomPageForm shareRoomPageForm) {

        Member member = memberRepository.getMemberById(memberId);

        Pageable pageable = PageRequest.of(shareRoomPageForm.getPage(),
            shareRoomPageForm.getSize());

        return ShareRoomListDto.of(
            shareRoomRepository.findByMember_MemberId(member.getMemberId(), pageable));
    }


    @Transactional(readOnly = true)
    public ShareRoomDto getOneShareRoom(Long memberId, Long shareId) {

        Member member = memberRepository.getMemberById(memberId);
        ShareRoom shareRoom = shareRoomRepository.getShareById(shareId);

        if (!member.getMemberId().equals(shareRoom.getMember().getMemberId())) {
            throw new GlobalException(NOT_MATCH_CREATE_MEMBER);
        }

        return ShareRoomDto.from(shareRoom);
    }


    @Transactional
    public ShareRoomDto createShareRoom(Long memberId, ShareRoomForm shareRoomForm) {

        Member member = memberRepository.getMemberById(memberId);

        String shareCode = "";
        // 생성된 공유코드가 다른 방에 없으면 while 문 종료
        while (true) {
            if (!shareRoomRepository.existsByShareCode(addShareCode())) {
                shareCode = addShareCode();
                break;
            }
        }

        ShareRoom shareRoom = shareRoomRepository.save(shareRoomForm.toEntity(shareCode));
        member.getShareRooms().add(shareRoom);

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
    public String deleteShareRoom(Long memberId, Long shareId) {

        Member member = memberRepository.getMemberById(memberId);
        ShareRoom shareRoom = shareRoomRepository.getShareById(shareId);

        if (!member.getMemberId().equals(shareRoom.getMember().getMemberId())) {
            throw new GlobalException(NOT_MATCH_CREATE_MEMBER);
        }

        shareRoomRepository.delete(shareRoom);

        return "공유방이 삭제 되었습니다.";
    }


    // 공유방 코드 생성
    public String addShareCode() {

        SecureRandom secureRandom = new SecureRandom();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < 8; i++) {
            int randomIndex = secureRandom.nextInt(SHARE_USE_CODE.length());
            sb.append(SHARE_USE_CODE.charAt(randomIndex));
        }

        return sb.toString();
    }
}
