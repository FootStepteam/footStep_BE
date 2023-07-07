package com.example.footstep.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "REFRESH_TOKEN_ID")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;
    private String tokenValue;


    public static RefreshToken createRefreshToken(Member member, String tokenValue) {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.member = member;
        refreshToken.tokenValue = tokenValue;
        return refreshToken;
    }
}
