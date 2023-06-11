package com.example.footstep.domain.dto;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginDto {

    @NotNull(message = "로그인 이메일을 작성해주세요")
    private String loginEmail;
    @NotNull(message = "패스워드를 입력해주세요")
    private String password;
}
