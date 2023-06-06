package com.example.footstep.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RecommendData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long apiId;
    @Column(columnDefinition = "NVARCHAR(255)")
    private String addr1;
    @Column(columnDefinition = "NVARCHAR(255)")
    private String addr2;
    @Column(columnDefinition = "NVARCHAR(255)")
    private String imgUrl;
    @Column(columnDefinition = "NVARCHAR(255)")
    private String mapx;
    @Column(columnDefinition = "NVARCHAR(255)")
    private String mapy;
    @Column(columnDefinition = "NVARCHAR(255)")
    private String tel;
}
