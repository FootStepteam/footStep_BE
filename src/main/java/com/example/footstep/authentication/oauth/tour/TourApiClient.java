package com.example.footstep.authentication.oauth.tour;

import com.example.footstep.domain.dto.share_room.RecommendDto;
import com.example.footstep.domain.dto.share_room.TourListDto;
import com.example.footstep.exception.ErrorCode;
import com.example.footstep.exception.GlobalException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class TourApiClient {

    @Value("${tour.client.key}")
    private String tourKey;

    @Value(("${tour.base.url}"))
    private String baseUrl;


    public RecommendDto searchTourImageKeyword(String keyword) throws IOException {
        String serviceKey = URLEncoder.encode(tourKey,"UTF-8");
        String en_keyword = URLEncoder.encode(keyword,"UTF-8");
        log.debug(serviceKey);

        String url = baseUrl +"?serviceKey=" + serviceKey +
                "&numOfRows=10" +
                "&pageNo=1" +
                "&MobileOS=ETC" +
                "&MobileApp=footStep"+
                "&arrange=A"+
                "&keyword=" + en_keyword +
                "&_type=json";

//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);

        URL requestUrl = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) requestUrl.openConnection();
        connection.setRequestMethod("GET");

        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            StringBuilder responseBody = new StringBuilder();

            while ((line = reader.readLine()) != null) {
                responseBody.append(line);
            }

            reader.close();

            ObjectMapper objectMapper = new ObjectMapper();
            try {
                JsonNode root = objectMapper.readTree(responseBody.toString());
                JsonNode itemsNode = root.path("response").path("body").path("items").path("item");

                List<TourListDto> photoList = new ArrayList<>();
                for (JsonNode itemNode : itemsNode) {
                    String galWebImageUrl = itemNode.path("galWebImageUrl").asText();
                    String galPhotographyLocation = itemNode.path("galPhotographyLocation").asText();
                    String galTitle = itemNode.path("galTitle").asText();

                    TourListDto tourListDto = new TourListDto(galWebImageUrl, galPhotographyLocation, galTitle);
                    photoList.add(tourListDto);
                }

                RecommendDto recommendDto = new RecommendDto();
                recommendDto.setSearchKeyword(keyword);
                recommendDto.setImageList(photoList);

                return recommendDto;
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        } else {
            log.info(String.valueOf(responseCode));
        }
        connection.disconnect();
        throw new GlobalException(ErrorCode.NOT_HAVE_DATA_TOUR);
    }

}
