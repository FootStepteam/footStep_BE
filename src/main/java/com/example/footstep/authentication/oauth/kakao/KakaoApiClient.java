package com.example.footstep.authentication.oauth.kakao;

import com.example.footstep.authentication.oauth.OAuthApiClient;
import com.example.footstep.authentication.oauth.OAuthInfoResponse;
import com.example.footstep.authentication.oauth.OAuthLoginParams;
import com.example.footstep.authentication.oauth.OAuthProvider;
import com.example.footstep.domain.entity.ShareRoom;
import com.example.footstep.domain.repository.ShareRoomRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.List;

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
    @Value("${oauth.kakao.url.friends}")
    private String friendsUrl;
    @Value("${oauth.kakao.url.share}")
    private String apiShareUrl;
    @Value("${oauth.kakao.url.sendme}")
    private String apiSendMeUrl;

    @Value("${oauth.kakao.template-id}")
    private String templateId;
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
        body.add("property_keys",
            "[\"kakao_account.email\", \"kakao_account.gender\", \"kakao_account.profile\"]");

        HttpEntity<?> request = new HttpEntity<>(body, httpHeaders);

        return restTemplate.postForObject(url, request, OAuthInfoResponse.KakaoInfoResponse.class);
    }

    @Override
    public void kakaoUnlink(String accessToken) {
        String unlinkUrl = apiUnurl;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.set("Authorization","Bearer " + accessToken);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();

        HttpEntity<?> request = new HttpEntity<>(body,headers);


        ResponseEntity<String> response =  restTemplate.postForEntity(unlinkUrl,request,String.class);
        if(response.getStatusCode() == HttpStatus.OK){
            return;
        }
    }

//    @Override
//    public List<String> getFriendUUIDs(String accessToken) {
//        String fUrl = friendsUrl;
//
//        HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.set("Authorization", "Bearer " + accessToken);
//        HttpEntity<?> request = new HttpEntity<>(httpHeaders);
//
//        ResponseEntity<String> response = restTemplate.exchange(
//                fUrl,
//                HttpMethod.GET,
//                request,
//                String.class
//        );
//
////        List<Friend> friends = response;
////        List<String> friendUUIDs = new ArrayList<>();
////
////        if (friends != null) {
////            for (Friend friend : friends) {
////                friendUUIDs.add(friend.getUUID());
////            }
////        }
//
//        List<String> friendUUIDs = null;
//        return friendUUIDs;
//    }
//
//    @Override
//    public void shareImage(String accessToken, String linkUrl, String[] friendsUuids,Long shareRoomId) {
//        String ShareUrl = apiShareUrl;
//        ShareRoom shareRoom = shareRoomRepository.getShareById(shareRoomId);
//        String imageUrl = shareRoom.getS3Url();
//        linkUrl = "http://localhost:8080"; // 클릭시 이동할 부분
//        HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
//        httpHeaders.set("Authorization", "Bearer " + accessToken);
//
//        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
//        body.add("template_id",templateId );
//        body.add("template_args", String.format("{\"image_url\":\"%s\",\"link_url\":\"%s\"}", imageUrl, linkUrl));
//        body.add("receiver_uuids", String.join(",", friendsUuids));
//
//        HttpEntity<?> httpEntity = new HttpEntity<>(body, httpHeaders);
//
//        restTemplate.postForObject(ShareUrl, httpEntity, Void.class);
//    }

    @Override
    public String KakaoSendMe(String accessToken, Long shareRoomId) {
        String SendMeUrl = apiSendMeUrl;
        ShareRoom shareRoom = shareRoomRepository.getShareById(shareRoomId);
        String s3Url = shareRoom.getS3Url();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.set("Authorization","Bearer " + accessToken);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();

        ObjectMapper objectMapper = new ObjectMapper();

        ObjectNode contentObject = objectMapper.createObjectNode();
        contentObject.put("title", "test-title");
        contentObject.put("description", "test-description");
        contentObject.put("image_url",s3Url );
        contentObject.put("image_width", 800);
        contentObject.put("image_height", 800);

        ObjectNode linkObject = objectMapper.createObjectNode();
        linkObject.put("web_url", "http://localhost:8080/swagger-ui/index.html#/auth-controller");
        linkObject.put("mobile_web_url", "http://localhost:8080/swagger-ui/index.html#/auth-controller");
        linkObject.put("android_execution_params", "contentId=100");
        linkObject.put("ios_execution_params", "contentId=100");

        contentObject.set("link", linkObject);

        ObjectNode feedObject = objectMapper.createObjectNode();
        feedObject.put("object_type", "feed");
        feedObject.set("content", contentObject);

        String templateJson = feedObject.toString();

        body.add("template_object",templateJson);

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(body, headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.exchange(SendMeUrl, HttpMethod.POST, requestEntity, String.class);

        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            return "카카오톡으로 메시지를 성공적으로 보냈습니다.";
        } else {
           return "카카오톡 메시지 전송에 실패했습니다. 응답코드: " + responseEntity.getStatusCodeValue();
        }
    }

}
