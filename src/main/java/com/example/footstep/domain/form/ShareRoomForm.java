package com.example.footstep.domain.form;

import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ShareRoomForm {

    @NotNull(message = "방 제목을 입력하세요.")
    private String shareName;
    @NotNull(message = "출발일을 선택하세요.")
    private String travelStartDate;
    @NotNull(message = "도착일을 선택하세요.")
    private String travelEndDate;
    private String imageUrl;
}
