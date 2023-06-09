package com.example.footstep.domain.dto;

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
public class ShareRoomDto {

    private Long shareId;
    private String shareName;
    private String shareCode;
    private String travelStartDate;
    private String travelEndDate;
    private String imageUrl;
}
