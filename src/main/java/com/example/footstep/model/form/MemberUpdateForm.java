package com.example.footstep.model.form;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberUpdateForm {

    private String profileUrl;
    private String nickname;
    private String description;
    private MultipartFile file;

}
