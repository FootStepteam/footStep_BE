package com.example.footstep.domain.dto.schedule;

import com.example.footstep.domain.entity.DaySchedule;
import com.example.footstep.domain.entity.Destination;
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
public class DayScheduleDto {

    private Long shareId;
    private Long dayScheduleId;
    private String planDate;
    private String content;
    private List<DestinationDto> destinationDtoList;


    public static DayScheduleDto from(DaySchedule daySchedule) {

        List<DestinationDto> destinationDtoList = new ArrayList<>();

        for (Destination destination : daySchedule.getDestinations()) {
            DestinationDto destinationDto = DestinationDto.from(destination);
            destinationDtoList.add(destinationDto);
        }

        return DayScheduleDto.builder()
            .shareId(daySchedule.getShareRoom().getShareId())
            .dayScheduleId(daySchedule.getDayScheduleId())
            .planDate(daySchedule.getPlanDate())
            .content(daySchedule.getContent())
            .destinationDtoList(destinationDtoList)
            .build();
    }
}
