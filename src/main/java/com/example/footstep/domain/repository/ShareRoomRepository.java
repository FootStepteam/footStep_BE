package com.example.footstep.domain.repository;

import com.example.footstep.domain.entity.ShareRoom;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShareRoomRepository extends JpaRepository<ShareRoom, Long> {

    List<ShareRoom> findByMember_MemberId(Long memberId, Pageable pageable);

    boolean existsByShareCode(String shareCode);

    Optional<ShareRoom> findByShareId(Long shareId);
}
