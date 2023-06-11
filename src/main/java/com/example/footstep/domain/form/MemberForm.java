package com.example.footstep.domain.form;

import com.example.footstep.authentication.oauth.OAuthProvider;
import com.example.footstep.domain.entity.Member;
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
public class MemberForm {

    private String loginEmail;
    private String nickname;
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
