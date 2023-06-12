package com.example.footstep.domain.form;

import com.example.footstep.authentication.oauth.OAuthProvider;
import com.example.footstep.domain.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberForm {


    @NotNull(message = "로그인 이메일을 입력해 주세요")
    private String loginEmail;
    @NotNull(message = "닉네임을 입력해 주세요")
    private String nickname;
    @NotNull(message = "패스워드를 입력해 주세요")
    private String password;
    private String gender;

    public Member from(MemberForm form){
        return Member.builder()
                .loginEmail(form.loginEmail)
                .nickname(form.nickname)
                .password(form.password)
                .img("default")
                .memberOAuth(OAuthProvider.MEMBER)
                .gender(form.gender)
                .build();
    }

}
