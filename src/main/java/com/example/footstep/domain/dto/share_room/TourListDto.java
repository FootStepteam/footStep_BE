package com.example.footstep.domain.dto.share_room;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TourListDto {
    @JsonProperty("galWebImageUrl")
    private String imageUrl;
    @JsonProperty("galPhotographyLocation")
    private String location;
    @JsonProperty("galTitle")
    private String title;
}
