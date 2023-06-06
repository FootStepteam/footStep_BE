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
public class Destination extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long destinationId;
    @Column(columnDefinition = "NVARCHAR(30) NOT NULL")
    private String destinationName;
    @Column(columnDefinition = "NVARCHAR(150) NOT NULL")
    private String destinationAddress;
    @Column(columnDefinition = "NVARCHAR(255) NOT NULL")
    private String startPoint;
    @Column(columnDefinition = "NUMERIC(12, 9) NOT NULL")
    private String lng;
    @Column(columnDefinition = "NUMERIC(12, 9) NOT NULL")
    private String lat;
    private int leadTime;
    private int seq;


    @ManyToOne
    @JoinColumn(name = "dayScheduleId")
    private DaySchedule daySchedule;

    @ManyToOne
    @JoinColumn(name = "shareId")
    private ShareRoom shareRoom;

    @ManyToOne
    @JoinColumn(name = "memberId")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "scheduleId")
    private Schedule schedule;
}
