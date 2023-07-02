package com.example.footstep.model.dto.schedule;

import com.example.footstep.model.entity.DaySchedule;
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
public class DayScheduleMemoDto {

    private Long shareId;
    private Long dayScheduleId;
    private String planDate;
    private String content;


    public static DayScheduleMemoDto from(DaySchedule daySchedule) {
        return DayScheduleMemoDto.builder()
            .shareId(daySchedule.getShareRoom().getShareId())
            .dayScheduleId(daySchedule.getDayScheduleId())
            .planDate(daySchedule.getPlanDate())
            .content(daySchedule.getContent())
            .build();
    }
}
