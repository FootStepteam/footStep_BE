package com.example.footstep.domain.repository;

import com.example.footstep.domain.entity.Member;
import java.util.Optional;

import com.example.footstep.exception.ErrorCode;
import com.example.footstep.exception.GlobalException;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {



    Optional<Member> findByLoginEmail(String email);

    Optional<Member> findByMemberId(Long memberId);
    default Member getMemberById(Long memberId) {
        return findByMemberId(memberId)
                .orElseThrow(() -> new GlobalException(ErrorCode.NOT_FIND_MEMBER_ID));
    }

}
