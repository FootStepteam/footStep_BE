package com.example.footstep.domain.dto;

import lombok.*;

import javax.persistence.Column;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginDto {
    @Column(columnDefinition = "NVARCHAR(30) NOT NULL")
    private String loginEmail;

    @Column(columnDefinition = "NVARCHAR(255) NOT NULL")
    private String password;
}
