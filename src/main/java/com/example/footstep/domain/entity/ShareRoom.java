package com.example.footstep.domain.entity;

import static javax.persistence.FetchType.LAZY;

import java.util.ArrayList;
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
public class ShareRoom extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long shareId;
    @Column(columnDefinition = "NVARCHAR(30) NOT NULL")
    private String shareName;
    @Column(columnDefinition = "NVARCHAR(255) NOT NULL")
    private String shareCode;

    @Column(columnDefinition = "NVARCHAR(255)")
    private String startPoint;
    @Column(columnDefinition = "NVARCHAR(255)")
    private String endPoint;
    @Column(columnDefinition = "NVARCHAR(10) NOT NULL")
    private String travelStartDate;
    @Column(columnDefinition = "NVARCHAR(10) NOT NULL")
    private String travelEndDate;
    @Column(columnDefinition = "NVARCHAR(255)")
    private String imageUrl;
    @Column(columnDefinition = "NVARCHAR(255)")
    private String s3Url;


    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "memberId")
    private Member member;
}
