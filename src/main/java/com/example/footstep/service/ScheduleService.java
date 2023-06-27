package com.example.footstep.service;

import static com.example.footstep.exception.ErrorCode.NOT_MATCH_CREATE_MEMBER;

import com.example.footstep.component.security.LoginMember;
import com.example.footstep.domain.dto.schedule.DayScheduleDto;
import com.example.footstep.domain.dto.schedule.DayScheduleMemoDto;
import com.example.footstep.domain.dto.schedule.DestinationDto;
import com.example.footstep.domain.entity.DaySchedule;
import com.example.footstep.domain.entity.Destination;
import com.example.footstep.domain.entity.ShareRoom;
import com.example.footstep.domain.form.DayScheduleForm;
import com.example.footstep.domain.form.ScheduleRecommendForm;
import com.example.footstep.domain.repository.DayScheduleRepository;
import com.example.footstep.domain.repository.DestinationRepository;
import com.example.footstep.domain.repository.ShareRoomRepository;
import com.example.footstep.exception.ErrorCode;
import com.example.footstep.exception.GlobalException;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ShareRoomRepository shareRoomRepository;
    private final DayScheduleRepository dayScheduleRepository;
    private final DestinationRepository destinationRepository;


    @Transactional(readOnly = true)
    public List<DayScheduleDto> getAllListSchedule(Long shareId, String startDate, String endDate) {

        ShareRoom shareRoom = shareRoomRepository.getShareById(shareId);

        List<DaySchedule> dayScheduleList =
            dayScheduleRepository.findByShareRoom_ShareIdAndPlanDateBetweenOrderByPlanDate(
                shareRoom.getShareId(), startDate, endDate);

        List<DayScheduleDto> dayScheduleDtoList = new ArrayList<>();

        for (DaySchedule daySchedule : dayScheduleList) {
            DayScheduleDto dayScheduleDto = DayScheduleDto.from(daySchedule);

            dayScheduleDtoList.add(dayScheduleDto);
        }

        return dayScheduleDtoList;
    }


    @Transactional(readOnly = true)
    public DayScheduleDto getAllListScheduleDate(Long shareId, String planDate) {

        ShareRoom shareRoom = shareRoomRepository.getShareById(shareId);

        DaySchedule daySchedule =
            dayScheduleRepository.findByShareRoom_ShareIdAndPlanDate(
                shareRoom.getShareId(), planDate).orElse(null);

        return daySchedule != null ? DayScheduleDto.from(daySchedule) : null;
    }


    @Transactional
    public DayScheduleMemoDto createOrUpdateScheduleMemo(
        LoginMember loginMember, Long shareId, DayScheduleForm dayScheduleForm) {

        ShareRoom shareRoom = shareRoomRepository.getShareById(shareId);

        if (!isShareRoomManager(loginMember, shareRoom)) {
            throw new GlobalException(NOT_MATCH_CREATE_MEMBER);
        }

        DaySchedule daySchedule = dayScheduleRepository.findByShareRoom_ShareIdAndPlanDate(
            shareRoom.getShareId(), dayScheduleForm.getPlanDate()).orElseGet(() ->
            dayScheduleRepository.save(dayScheduleForm.toEntity(shareRoom)));

        daySchedule.setContent(dayScheduleForm.getContent());

        return DayScheduleMemoDto.from(daySchedule);
    }


    @Transactional
    public List<DestinationDto> updateScheduleRecommend(
        Long shareId, ScheduleRecommendForm recommendForm) {

        ShareRoom shareRoom = shareRoomRepository.getShareById(shareId);

        Destination startDestination =
            destinationRepository.findByDaySchedule_PlanDateAndLatAndLng(
                    recommendForm.getPlanDate(), recommendForm.getLat(), recommendForm.getLng())
                .orElseThrow(() -> new GlobalException(ErrorCode.NOT_FIND_DESTINATION_ID));

        List<Destination> destinationList =
            destinationRepository.findByDaySchedule_ShareRoom_ShareIdAndDaySchedule_PlanDate(
                shareRoom.getShareId(), recommendForm.getPlanDate());

        List<Destination> recommendList = new ArrayList<>();

        Destination currentDestination = startDestination;
        recommendList.add(currentDestination);

        while (recommendList.size() < destinationList.size()) {
            double minDistance = Double.MAX_VALUE;
            Destination addDestination = null;

            for (Destination nextDestination : destinationList) {
                if (!recommendList.contains(nextDestination)) {
                    double distance = distanceTo(currentDestination, nextDestination);
                    if (distance < minDistance) {
                        minDistance = distance;
                        addDestination = nextDestination;
                    }
                }
            }

            if (addDestination == null) {
                break;
            }
            recommendList.add(addDestination);
            currentDestination = addDestination;
        }

        int seqCount = 1;
        List<DestinationDto> recommendDestinationList = new ArrayList<>();

        for (Destination recommendDestination : recommendList) {
            recommendDestination.setSeq(seqCount++);
            recommendDestinationList.add(DestinationDto.from(recommendDestination));
        }

        return recommendDestinationList;
    }


    @Transactional
    public void deleteOutsideSchedule(LoginMember loginMember, Long shareId) {

        ShareRoom shareRoom = shareRoomRepository.getShareById(shareId);

        if (!isShareRoomManager(loginMember, shareRoom)) {
            throw new GlobalException(NOT_MATCH_CREATE_MEMBER);
        }

        List<DaySchedule> planDateNotBetween = dayScheduleRepository.findPlanDateNotBetween(
            shareRoom.getShareId(), shareRoom.getTravelStartDate(), shareRoom.getTravelEndDate());

        dayScheduleRepository.deleteAll(planDateNotBetween);
    }


    // 경도, 위도 거리
    public double distanceTo(Destination currentDestination, Destination nextDestination) {

        double earthRadius = 6371;

        double currentLng = Math.toRadians(Double.parseDouble(currentDestination.getLng()));
        double currentLat = Math.toRadians(Double.parseDouble(currentDestination.getLat()));
        double nextLng = Math.toRadians(Double.parseDouble(nextDestination.getLng()));
        double nextLat = Math.toRadians(Double.parseDouble(nextDestination.getLat()));

        double lng = nextLng - currentLng;
        double lat = nextLat - currentLat;

        // Haversine
        double firstHaversine =
            Math.pow(Math.sin(lat / 2), 2) + Math.cos(currentLat) *
                Math.cos(nextLat) * Math.pow(Math.sin(lng / 2), 2);

        double finalHaversine =
            2 * Math.atan2(Math.sqrt(firstHaversine), Math.sqrt(1 - firstHaversine));

        return finalHaversine * earthRadius;
    }


    public boolean isShareRoomManager(LoginMember loginMember, ShareRoom shareRoom) {
        return loginMember.getMemberId().equals(shareRoom.getMember().getMemberId());
    }
}
