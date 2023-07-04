package com.example.footstep.model.form;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberUpdateForm {

    private String profileUrl;
    private String nickname;
    private String description;

}
