package com.example.footstep.service;

import com.example.footstep.domain.dto.chat.ChatRoomDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ChatService {
    private final Map<String, ChatRoomDto> chatRoomMap;

    public ChatRoomDto createChatRoom(String roomName){
        ChatRoomDto chatRoom = new ChatRoomDto().create(roomName);

        chatRoomMap.put(chatRoom.getRoomId(), chatRoom);

        return chatRoom;
    }
    public void plusUserCnt(String roomId){
        ChatRoomDto room = chatRoomMap.get(roomId);
        room.setUserCount(room.getUserCount()+1);
    }
    public String addUser(String roomId, String userName){
        ChatRoomDto room = chatRoomMap.get(roomId);
        String userUUID = UUID.randomUUID().toString();

        // 아이디 중복 확인 후 userList 에 추가
        room.getUserlist().put(userUUID, userName);

        return userUUID;
    }
    public void minusUserCnt(String roomId){
        ChatRoomDto room = chatRoomMap.get(roomId);
        room.setUserCount(room.getUserCount()-1);
    }

    // 채팅방 유저 이름 중복 확인
    public String isDuplicateName(String roomId, String username){
        ChatRoomDto room = chatRoomMap.get(roomId);
        String tmp = username;

        // 만약 userName 이 중복이라면 랜덤한 숫자를 붙임
        // 이때 랜덤한 숫자를 붙였을 때 getUserlist 안에 있는 닉네임이라면 다시 랜덤한 숫자 붙이기!
        while(room.getUserlist().containsValue(tmp)){
            int ranNum = (int) (Math.random()*100)+1;

            tmp = username+ranNum;
        }

        return tmp;
    }

    // 채팅방 유저 리스트 삭제
    public void delUser(String roomId, String userUUID){
        ChatRoomDto room = chatRoomMap.get(roomId);
        room.getUserlist().remove(userUUID);
    }

    // 채팅방 userName 조회
    public String getUserName(String roomId, String userUUID){
        ChatRoomDto room = chatRoomMap.get(roomId);
        return room.getUserlist().get(userUUID);
    }

    // 채팅방 전체 userlist 조회
    public ArrayList<String> getUserList(String roomId){
        ArrayList<String> list = new ArrayList<>();

        ChatRoomDto room = chatRoomMap.get(roomId);

        // hashmap 을 for 문을 돌린 후
        // value 값만 뽑아내서 list 에 저장 후 reutrn
        room.getUserlist().forEach((key, value) -> list.add(value));
        return list;
    }
}
