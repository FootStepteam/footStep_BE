package com.example.footstep.authentication.oauth;

public interface OAuthApiClient {

    OAuthProvider oAuthProvider();

    String requestAccessToken(OAuthLoginParams params);

    OAuthInfoResponse requestOauthInfo(String accessToken);

    void kakaoUnlink(String accessToken);

    String KakaoSendMe(String accessToken, Long shareRoomId);
}
