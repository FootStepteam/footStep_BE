package com.example.footstep.domain.repository;

import com.example.footstep.domain.entity.Comment;
import com.example.footstep.domain.entity.Community;
import com.example.footstep.exception.ErrorCode;
import com.example.footstep.exception.GlobalException;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    default Comment getCommentById(Long id) {
        return findById(id)
            .orElseThrow(() -> new GlobalException(ErrorCode.NOT_FIND_COMMENT_ID));
    }

}
