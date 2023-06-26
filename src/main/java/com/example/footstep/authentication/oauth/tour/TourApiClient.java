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
                "&listYN=Y"+
                "&arrange=A"+
                "&keyword=" + en_keyword +
                "&_type=json" +
                "&contentTypeId=12";

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
                    String firstimage = itemNode.path("firstimage").asText();
                    String firstimage2 = itemNode.path("firstimage2").asText();
                    String addr1 = itemNode.path("addr1").asText();
                    String addr2 = itemNode.path("addr2").asText();

                    String mapx = itemNode.path("mapx").asText();
                    String mapy = itemNode.path("mapy").asText();
                    String title = itemNode.path("title").asText();

                    TourListDto tourListDto = new TourListDto(firstimage, firstimage2, addr1,addr2,mapx,mapy,title);
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
