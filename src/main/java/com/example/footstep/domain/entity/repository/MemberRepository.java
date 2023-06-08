package com.example.footstep.domain.entity.repository;

import com.example.footstep.domain.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> existsByLoginEmail(String email);
    Optional<Member> findByLoginEmail(String email);
}
