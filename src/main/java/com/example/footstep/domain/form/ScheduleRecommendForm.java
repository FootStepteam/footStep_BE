package com.example.footstep.domain.form;

import javax.validation.constraints.NotNull;
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
public class ScheduleRecommendForm {

    @NotNull(message = "여행일자를 선택하세요.")
    private String planDate;
    @NotNull(message = "추천 기준점을 선택하세요.")
    private String lng;
    @NotNull(message = "추천 기준점을 선택하세요.")
    private String lat;
}
