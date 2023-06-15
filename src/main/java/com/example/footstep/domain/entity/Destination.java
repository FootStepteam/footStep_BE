package com.example.footstep.domain.entity;

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
    @Column(columnDefinition = "NVARCHAR(30) NOT NULL")
    private String destinationName;
    @Column(columnDefinition = "NVARCHAR(150) NOT NULL")
    private String destinationAddress;
    @Column(columnDefinition = "NUMERIC(12, 9) NOT NULL")
    private String lng;
    @Column(columnDefinition = "NUMERIC(12, 9) NOT NULL")
    private String lat;
    private int seq;


    @ManyToOne
    @JoinColumn(name = "dayScheduleId")
    private DaySchedule daySchedule;
}
