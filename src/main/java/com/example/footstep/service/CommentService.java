package com.example.footstep.service;

import com.example.footstep.domain.entity.Comment;
import com.example.footstep.domain.entity.Community;
import com.example.footstep.domain.entity.Member;
import com.example.footstep.domain.form.CommentCreateForm;
import com.example.footstep.domain.form.CommentUpdateForm;
import com.example.footstep.domain.repository.CommentRepository;
import com.example.footstep.domain.repository.CommunityRepository;
import com.example.footstep.domain.repository.MemberRepository;
import com.example.footstep.exception.ErrorCode;
import com.example.footstep.exception.GlobalException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final CommunityRepository communityRepository;
    private  final MemberRepository memberRepository;

    @Transactional
    public void create(Long memberId, Long communityId, CommentCreateForm commentCreateForm) {

        Community community = communityRepository.getCommunityById(communityId);
        Member member = memberRepository.getMemberById(memberId);

        Comment comment = commentCreateForm.toEntity(community, member);

        commentRepository.save(comment);

    }

    @Transactional
    public void update(Long memberId, Long commentId, CommentUpdateForm commentUpdateForm) {

        Comment comment = commentRepository.getCommentById(commentId);

        if(!comment.isWrittenBy(memberId)) {
            throw new GlobalException(ErrorCode.UN_MATCHED_MEMBER_AND_COMMENT);
        }

        comment.update(commentUpdateForm.getContent());

    }

    @Transactional
    public void delete(Long memberId, Long commentId) {

        Comment comment = commentRepository.getCommentById(commentId);

        if(!comment.isWrittenBy(memberId)) {
            throw new GlobalException(ErrorCode.UN_MATCHED_MEMBER_AND_COMMENT);
        }

        commentRepository.delete(comment);
    }
}
