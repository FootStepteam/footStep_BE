package com.example.footstep.model.form;

import com.example.footstep.authentication.oauth.OAuthProvider;
import com.example.footstep.model.entity.Member;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberForm {

    @NotNull(message = "로그인 이메일을 입력해 주세요")
    private String loginEmail;
    @NotNull(message = "닉네임을 입력해 주세요")
    private String nickname;
    @NotNull(message = "패스워드를 입력해 주세요")
    private String password;
    private String gender;


    public Member toEntity() {
        return Member.builder()
            .loginEmail(loginEmail)
            .nickname(nickname)
            .password(password)
            .img("default")
            .memberOAuth(OAuthProvider.MEMBER)
            .gender(gender)
            .build();
    }
}
