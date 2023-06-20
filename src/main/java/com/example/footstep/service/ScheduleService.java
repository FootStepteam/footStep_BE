package com.example.footstep.service;

import static com.example.footstep.exception.ErrorCode.NOT_FIND_DAY_SCHEDULE_ID;

import com.example.footstep.component.security.LoginMember;
import com.example.footstep.domain.dto.schedule.DayScheduleDto;
import com.example.footstep.domain.dto.schedule.DayScheduleMemoDto;
import com.example.footstep.domain.entity.DaySchedule;
import com.example.footstep.domain.entity.ShareRoom;
import com.example.footstep.domain.form.DayScheduleForm;
import com.example.footstep.domain.repository.DayScheduleRepository;
import com.example.footstep.domain.repository.MemberRepository;
import com.example.footstep.domain.repository.ShareRoomRepository;
import com.example.footstep.exception.GlobalException;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final MemberRepository memberRepository;
    private final ShareRoomRepository shareRoomRepository;
    private final DayScheduleRepository dayScheduleRepository;


    @Transactional(readOnly = true)
    public List<DayScheduleDto> getAllListSchedule(Long shareId) {

        ShareRoom shareRoom = shareRoomRepository.getShareById(shareId);

        List<DaySchedule> dayScheduleList =
            dayScheduleRepository.findByShareRoom_ShareIdOrderByPlanDate(shareRoom.getShareId());

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
                    shareRoom.getShareId(), planDate)
                .orElseThrow(() -> new GlobalException(NOT_FIND_DAY_SCHEDULE_ID));

        return DayScheduleDto.from(daySchedule);
    }


    @Transactional
    public DayScheduleMemoDto createOrUpdateScheduleMemo(
        LoginMember loginMember, Long shareId, DayScheduleForm dayScheduleForm) {

        memberRepository.getMemberById(loginMember.getMemberId());

        ShareRoom shareRoom = shareRoomRepository.getShareById(shareId);

        DaySchedule daySchedule = dayScheduleRepository.findByShareRoom_ShareIdAndPlanDate(
            shareRoom.getShareId(), dayScheduleForm.getPlanDate()).orElseGet(() ->
            dayScheduleRepository.save(dayScheduleForm.toEntity(shareRoom)));

        daySchedule.setContent(dayScheduleForm.getContent());

        return DayScheduleMemoDto.from(daySchedule);
    }
}
