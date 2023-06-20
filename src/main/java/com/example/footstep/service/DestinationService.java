package com.example.footstep.service;

import static com.example.footstep.exception.ErrorCode.ALREADY_DESTINATION;

import com.example.footstep.domain.dto.schedule.DestinationDto;
import com.example.footstep.domain.entity.DaySchedule;
import com.example.footstep.domain.entity.Destination;
import com.example.footstep.domain.entity.ShareRoom;
import com.example.footstep.domain.form.DestinationForm;
import com.example.footstep.domain.repository.DayScheduleRepository;
import com.example.footstep.domain.repository.DestinationRepository;
import com.example.footstep.domain.repository.ShareRoomRepository;
import com.example.footstep.exception.GlobalException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DestinationService {

    private final ShareRoomRepository shareRoomRepository;
    private final DayScheduleRepository dayScheduleRepository;
    private final DestinationRepository destinationRepository;
    private final Lock lock = new ReentrantLock();


    @Transactional
    public DestinationDto createDestination(Long shareId, DestinationForm destinationForm) {

        ShareRoom shareRoom = shareRoomRepository.getShareById(shareId);
        DaySchedule daySchedule;

        try {
            lock.lock();

            // 일별일정이 있다면 정보조회 없다면 생성
            daySchedule = dayScheduleRepository.findByShareRoom_ShareIdAndPlanDate(
                shareRoom.getShareId(), destinationForm.getPlanDate()).orElseGet(() ->
                dayScheduleRepository.save(destinationForm.toEntityDaySchedule(shareRoom)));

            if (destinationRepository.existsByDaySchedule_PlanDateAndLatAndLng(
                destinationForm.getPlanDate(), destinationForm.getLat(),
                destinationForm.getLng())) {
                throw new GlobalException(ALREADY_DESTINATION);
            }

        } finally {
            lock.unlock();
        }

        Destination destination =
            destinationRepository.save(destinationForm.toEntityDestination(daySchedule));

        return DestinationDto.from(destination);
    }


    @Transactional
    public String deleteDestination(Long shareId, Long destinationId) {

        shareRoomRepository.getShareById(shareId);

        Destination destination = destinationRepository.getDestinationById(destinationId);

        destinationRepository.delete(destination);

        return "목적지가 삭제 되었습니다.";
    }
}
