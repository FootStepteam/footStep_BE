package com.example.footstep.domain.entity.board;

import static javax.persistence.FetchType.LAZY;

import com.example.footstep.domain.entity.BaseTimeEntity;
import com.example.footstep.domain.entity.member.Member;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "board")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Board extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private String title;

    private String content;

    private boolean publishStatus;  // 게시글 공개 여부

    @Builder
    public Board(Long id, Member member, String title, String content, boolean publishStatus) {
        this.id = id;
        this.member = member;
        this.title = title;
        this.content = content;
        this.publishStatus = publishStatus;
    }
}
