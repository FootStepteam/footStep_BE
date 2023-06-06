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
public class DaySchedule extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long dayScheduleId;
    @Column(columnDefinition = "NVARCHAR(8) NOT NULL")
    private String planDate;
    @Column(columnDefinition = "NVARCHAR(255)")
    private String content;


    @ManyToOne
    @JoinColumn(name = "scheduleId")
    private Schedule schedule;

    @ManyToOne
    @JoinColumn(name = "shareId")
    private ShareRoom shareRoom;

    @ManyToOne
    @JoinColumn(name = "memberId")
    private Member member;
}
