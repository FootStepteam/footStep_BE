package com.example.footstep.domain.dto.chat;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MessageDto implements Serializable {

    private static final long serialVersionUID = 6494678977089006639L;

    //@JsonFormat(shape = JsonFormat.Shape.OBJECT)
    public enum MessageType {
        ENTER, JOIN, TALK;
    }
    private MessageDto.MessageType type;
    private Long shareRoomEnterId;
    private Long shareId;
    private String nickName;
    private String message;

}
