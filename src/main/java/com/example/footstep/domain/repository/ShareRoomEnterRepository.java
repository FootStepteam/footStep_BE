package com.example.footstep.domain.repository;

import com.example.footstep.domain.entity.ShareRoomEnter;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShareRoomEnterRepository extends JpaRepository<ShareRoomEnter, Long> {

    Optional<ShareRoomEnter> findByShareRoom_ShareIdAndMember_MemberId(Long shareId, Long memberId);
}
