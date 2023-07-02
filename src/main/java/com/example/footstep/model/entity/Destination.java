package com.example.footstep.model.entity;

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
public class Destination extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long destinationId;
    @Column(columnDefinition = "NVARCHAR(10) NOT NULL")
    private String destinationCategoryCode;
    @Column(columnDefinition = "NVARCHAR(30) NOT NULL")
    private String destinationName;
    @Column(columnDefinition = "NVARCHAR(150) NOT NULL")
    private String destinationAddress;
    @Column(columnDefinition = "NUMERIC(20, 17) NOT NULL")
    private String lng;
    @Column(columnDefinition = "NUMERIC(20, 17) NOT NULL")
    private String lat;
    private int seq;


    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "dayScheduleId")
    private DaySchedule daySchedule;
}
