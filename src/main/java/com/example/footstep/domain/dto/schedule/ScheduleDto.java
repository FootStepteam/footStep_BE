package com.example.footstep.domain.dto.schedule;

import com.example.footstep.domain.entity.DaySchedule;
import com.example.footstep.domain.entity.Destination;
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
public class ScheduleDto {

    private String planDate;
    private String content;
    private String destinationCategoryCode;
    private String destinationName;
    private String destinationAddress;
    private String lng;
    private String lat;
    private int seq;


    public static ScheduleDto from(DaySchedule daySchedule, Destination destination) {
        return ScheduleDto.builder()
            .planDate(daySchedule.getPlanDate())
            .content(daySchedule.getContent())
            .destinationCategoryCode(destination.getDestinationCategoryCode())
            .destinationName(destination.getDestinationName())
            .destinationAddress(destination.getDestinationAddress())
            .lng(destination.getLng())
            .lat(destination.getLat())
            .seq(destination.getSeq())
            .build();
    }
}
