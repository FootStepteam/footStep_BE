package com.example.footstep.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import org.hibernate.envers.AuditOverride;


@Entity
@AuditOverride(forClass = BaseTimeEntity.class)
public class ShareRoom extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long shareId;
    @Column(columnDefinition = "NVARCHAR(30) NOT NULL")
    private String shareName;
    @Column(columnDefinition = "NVARCHAR(255) NOT NULL")
    private String shareCode;


    @ManyToOne
    @JoinColumn(name = "memberId")
    private Member member;
}
