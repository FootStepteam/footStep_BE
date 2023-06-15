package com.example.footstep.domain.dto.destination.redis;

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
public class DestinationRedisListDto {

    private Long shareId;
    private String planDate;
    List<DestinationRedisDto> destinationRedisInfo;


    public static DestinationRedisListDto from(
        Long shareId, String planDate, List<DestinationRedisDto> destinationRedisInfo) {

        return DestinationRedisListDto.builder()
            .shareId(shareId)
            .planDate(planDate)
            .destinationRedisInfo(destinationRedisInfo)
            .build();
    }
}
