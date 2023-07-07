package com.example.footstep.model.dto.share_room;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecommendDto {

    private String SearchKeyword;
    private List<TourListDto> imageList;
}
