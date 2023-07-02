package com.example.footstep.model.entity;

import lombok.Getter;

@Getter
public enum MemberStatus {

    NORMAL("정상 회원"),
    DELETE("탈퇴 회원"),
    ;


    private String status;
    MemberStatus(String status) {
        this.status = status;
    }

}
