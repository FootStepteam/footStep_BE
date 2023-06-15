package com.example.footstep.domain.dto.destination.redis;

import com.example.footstep.domain.entity.redis.DestinationRedis;
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
public class DestinationRedisDto {

    private Long id;
    private String destinationCategoryCode;
    private String destinationName;
    private String destinationAddress;
    private String lng;
    private String lat;
    private int seq;


    public static DestinationRedisDto from(DestinationRedis destinationRedis) {
        return DestinationRedisDto.builder()
            .id(destinationRedis.getId())
            .destinationCategoryCode(destinationRedis.getDestinationCategoryCode())
            .destinationName(destinationRedis.getDestinationName())
            .destinationAddress(destinationRedis.getDestinationAddress())
            .lng(destinationRedis.getLng())
            .lat(destinationRedis.getLat())
            .seq(destinationRedis.getSeq())
            .build();
    }
}
