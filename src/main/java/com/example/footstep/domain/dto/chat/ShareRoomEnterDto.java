package com.example.footstep.domain.dto.chat;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ShareRoomEnterDto {

    private Long shareId;
    private String shareName;


    public static ShareRoomEnterDto of(Long shareId, String shareName) {

        ShareRoomEnterDto shareRoomEnterDto = new ShareRoomEnterDto();

        shareRoomEnterDto.shareId = shareId;
        shareRoomEnterDto.shareName = shareName;

        return shareRoomEnterDto;
    }
}
