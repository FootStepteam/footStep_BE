package com.example.footstep.domain.entity.redis;

import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@RedisHash(value = "scheduleRedis")
public class DestinationRedis {

    @Id
    private Long id;
    @Indexed
    private Long shareId;
    @Indexed
    private String planDate;
    private String content;
    private String destinationCategoryCode;
    private String destinationName;
    private String destinationAddress;
    private String lng;
    private String lat;
    private int seq;
}