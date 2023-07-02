package com.example.footstep.model.repository;

import com.example.footstep.model.entity.ShareRoomEnter;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShareRoomEnterRepository extends JpaRepository<ShareRoomEnter, Long> {

    List<ShareRoomEnter> findByMember_MemberId(Long memberId, Pageable pageable);

    Optional<ShareRoomEnter> findByShareRoomEnterId(Long shareRoomEnterId);

    Optional<ShareRoomEnter> findByShareRoom_ShareIdAndMember_MemberId(Long shareId, Long memberId);
}
