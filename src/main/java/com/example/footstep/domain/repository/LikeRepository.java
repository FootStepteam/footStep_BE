package com.example.footstep.domain.repository;

import com.example.footstep.domain.entity.Likes;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface LikeRepository extends JpaRepository<Likes, Long> {

    @Query(value = "select community_id from Likes "
        + "where member_id = :memberId", nativeQuery = true)
    List<Long> findCommunityIds(Long memberId);

}
