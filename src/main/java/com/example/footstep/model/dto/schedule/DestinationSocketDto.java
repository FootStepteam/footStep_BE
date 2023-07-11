package com.example.footstep.model.dto.schedule;

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
public class DestinationSocketDto {

    private String messageType;
    private List<DayScheduleDto> dayScheduleDtoList;


    public static DestinationSocketDto from(List<DayScheduleDto> dayScheduleDto) {
        return DestinationSocketDto.builder()
            .messageType("destination")
            .dayScheduleDtoList(dayScheduleDto)
            .build();
    }
}
