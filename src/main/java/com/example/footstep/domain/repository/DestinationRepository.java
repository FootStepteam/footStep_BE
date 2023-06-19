package com.example.footstep.domain.repository;

import static com.example.footstep.exception.ErrorCode.NOT_FIND_DESTINATION_ID;

import com.example.footstep.domain.entity.Destination;
import com.example.footstep.exception.GlobalException;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DestinationRepository extends JpaRepository<Destination, Long> {

    Optional<Destination> findByDestinationId(Long destinationId);

    default Destination getDestinationById(Long destinationId) {
        return findByDestinationId(destinationId)
            .orElseThrow(() -> new GlobalException(NOT_FIND_DESTINATION_ID));
    }

    boolean existsByDaySchedule_PlanDateAndLatAndLng(String planDate, String lat, String lng);
}
