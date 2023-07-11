package com.example.footstep.model.dto.share_room;

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
public class ShareRoomSocketDto {

    private String messageType;
    private ShareRoomDto shareRoomDto;


    public static ShareRoomSocketDto from(ShareRoomDto shareRoomDto) {
        return ShareRoomSocketDto.builder()
            .messageType("shareRoom")
            .shareRoomDto(shareRoomDto)
            .build();
    }
}
