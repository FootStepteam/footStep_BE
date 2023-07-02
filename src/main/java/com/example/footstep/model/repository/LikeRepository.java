package com.example.footstep.model.repository;

import com.example.footstep.model.entity.Likes;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface LikeRepository extends JpaRepository<Likes, Long> {

    @Query(value = "select community_id from Likes "
        + "where member_id = :memberId", nativeQuery = true)
    List<Long> findCommunityIds(Long memberId);

    boolean existsByCommunity_CommunityIdAndMember_MemberId(Long communityId, Long memberId);

    Optional<Likes> findByCommunity_CommunityIdAndMember_MemberId(Long communityId, Long memberId);
}
