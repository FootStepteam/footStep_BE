package com.example.footstep.domain.entity;

import static javax.persistence.FetchType.LAZY;

import javax.persistence.Column;
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
public class Community extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long communityId;
    @Column(columnDefinition = "NVARCHAR(30) NOT NULL")
    private String communityName;
    @Column(columnDefinition = "NVARCHAR(255) NOT NULL")
    private String content;
    @Column(columnDefinition = "CHAR(1) NOT NULL")
    private boolean communityPublicState;  // 게시글 공개 여부


    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "memberId")
    private Member member;
}
