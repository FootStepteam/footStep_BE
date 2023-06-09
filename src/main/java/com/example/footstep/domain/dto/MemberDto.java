package com.example.footstep.domain.dto;

import lombok.*;

import javax.persistence.Column;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberDto {
    @Column(columnDefinition = "NVARCHAR(30) NOT NULL")
    private String loginEmail;
    @Column(columnDefinition = "NVARCHAR(30) NOT NULL")
    private String nickname;
    @Column(columnDefinition = "NVARCHAR(255) NOT NULL")
    private String password;
    @Column(columnDefinition = "CHAR(1) NOT NULL")
    private String gender;

}
