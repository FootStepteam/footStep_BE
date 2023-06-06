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
public class Memo extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memoId;
    @Column(columnDefinition = "NVARCHAR(30) NOT NULL")
    private String memoTitle;
    @Column(columnDefinition = "TEST")
    private String memoContent;


    @ManyToOne
    @JoinColumn(name = "destinationId")
    private Destination destination;

    @ManyToOne
    @JoinColumn(name = "memberId")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "scheduleId")
    private Schedule schedule;

    @ManyToOne
    @JoinColumn(name = "shareId")
    private ShareRoom shareRoom;

    @ManyToOne
    @JoinColumn(name = "dayScheduleId")
    private DaySchedule daySchedule;
}
