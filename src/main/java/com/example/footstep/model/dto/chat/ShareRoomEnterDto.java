package com.example.footstep.model.dto.chat;

import com.example.footstep.model.entity.ShareRoomEnter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShareRoomEnterDto {

    private Long shareRoomEnterId;
    private Long memberId;
    private String nickname;
    private Long shareId;
    private String shareName;


    public static ShareRoomEnterDto from(ShareRoomEnter shareRoomEnter) {
        return ShareRoomEnterDto.builder()
            .shareRoomEnterId(shareRoomEnter.getShareRoomEnterId())
            .memberId(shareRoomEnter.getMember().getMemberId())
            .nickname(shareRoomEnter.getMember().getNickname())
            .shareId(shareRoomEnter.getShareRoom().getShareId())
            .shareName(shareRoomEnter.getShareRoom().getShareName())
            .build();
    }
}
