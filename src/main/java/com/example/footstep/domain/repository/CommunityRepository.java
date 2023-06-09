package com.example.footstep.domain.repository;

import com.example.footstep.domain.entity.Community;
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

}
