package com.example.footstep.authentication.oauth.kakao;

import com.example.footstep.authentication.oauth.OAuthApiClient;
import com.example.footstep.authentication.oauth.OAuthInfoResponse;
import com.example.footstep.authentication.oauth.OAuthLoginParams;
import com.example.footstep.authentication.oauth.OAuthProvider;
import com.example.footstep.model.entity.ShareRoom;
import com.example.footstep.model.repository.ShareRoomRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class KakaoApiClient implements OAuthApiClient {

    private static final String GRANT_TYPE = "authorization_code";
    private final ShareRoomRepository shareRoomRepository;

    @Value("${oauth.kakao.url.auth}")
    private String authUrl;
    @Value("${oauth.kakao.url.api}")
    private String apiUrl;
    @Value("${oauth.kakao.url.unlink}")
    private String apiUnurl;
    @Value("${oauth.kakao.client-id}")
    private String clientId;
    @Value("${oauth.kakao.url.sendme}")
    private String apiSendMeUrl;

    private final RestTemplate restTemplate;


    @Override
    public OAuthProvider oAuthProvider() {

        return OAuthProvider.KAKAO;
    }


    @Override
    public String requestAccessToken(OAuthLoginParams params) {

        String url = authUrl + "/oauth/token";

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> body = params.makeBody();
        body.add("grant_type", GRANT_TYPE);
        body.add("client_id", clientId);

        HttpEntity<?> request = new HttpEntity<>(body, httpHeaders);

        KakaoTokens response = restTemplate.postForObject(url, request, KakaoTokens.class);

        assert response != null;
        return response.getAccessToken();
    }


    @Override
    public OAuthInfoResponse requestOauthInfo(String accessToken) {

        String url = apiUrl + "/v2/user/me";

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        httpHeaders.set("Authorization", "Bearer " + accessToken);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("property_keys", "[\"kakao_account.email\", \"kakao_account.gender\", \"kakao_account.profile\"]");

        HttpEntity<?> request = new HttpEntity<>(body, httpHeaders);

        return restTemplate.postForObject(url, request, OAuthInfoResponse.KakaoInfoResponse.class);
    }


    @Override
    public void kakaoUnlink(String accessToken) {

        String unlinkUrl = apiUnurl;

        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.set("Authorization", "Bearer " + accessToken);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();

        HttpEntity<?> request = new HttpEntity<>(body, headers);

        ResponseEntity<String> response =
            restTemplate.postForEntity(unlinkUrl, request, String.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            return;
        }
    }


    @Override
    public String KakaoSendMe(String accessToken, Long shareRoomId) {

        String SendMeUrl = apiSendMeUrl;
        ShareRoom shareRoom = shareRoomRepository.getShareById(shareRoomId);
        String s3Url = shareRoom.getS3Url();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.set("Authorization", "Bearer " + accessToken);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();

        ObjectMapper objectMapper = new ObjectMapper();

        ObjectNode contentObject = objectMapper.createObjectNode();
        contentObject.put("title", "발자국");
        contentObject.put("description", "나의 여행 일정");
        contentObject.put("image_url", s3Url);
        contentObject.put("image_width", 800);
        contentObject.put("image_height", 800);

        ObjectNode linkObject = objectMapper.createObjectNode();
        linkObject.put("web_url", "https://footstep-fe.vercel.app/");
        linkObject.put("mobile_web_url", "https://footstep-fe.vercel.app/");
        linkObject.put("android_execution_params", "contentId=100");
        linkObject.put("ios_execution_params", "contentId=100");

        contentObject.set("link", linkObject);

        ObjectNode feedObject = objectMapper.createObjectNode();
        feedObject.put("object_type", "feed");
        feedObject.set("content", contentObject);

        String templateJson = feedObject.toString();

        body.add("template_object", templateJson);

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(body, headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.exchange(SendMeUrl, HttpMethod.POST,
            requestEntity, String.class);

        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            return "카카오톡으로 메시지를 성공적으로 보냈습니다.";
        } else {
            return "카카오톡 메시지 전송에 실패했습니다. 응답코드: " + responseEntity.getStatusCodeValue();
        }
    }
}
