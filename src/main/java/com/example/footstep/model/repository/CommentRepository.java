package com.example.footstep.model.repository;

import com.example.footstep.model.entity.Comment;
import com.example.footstep.exception.ErrorCode;
import com.example.footstep.exception.GlobalException;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query(value = "select cm "
        + "from Comment cm "
        + "left join fetch cm.member "
        + "where cm.community.communityId = :communityId")
    List<Comment> findAllByCommunityIdWithMember(Long communityId);

    default Comment getCommentById(Long id) {
        return findById(id)
            .orElseThrow(() -> new GlobalException(ErrorCode.NOT_FIND_COMMENT_ID));
    }
}
