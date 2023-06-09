package com.example.footstep.domain.repository;

import com.example.footstep.domain.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> existsByLoginEmail(String email);
    Optional<Member> findByLoginEmail(String email);
    Optional<Member> findByMemberId(Long memberId);

}
