package com.example.footstep.domain.dto.share_room;

import com.example.footstep.domain.entity.ShareRoom;
import java.util.ArrayList;
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

    private Long shareId;
    private String shareName;
    private String shareCode;
    private String startPoint;
    private String endPoint;
    private String travelStartDate;
    private String travelEndDate;
    private String imageUrl;


    public static List<ShareRoomListDto> of(List<ShareRoom> shareRooms) {
        List<ShareRoomListDto> shareRoomList = new ArrayList<>();

        for (ShareRoom shareRoom : shareRooms) {
            ShareRoomListDto shareRoomListDto = ShareRoomListDto.builder()
                .shareId(shareRoom.getShareId())
                .shareName(shareRoom.getShareName())
                .shareCode(shareRoom.getShareCode())
                .startPoint(shareRoom.getStartPoint())
                .endPoint(shareRoom.getEndPoint())
                .travelStartDate(shareRoom.getTravelStartDate())
                .travelEndDate(shareRoom.getTravelEndDate())
                .imageUrl(shareRoom.getImageUrl())
                .build();

            shareRoomList.add(shareRoomListDto);
        }

        return shareRoomList;
    }
}
