package com.example.footstep.domain.repository;

import com.example.footstep.domain.entity.Member;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {



    Optional<Member> findByLoginEmail(String email);

    Optional<Member> findByMemberId(Long memberId);

}
