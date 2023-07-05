package com.example.footstep.model.dto.community;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SearchCondition {

    private String keyword;
    private String type;

    SearchCondition() {
        this.keyword = "";
        type = "nickname";
    }
}
