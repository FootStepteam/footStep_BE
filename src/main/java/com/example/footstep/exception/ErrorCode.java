package com.example.footstep.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    // 회원
    NOT_FIND_MEMBER_ID("존재하지 않는 아이디 입니다."),


    // 공유방
    NOT_FIND_SHARE_ID("존재하지 않는 공유방 입니다."),
    NOT_MATCH_CREATE_MEMBER("공유방을 생성한 사용자가 아닙니다."),

    // 일정
    NOT_FIND_SCHEDULE_ID("존재하지 않는 일정 입니다."),

    // 게시글
    NOT_FIND_COMMUNITY_ID("존재하지 않는 일정 입니다."),
    ;

    private final String MESSAGE;
}
