package com.example.footstep.domain.dto.share_room;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecommendDto {
    private String SearchKeyword;
    private List<TourListDto> imageList;

}
