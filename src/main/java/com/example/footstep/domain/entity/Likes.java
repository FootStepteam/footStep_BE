package com.example.footstep.domain.entity;

import static javax.persistence.FetchType.LAZY;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.envers.AuditOverride;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@AuditOverride(forClass = BaseTimeEntity.class)
public class Likes extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long likesId;


    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "communityId")
    private Community community;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "memberId")
    private Member member;
}
