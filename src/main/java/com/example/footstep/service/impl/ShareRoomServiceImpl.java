package com.example.footstep.service.impl;

import com.example.footstep.domain.dto.ShareRoomDto;
import com.example.footstep.domain.dto.ShareRoomListDto;
import com.example.footstep.domain.entity.Member;
import com.example.footstep.domain.entity.ShareRoom;
import com.example.footstep.domain.form.ShareRoomForm;
import com.example.footstep.domain.form.ShareRoomPageForm;
import com.example.footstep.domain.repository.MemberRepository;
import com.example.footstep.domain.repository.ShareRoomRepository;
import com.example.footstep.exception.ErrorCode;
import com.example.footstep.exception.GlobalException;
import com.example.footstep.service.ShareRoomService;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/*
**
파  일  명 : ShareRoomServiceImpl
제작  연도 : 2023.06.07
작  성  자 : 오동건
개발 HISTORY
DATE                  AUTHOR                DESCRIPTION
------------------+--------------------+--------------------------------------------
2023.06.07            오동건                 개발
2023.06.08            오동건                 Schedule 테이블 삭제로 인한 코드 수정
                                            imageUrl 추가
------------------+--------------------+--------------------------------------------
*/
@Service
@RequiredArgsConstructor
public class ShareRoomServiceImpl implements ShareRoomService {

    private final MemberRepository memberRepository;
    private final ShareRoomRepository shareRoomRepository;
    private final PasswordEncoder passwordEncoder;


    // 공유방 코드에 사용되는 문자
    private static final String SHARE_USE_CODE = "abcdefghijklmnopqrstuvwxyz"
        + "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
        + "0123456789"
        + "!@#$%^&*()_-+=[]{}<>?";


    // 공유방 리스트 조회
    public List<ShareRoomListDto> searchListShareRoom(Long memberId,
        ShareRoomPageForm shareRoomPageForm) {

        Member member = memberRepository.findByMemberId(memberId)
            .orElseThrow(() -> new GlobalException(ErrorCode.NOT_FIND_MEMBER_ID));

        Pageable pageable = PageRequest.of(shareRoomPageForm.getPage(),
            shareRoomPageForm.getSize());

        List<ShareRoomListDto> shareRoomList = new ArrayList<>();

        for (ShareRoom shareRoom :
            shareRoomRepository.findByMember_MemberId(member.getMemberId(), pageable)) {
            ShareRoomListDto shareRoomListDto = ShareRoomListDto.builder()
                .shareId(shareRoom.getShareId())
                .shareName(shareRoom.getShareName())
                .shareCode(shareRoom.getShareCode())
                .startPoint(shareRoom.getStartPoint())
                .endPoint(shareRoom.getEndPoint())
                .travelStartDate(shareRoom.getTravelStartDate())
                .travelEndDate(shareRoom.getTravelEndDate())
                .imageUrl(shareRoom.getImageUrl())
                .build();

            shareRoomList.add(shareRoomListDto);
        }

        return shareRoomList;
    }


    // 공유방 조회
    public ShareRoomDto searchShareRoom(Long memberId, Long shareId) {

        Member member = memberRepository.findByMemberId(memberId)
            .orElseThrow(() -> new GlobalException(ErrorCode.NOT_FIND_MEMBER_ID));

        ShareRoom shareRoom = shareRoomRepository.findByShareId(shareId)
            .orElseThrow(() -> new GlobalException(ErrorCode.NOT_FIND_SHARE_ID));

        if (!member.getMemberId().equals(shareRoom.getMember().getMemberId())) {
            throw new GlobalException(ErrorCode.NOT_MATCH_CREATE_MEMBER);
        }

        return ShareRoomDto.builder()
            .shareId(shareRoom.getShareId())
            .shareName(shareRoom.getShareName())
            .shareCode(shareRoom.getShareCode())
            .travelStartDate(shareRoom.getTravelStartDate())
            .travelEndDate(shareRoom.getTravelEndDate())
            .imageUrl(shareRoom.getImageUrl())
            .build();
    }


    // 공유방 생성
    @Transactional
    public ShareRoomDto addShareRoom(Long memberId, ShareRoomForm shareRoomForm) {

        Member member = memberRepository.findByMemberId(memberId)
            .orElseThrow(() -> new GlobalException(ErrorCode.NOT_FIND_MEMBER_ID));

        // 공유코드
        String shareCode = "";

        // 생성된 공유코드가 다른 방에 없으면 while 문 종료
        while (true) {
            if (!shareRoomRepository.existsByShareCode(addShareCode())) {
                shareCode = addShareCode();
                break;
            }
        }

        // 공유방 저장
        ShareRoom shareRoom = shareRoomRepository.save(ShareRoom.builder()
            .shareName(shareRoomForm.getShareName())
            .shareCode(passwordEncoder.encode(shareCode))
            .travelStartDate(shareRoomForm.getTravelStartDate())
            .travelEndDate(shareRoomForm.getTravelEndDate())
            .build());

        member.getShareRooms().add(shareRoom);

        return ShareRoomDto.builder()
            .shareId(shareRoom.getShareId())
            .shareName(shareRoom.getShareName())
            .shareCode(shareRoom.getShareCode())
            .travelStartDate(shareRoom.getTravelStartDate())
            .travelEndDate(shareRoom.getTravelEndDate())
            .imageUrl(shareRoom.getImageUrl())
            .build();
    }


    // 공유방 수정
    @Transactional
    public ShareRoomDto modifyShareRoom(Long memberId, Long shareId, ShareRoomForm shareRoomForm) {

        Member member = memberRepository.findByMemberId(memberId)
            .orElseThrow(() -> new GlobalException(ErrorCode.NOT_FIND_MEMBER_ID));

        ShareRoom shareRoom = shareRoomRepository.findByShareId(shareId)
            .orElseThrow(() -> new GlobalException(ErrorCode.NOT_FIND_SHARE_ID));

        if (!member.getMemberId().equals(shareRoom.getMember().getMemberId())) {
            throw new GlobalException(ErrorCode.NOT_MATCH_CREATE_MEMBER);
        }

        // 공유방 수정
        shareRoom.setShareName(shareRoomForm.getShareName());
        shareRoom.setTravelStartDate(shareRoomForm.getTravelStartDate());
        shareRoom.setTravelEndDate(shareRoomForm.getTravelEndDate());
        shareRoom.setImageUrl(shareRoomForm.getImageUrl());

        return ShareRoomDto.builder()
            .shareId(shareRoom.getShareId())
            .shareName(shareRoom.getShareName())
            .shareCode(shareRoom.getShareCode())
            .travelStartDate(shareRoom.getTravelStartDate())
            .travelEndDate(shareRoom.getTravelEndDate())
            .imageUrl(shareRoom.getImageUrl())
            .build();
    }


    // 공유방 삭제
    public String deleteShareRoom(Long memberId, Long shareId) {
        Member member = memberRepository.findByMemberId(memberId)
            .orElseThrow(() -> new GlobalException(ErrorCode.NOT_FIND_MEMBER_ID));

        ShareRoom shareRoom = shareRoomRepository.findByShareId(shareId)
            .orElseThrow(() -> new GlobalException(ErrorCode.NOT_FIND_SHARE_ID));

        if (!member.getMemberId().equals(shareRoom.getMember().getMemberId())) {
            throw new GlobalException(ErrorCode.NOT_MATCH_CREATE_MEMBER);
        }

        shareRoomRepository.delete(shareRoom);

        return "공유방이 삭제 되었습니다.";
    }


    // 공유코드 생성
    public String addShareCode() {

        SecureRandom secureRandom = new SecureRandom();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < 50; i++) {
            int randomIndex = secureRandom.nextInt(SHARE_USE_CODE.length());
            sb.append(SHARE_USE_CODE.charAt(randomIndex));
        }

        return sb.toString();
    }
}
