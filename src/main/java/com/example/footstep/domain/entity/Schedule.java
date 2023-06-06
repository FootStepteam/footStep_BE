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
public class Schedule extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long scheduleId;
    @Column(columnDefinition = "NVARCHAR(255)")
    private String startPoint;
    @Column(columnDefinition = "NVARCHAR(255)")
    private String endPoint;
    @Column(columnDefinition = "NVARCHAR(8) NOT NULL")
    private String travelStartDate;
    @Column(columnDefinition = "NVARCHAR(8) NOT NULL")
    private String travelEndDate;


    @ManyToOne
    @JoinColumn(name = "shareId")
    private ShareRoom shareRoom;

    @ManyToOne
    @JoinColumn(name = "memberId")
    private Member member;
}
