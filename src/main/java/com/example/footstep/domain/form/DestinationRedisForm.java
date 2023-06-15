package com.example.footstep.domain.form;

import com.example.footstep.domain.entity.redis.DestinationRedis;
import lombok.Getter;

@Getter
public class DestinationRedisForm {

    private String planDate;
    private String content;
    private String destinationCategoryCode;
    private String destinationName;
    private String destinationAddress;
    private String lng;
    private String lat;
    private int seq;


    public DestinationRedis toEntityScheduleRedis(Long shareId) {
        return DestinationRedis.builder()
            .shareId(shareId)
            .planDate(planDate)
            .destinationCategoryCode(destinationCategoryCode)
            .content(content)
            .destinationName(destinationName)
            .destinationAddress(destinationAddress)
            .lng(lng)
            .lat(lat)
            .seq(seq)
            .build();
    }
}
