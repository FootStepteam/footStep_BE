package com.example.footstep.service;

import com.example.footstep.domain.entity.ShareRoom;
import com.example.footstep.domain.entity.redis.DestinationRedis;
import com.example.footstep.domain.form.DestinationRedisForm;
import com.example.footstep.domain.repository.ShareRoomRepository;
import com.example.footstep.domain.repository.redis.ScheduleRedisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DestinationService {

    private final ShareRoomRepository shareRoomRepository;

    // Redis
    private final ScheduleRedisRepository scheduleRedisRepository;


    public DestinationRedis createDestination(
        Long shareId, DestinationRedisForm destinationRedisForm) {

        ShareRoom shareById = shareRoomRepository.getShareById(shareId);

        return scheduleRedisRepository.save(
            destinationRedisForm.toEntityScheduleRedis(shareById.getShareId()));
    }
}
