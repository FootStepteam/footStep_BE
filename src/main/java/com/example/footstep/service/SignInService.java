package com.example.footstep.service;

import com.example.footstep.component.jwt.AuthTokens;
import com.example.footstep.domain.entity.dto.member.LoginDto;

public interface SignInService {
    AuthTokens login(LoginDto loginDto);
}
