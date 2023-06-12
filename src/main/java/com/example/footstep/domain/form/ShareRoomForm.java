package com.example.footstep.domain.form;

import com.example.footstep.domain.entity.ShareRoom;
import javax.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class ShareRoomForm {

    @NotNull(message = "방 제목을 입력하세요.")
    private String shareName;
    @NotNull(message = "출발일을 선택하세요.")
    private String travelStartDate;
    @NotNull(message = "도착일을 선택하세요.")
    private String travelEndDate;
    private String imageUrl;


    public ShareRoom toEntity(String shareCode) {
        return ShareRoom.builder()
            .shareName(shareName)
            .shareCode(shareCode)
            .travelStartDate(travelStartDate)
            .travelEndDate(travelEndDate)
            .imageUrl(imageUrl)
            .build();
    }

}
