package com.example.footstep.domain.repository;

import com.example.footstep.domain.entity.DaySchedule;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DayScheduleRepository extends JpaRepository<DaySchedule, Long> {

    List<DaySchedule> findByShareRoom_ShareIdAndPlanDateBetweenOrderByPlanDate(
        Long shareId, String startDate, String endDate);

    Optional<DaySchedule> findByShareRoom_ShareIdAndPlanDate(Long shareId, String planDate);
}
