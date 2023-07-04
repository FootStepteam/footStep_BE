package com.example.footstep.model.dto.community;

import com.example.footstep.model.entity.Community;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.domain.Page;

@Getter
@AllArgsConstructor
public class CommunityListDto {

    private List<CommunityElementDto> communities;
    private int totalPages;


    public static CommunityListDto from(Page<Community> communities) {

        List<CommunityElementDto> postsElementResponses =
            communities.getContent()
                .stream()
                .map(CommunityElementDto::from)
                .collect(Collectors.toList());

        return new CommunityListDto(postsElementResponses, communities.getTotalPages());
    }
}
