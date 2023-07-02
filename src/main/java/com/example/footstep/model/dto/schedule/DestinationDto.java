package com.example.footstep.model.dto.schedule;

import com.example.footstep.model.entity.Destination;
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
public class DestinationDto {

    private Long destinationId;
    private String destinationCategoryCode;
    private String destinationName;
    private String destinationAddress;
    private String lng;
    private String lat;
    private int seq;


    public static DestinationDto from(Destination destination) {
        return DestinationDto.builder()
            .destinationId(destination.getDestinationId())
            .destinationCategoryCode(destination.getDestinationCategoryCode())
            .destinationName(destination.getDestinationName())
            .destinationAddress(destination.getDestinationAddress())
            .lng(destination.getLng())
            .lat(destination.getLat())
            .seq(destination.getSeq())
            .build();
    }
}
