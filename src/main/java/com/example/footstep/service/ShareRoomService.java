package com.example.footstep.service;

import com.example.footstep.domain.dto.ShareRoomDto;
import com.example.footstep.domain.dto.ShareRoomListDto;
import com.example.footstep.domain.form.ShareRoomForm;
import com.example.footstep.domain.form.ShareRoomPageForm;
import java.util.List;

/*
**
파  일  명 : ShareRoomService
제작  연도 : 2023.06.07
작  성  자 : 오동건
개발 HISTORY
DATE                  AUTHOR                DESCRIPTION
------------------+--------------------+--------------------------------------------
2023.06.07            오동건                 개발
------------------+--------------------+--------------------------------------------
*/
public interface ShareRoomService {

    // 공유방 리스트 조회
    List<ShareRoomListDto> searchListShareRoom(Long memberId, ShareRoomPageForm shareRoomPageForm);

    // 공유방 조회
    ShareRoomDto searchShareRoom(Long memberId, Long shareId);

    // 공유방 생성
    ShareRoomDto addShareRoom(Long memberId, ShareRoomForm shareRoomForm);

    // 공유방 수정
    ShareRoomDto modifyShareRoom(Long memberId, Long shareId, ShareRoomForm shareRoomForm);

    // 공유방 삭제
    String deleteShareRoom(Long memberId, Long shareId);
}
