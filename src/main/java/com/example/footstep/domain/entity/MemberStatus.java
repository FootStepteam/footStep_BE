package com.example.footstep.domain.entity;

import lombok.Getter;

@Getter
public enum MemberStatus {

    NORMAL("정상 회원"),
    DELETE("탈퇴 회원"),
    ;

    MemberStatus(String status) {
        this.status = status;
    }

    private String status;

}
