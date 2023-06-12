package com.example.footstep.domain.dto.community;

import com.example.footstep.domain.entity.Community;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.domain.Slice;

@Getter
@AllArgsConstructor
public class CommunityListDto {

    private List<CommunityElementDto> communities;
    private boolean lastPage;

    public static CommunityListDto ofSlice(Slice<Community> communities) {

        List<CommunityElementDto> postsElementResponses = communities.getContent()
            .stream()
            .map(CommunityElementDto::from)
            .collect(Collectors.toList());
        return new CommunityListDto(postsElementResponses, communities.isLast());
    }
}
