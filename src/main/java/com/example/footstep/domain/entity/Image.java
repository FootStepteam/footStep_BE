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
public class Image extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long imageId;
    @Column(columnDefinition = "NVARCHAR(50) NOT NULL")
    private String fileName;
    @Column(columnDefinition = "NVARCHAR(255) NOT NULL")
    private String filePath;
    @Column(columnDefinition = "NVARCHAR(10) NOT NULL")
    private String fileExtension;
    @Column(columnDefinition = "NVARCHAR(255) NOT NULL")
    private String imageURL;


    @ManyToOne
    @JoinColumn(name = "shareId")
    private ShareRoom shareRoom;
}
