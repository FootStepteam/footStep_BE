package com.example.footstep.domain.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.footstep.domain.entity.Community;
import com.example.footstep.domain.entity.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest
class CommunityRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    CommunityRepository communityRepository;

    @Transactional
    @Test
    void 게시글_조회() {

        // given
        Member member = Member.builder()
            .memberId(1L)
            .gender("m")
            .nickname("닉네임")
            .password("password")
            .loginEmail("test@naver.com")
            .build();

        memberRepository.save(member);

        Community community = Community.builder()
            .communityId(1L)
            .communityName("제목")
            .content("내용")
            .communityPublicState(false)
            .member(member)
            .build();

        communityRepository.save(community);

        // when
        Community findCommunity = communityRepository.findById(1L)
            .get();

        Member findCommunityMember = findCommunity.getMember();

        // then
        assertThat(findCommunity).isNotNull();
        assertThat(findCommunity.getCommunityId()).isEqualTo(1L);
        assertThat(findCommunity.getCommunityName()).isEqualTo("제목");
        assertThat(findCommunity.getContent()).isEqualTo("내용");
        assertThat(findCommunity.isCommunityPublicState()).isFalse();

        assertThat(findCommunityMember).isNotNull();
        assertThat(findCommunityMember.getGender()).isEqualTo("m");
        assertThat(findCommunityMember.getNickname()).isEqualTo("닉네임");
        assertThat(findCommunityMember.getLoginEmail()).isEqualTo("test@naver.com");

    }

}