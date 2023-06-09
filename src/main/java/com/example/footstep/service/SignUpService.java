package com.example.footstep.service;

import com.example.footstep.domain.entity.dto.member.MemberDto;

public interface SignUpService {
    String memberSignup(MemberDto memberDto);
}
