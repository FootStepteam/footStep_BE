package com.example.footstep.domain.repository;

import com.example.footstep.domain.entity.Community;
import com.example.footstep.exception.ErrorCode;
import com.example.footstep.exception.GlobalException;
import io.lettuce.core.dynamic.annotation.Param;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface CommunityRepository extends JpaRepository<Community, Long> {

    @Modifying(clearAutomatically = true)
    @Query("update Community c set c.likeCount = c.likeCount + 1 where c.communityId = :id")
    void increaseLikeCount(Long id);

    Slice<Community> findSliceBy(Pageable pageable);

    Optional<Community> findByCommunityIdAndMember_MemberId(Long communityId, Long memberId);

    @Query(value = "select c "
        + "from Community c "
        + "left join fetch c.member "
        + "left join fetch c.shareRoom "
        + "where c.communityId = :id")
    Optional<Community>findByIdWithShareRoomAndWriter(@Param("id") Long id);

    default Community getCommunityById(Long id) {
        return findById(id)
            .orElseThrow(() -> new GlobalException(ErrorCode.NOT_FIND_COMMUNITY_ID));
    }

}
