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
public class Memo extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memoId;
    @Column(columnDefinition = "NVARCHAR(30) NOT NULL")
    private String memoTitle;
    @Column(columnDefinition = "TEXT")
    private String memoContent;


    @ManyToOne
    @JoinColumn(name = "destinationId")
    private Destination destination;
}
