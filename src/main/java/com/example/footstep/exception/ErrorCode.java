package com.example.footstep.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    // 회원
    NOT_FIND_MEMBER_ID("존재하지 않는 아이디 입니다."),
    WRONG_MEMBER_PASSWORD("패스워드가 일치 하지 않습니다."),
    ALREADY_MEMBER_EMAIL("이메일이 존재 합니다."),
    EMPTY_MEMBER_EMAIL("이메일을 입력해 주세요"),
    EMPTY_MEMBER_PASSWORD("패스워드를 입력해 주세요"),
    EMPTY_MEMBER_NICKNAME("닉네임을 입력 해주세요"),

    // 카카오API
    WRONG_ACCESS_TOKEN_AUTH("잘못된 인증입니다."),
    EMPTY_ACCESS_TOKEN("토큰이 비었습니다."),

    // 공유방
    NOT_FIND_SHARE_ID("존재하지 않는 공유방 입니다."),
    NOT_MATCH_CREATE_MEMBER("공유방을 생성한 사용자가 아닙니다."),

    // 일정
    NOT_FIND_DAY_SCHEDULE_ID("존재하지 않는 일정 입니다."),

    // 목적지
    NOT_FIND_DESTINATION_ID("존재하지 않는 목적지 입니다."),
    ALREADY_DESTINATION("등록된 목적지 입니다."),

    // 게시글
    NOT_FIND_COMMUNITY_ID("존재하지 않는 게시글 입니다."),
    UN_MATCHED_MEMBER_AND_COMMUNITY("해당 게시글의 작성자가 아닙니다."),

    // 댓글
    NOT_FIND_COMMENT_ID("존재하지 않는 댓글 입니다."),
    UN_MATCHED_MEMBER_AND_COMMENT("해당 댓글의 작성자가 아닙니다."),

    // 좋아요
    NOT_FIND_LIKE_COMMUNITY("회원이 좋아요한 게시글이 없습니다."),
    ;

    private final String MESSAGE;
}
