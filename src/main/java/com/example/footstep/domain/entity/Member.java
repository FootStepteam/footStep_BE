package com.example.footstep.domain.entity;

import com.example.footstep.authentication.oauth.OAuthProvider;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.envers.AuditOverride;


@Entity
@Table(name = "members")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@AuditOverride(forClass = BaseTimeEntity.class)
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;
    @Column(columnDefinition = "NVARCHAR(30) NOT NULL")
    private String loginEmail;
    @Column(columnDefinition = "NVARCHAR(30) NOT NULL")
    private String nickname;
    @Column(columnDefinition = "NVARCHAR(255)")
    private String password;
    @Column(columnDefinition = "CHAR(30)")
    private String gender;

    @Column(columnDefinition = "NVARCHAR(255)")
    private String img;
    @Column(columnDefinition = "NVARCHAR(50)")
    private OAuthProvider memberOAuth;


    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "memberId")
    private List<ShareRoom> shareRooms = new ArrayList<>();
}
