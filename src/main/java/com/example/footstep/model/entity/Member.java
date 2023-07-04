package com.example.footstep.model.entity;

import com.example.footstep.authentication.oauth.OAuthProvider;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
    @Column(columnDefinition = "NVARCHAR(10)")
    private String gender;
    @Column(columnDefinition = "NVARCHAR(255)")
    private String description;
    @Enumerated(EnumType.STRING)
    private MemberStatus memberStatus = MemberStatus.NORMAL;
    @Column(columnDefinition = "NVARCHAR(255)")
    private String img;
    @Column(columnDefinition = "NVARCHAR(50)")
    private OAuthProvider memberOAuth;


    public void updateProfile(String nickname, String profileUrl, String description) {

        this.nickname = nickname;
        this.img = profileUrl;
        this.description = description;
    }


    public void changePassword(String password) {

        this.password = password;
    }

    public void delete() {
        this.memberStatus = MemberStatus.DELETE;
    }

}
