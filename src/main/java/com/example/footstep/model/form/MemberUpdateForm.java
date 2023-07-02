package com.example.footstep.model.form;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberUpdateForm {

    private String profileUrl;
    private String nickname;
    private String description;
}
