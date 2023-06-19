package com.example.footstep.authentication.oauth;

import java.util.List;

public interface OAuthApiClient {

    OAuthProvider oAuthProvider();

    String requestAccessToken(OAuthLoginParams params);

    OAuthInfoResponse requestOauthInfo(String accessToken);

    void kakaoUnlink(String accessToken);
//    List<String> getFriendUUIDs(String accessToken);
//    void shareImage(String accessToken , String linkUrl , String[] receiverUuids,Long shareRoomId);

    String KakaoSendMe(String accessToken, Long shareRoomId);
}
