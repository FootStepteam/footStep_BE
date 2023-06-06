package com.example.footstep.domain.entity.like;

import static javax.persistence.FetchType.LAZY;

import com.example.footstep.domain.entity.board.Board;
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
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "like")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Like {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "like_id")
    private Long id;

    @Column(name = "board_id")
    private Long boardId;

    @Column(name = "member_id")
    private Long memberId;

//    @ManyToOne(fetch = LAZY)
//    @JoinColumn(name = "board_id")
//    private Board board;
//
//    @ManyToOne(fetch = LAZY)
//    @JoinColumn(name = "member_id")
//    private Member member;

}
