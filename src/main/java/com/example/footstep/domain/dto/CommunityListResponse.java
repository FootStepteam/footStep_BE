package com.example.footstep.domain.dto;

import com.example.footstep.domain.entity.Community;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.domain.Slice;

@Getter
@AllArgsConstructor
public class CommunityListResponse {

    private List<CommunityElementResponse> communities;
    private boolean lastPage;

    public static CommunityListResponse ofSlice(Slice<Community> communities) {

        List<CommunityElementResponse> postsElementResponses = communities.getContent()
            .stream()
            .map(CommunityElementResponse::from)
            .collect(Collectors.toList());
        return new CommunityListResponse(postsElementResponses, communities.isLast());
    }

}
