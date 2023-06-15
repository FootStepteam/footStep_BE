package com.example.footstep.service;

import com.example.footstep.domain.dto.destination.redis.DestinationRedisDto;
import com.example.footstep.domain.dto.destination.redis.DestinationRedisListDto;
import com.example.footstep.domain.entity.ShareRoom;
import com.example.footstep.domain.entity.redis.DestinationRedis;
import com.example.footstep.domain.form.DestinationRedisForm;
import com.example.footstep.domain.repository.ShareRoomRepository;
import com.example.footstep.domain.repository.redis.ScheduleRedisRepository;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DestinationService {

    private final ShareRoomRepository shareRoomRepository;

    // Redis
    private final ScheduleRedisRepository scheduleRedisRepository;


    public List<DestinationRedisListDto> getAllListDestination(Long shareId) {

        ShareRoom shareRoomId = shareRoomRepository.getShareById(shareId);

        List<DestinationRedis> destinationRedisList =
            scheduleRedisRepository.findByShareIdOrderByPlanDateAscSeq(shareRoomId.getShareId());

        Map<String, List<DestinationRedisDto>> mapDestinationRedis = new HashMap<>();
        for (DestinationRedis destinationRedis : destinationRedisList) {
            String planDate = destinationRedis.getPlanDate();
            // 키에 값이 있다면 값을 반환, 없다면 생성
            mapDestinationRedis.computeIfAbsent(planDate, destinationRedisData -> new ArrayList<>())
                .add(DestinationRedisDto.from(destinationRedis));
        }

        List<DestinationRedisListDto> destinationRedisDateList = new ArrayList<>();
        for (Map.Entry<String, List<DestinationRedisDto>> entry : mapDestinationRedis.entrySet()) {
            String planDate = entry.getKey();

            destinationRedisDateList.add(
                DestinationRedisListDto.from(shareId, planDate, entry.getValue()));
        }

        destinationRedisDateList.sort(Comparator.comparing(DestinationRedisListDto::getPlanDate));

        return destinationRedisDateList;
    }


    public DestinationRedisListDto getAllListDestinationDate(Long shareId, String planDate) {

        ShareRoom shareRoomId = shareRoomRepository.getShareById(shareId);

        List<DestinationRedisDto> destinationRedisInfo = new ArrayList<>();

        List<DestinationRedis> destinationRedisList =
            scheduleRedisRepository.findByShareIdAndPlanDateOrderBySeq(
                shareRoomId.getShareId(), planDate);

        for (DestinationRedis destinationRedis : destinationRedisList) {
            destinationRedisInfo.add(DestinationRedisDto.from(destinationRedis));
        }

        return DestinationRedisListDto.from(shareId, planDate, destinationRedisInfo);
    }


    public DestinationRedis createDestination(
        Long shareId, DestinationRedisForm destinationRedisForm) {

        ShareRoom shareRoomId = shareRoomRepository.getShareById(shareId);

        return scheduleRedisRepository.save(
            destinationRedisForm.toEntityScheduleRedis(shareRoomId.getShareId()));
    }
}
