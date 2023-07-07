package com.example.footstep.service;

import static com.example.footstep.exception.ErrorCode.ALREADY_DESTINATION;

import com.example.footstep.model.dto.schedule.DayScheduleDto;
import com.example.footstep.model.dto.schedule.DestinationDto;
import com.example.footstep.model.entity.DaySchedule;
import com.example.footstep.model.entity.Destination;
import com.example.footstep.model.entity.ShareRoom;
import com.example.footstep.model.form.DestinationForm;
import com.example.footstep.model.repository.DayScheduleRepository;
import com.example.footstep.model.repository.DestinationRepository;
import com.example.footstep.model.repository.ShareRoomRepository;
import com.example.footstep.exception.GlobalException;
import java.util.ArrayList;
import java.util.List;
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

            if (destinationRepository.existsByDaySchedule_ShareRoom_ShareIdAndDaySchedule_PlanDateAndLatAndLng(
                shareRoom.getShareId() ,destinationForm.getPlanDate(), destinationForm.getLat(),
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
    public void deleteDestinationPlanDate(Long shareId, String planDate) {

        ShareRoom shareRoom = shareRoomRepository.getShareById(shareId);

        List<Destination> destinationList =
            destinationRepository.findByDaySchedule_ShareRoom_ShareIdAndDaySchedule_PlanDate(
                shareRoom.getShareId(), planDate);

        destinationRepository.deleteAll(destinationList);
    }


    @Transactional
    public void deleteDestination(Long shareId, Long destinationId) {

        shareRoomRepository.getShareById(shareId);

        Destination destination = destinationRepository.getDestinationById(destinationId);

        destinationRepository.delete(destination);
    }


    @Transactional
    public List<DayScheduleDto> deleteDestinationMessage(Long shareId, Long destinationId) {

        ShareRoom shareRoom = shareRoomRepository.getShareById(shareId);

        Destination destination = destinationRepository.getDestinationById(destinationId);

        destinationRepository.delete(destination);

        List<DaySchedule> dayScheduleList =
            dayScheduleRepository.findByShareRoom_ShareIdAndPlanDateBetweenOrderByPlanDate(
                shareRoom.getShareId(), shareRoom.getTravelStartDate(),
                shareRoom.getTravelEndDate());

        List<DayScheduleDto> dayScheduleDtoList = new ArrayList<>();

        for (DaySchedule daySchedule : dayScheduleList) {
            DayScheduleDto dayScheduleDto = DayScheduleDto.from(daySchedule);

            dayScheduleDtoList.add(dayScheduleDto);
        }

        return dayScheduleDtoList;
    }
}
