package com.example.footstep.service;

import com.example.footstep.domain.entity.redis.DestinationRedis;
import com.example.footstep.domain.form.DestinationRedisForm;
import com.example.footstep.domain.repository.MemberRepository;
import com.example.footstep.domain.repository.ShareRoomRepository;
import com.example.footstep.domain.repository.redis.ScheduleRedisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DestinationService {

    private final MemberRepository memberRepository;
    private final ShareRoomRepository shareRoomRepository;

    // Redis
    private final ScheduleRedisRepository scheduleRedisRepository;


    public DestinationRedis createDestination(
        Long memberId, Long shareId, DestinationRedisForm destinationRedisForm) {

        memberRepository.getMemberById(memberId);

        shareRoomRepository.getShareById(shareId);

        return scheduleRedisRepository.save(destinationRedisForm.toEntityScheduleRedis(shareId));
    }
}
