package com.example.footstep.model.dto.share_room;

import java.util.List;
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
public class ShareRoomListDto {

    private int totalPages;
    private List<ShareRoomDto> shareRoomDtoList;


    public static ShareRoomListDto of(List<ShareRoomDto> shareRoomDto, int totalPages) {

        return ShareRoomListDto.builder()
            .totalPages(totalPages)
            .shareRoomDtoList(shareRoomDto)
            .build();
    }
}
