package com.example.footstep.component.jwt;

import com.example.footstep.authentication.oauth.OAuthApiClient;
import com.example.footstep.authentication.oauth.OAuthInfoResponse;
import com.example.footstep.authentication.oauth.OAuthLoginParams;
import com.example.footstep.authentication.oauth.OAuthProvider;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class RequestOAuthInfoService {

    private final Map<OAuthProvider, OAuthApiClient> clients;


    public RequestOAuthInfoService(List<OAuthApiClient> clients) {

        this.clients = clients.stream().collect(
            Collectors.toUnmodifiableMap(OAuthApiClient::oAuthProvider, Function.identity())
        );
    }


    public OAuthInfoResponse request(OAuthLoginParams kakaoAccessCode) {

        OAuthApiClient client = clients.get(kakaoAccessCode.oAuthProvider());
        String accessToken = client.requestAccessToken(kakaoAccessCode);
        return client.requestOauthInfo(accessToken);
    }


    public String getAccessToken(OAuthLoginParams accessCode) {

        OAuthApiClient client = clients.get(accessCode.oAuthProvider());
        return client.requestAccessToken(accessCode);
    }
}
