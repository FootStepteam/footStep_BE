package com.example.footstep.domain.form;

import com.example.footstep.domain.entity.DaySchedule;
import com.example.footstep.domain.entity.ShareRoom;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DayScheduleForm {

    @NotNull(message = "여행일자를 선택하세요.")
    private String planDate;
    @Size(min = 2, message = "2자 이상 입력하세요.")
    private String content;


    public DaySchedule toEntity(ShareRoom shareRoom) {
        return DaySchedule.builder()
            .planDate(planDate)
            .content(content)
            .shareRoom(shareRoom)
            .build();
    }
}
