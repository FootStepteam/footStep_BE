package com.example.footstep.service;

import com.example.footstep.authentication.oauth.OAuthLoginParams;
import com.example.footstep.component.jwt.AuthTokens;

public interface KakaoService {
    AuthTokens login(OAuthLoginParams params);

}
