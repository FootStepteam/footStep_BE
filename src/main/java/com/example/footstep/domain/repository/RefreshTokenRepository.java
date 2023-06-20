package com.example.footstep.domain.repository;

import com.example.footstep.domain.entity.RefreshToken;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    @Query("select rt from RefreshToken rt "
        + "left join fetch rt.member "
        + "where rt.tokenValue = :refreshToken")
    Optional<RefreshToken> findByTokenValueWithMember(String refreshToken);
}
