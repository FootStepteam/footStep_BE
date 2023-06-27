package com.example.footstep.domain.entity;

import static javax.persistence.FetchType.LAZY;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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
public class DaySchedule extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long dayScheduleId;
    @Column(columnDefinition = "NVARCHAR(10) NOT NULL")
    private String planDate;
    @Column(columnDefinition = "TEXT")
    private String content;


    @OneToMany(mappedBy = "daySchedule", cascade = CascadeType.REMOVE)
    private List<Destination> destinations;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "shareId")
    private ShareRoom shareRoom;
}
