package com.example.footstep.model.repository;

import com.example.footstep.model.entity.Community;
import com.example.footstep.exception.ErrorCode;
import com.example.footstep.exception.GlobalException;
import com.example.footstep.model.entity.MemberStatus;
import io.lettuce.core.dynamic.annotation.Param;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface CommunityRepository extends JpaRepository<Community, Long> {

    @Modifying(clearAutomatically = true)
    @Query("update Community c set c.likeCount = c.likeCount + 1 where c.communityId = :id")
    void increaseLikeCount(Long id);

    @Modifying(clearAutomatically = true)
    @Query("update Community c set c.likeCount = c.likeCount - 1 where c.communityId = :id")
    void decreaseLikeCount(Long id);

    Optional<Community> findByCommunityIdAndMember_MemberId(Long communityId, Long memberId);

    @Query(value = "select c from Community c "
        + "left join fetch c.shareRoom "
        + "where c.communityId in :ids")
    List<Community> findCommunitiesLikedBy(List<Long> ids);

    @Query(value = "select c "
        + "from Community c "
        + "left join fetch c.member "
        + "left join fetch c.shareRoom "
        + "where c.communityId = :id")
    Optional<Community> findByIdWithShareRoomAndMember(@Param("id") Long id);

    @Query(value = "select c from Community c join fetch c.member m where m.memberStatus = :status",
        countQuery = "select count(c) from Community c join c.member m where m.memberStatus = :status")
    Page<Community> findAllByMemberStatus(MemberStatus status, Pageable pageable);


    default Community getCommunityById(Long id) {
        return findById(id)
            .orElseThrow(() -> new GlobalException(ErrorCode.NOT_FIND_COMMUNITY_ID));
    }
}
