package com.example.footstep.service;

import com.example.footstep.domain.dto.chat.MessageDto;
import com.example.footstep.domain.dto.chat.ShareRoomEnterDto;
import com.example.footstep.domain.dto.chat.redis.RedisSubscriber;
import com.example.footstep.domain.entity.Member;
import com.example.footstep.domain.entity.ShareRoom;
import com.example.footstep.domain.entity.ShareRoomEnter;
import com.example.footstep.domain.repository.MemberRepository;
import com.example.footstep.domain.repository.ShareRoomEnterRepository;
import com.example.footstep.domain.repository.ShareRoomRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ShareRoomEnterService {

    private final RedisMessageListenerContainer redisMessageListener;
    private final RedisSubscriber redisSubscriber;
    private ObjectMapper objectMapper = new ObjectMapper();

    private static final String CHAT_ROOMS = "CHAT_ROOM";
    private final RedisTemplate<String ,Object> redisTemplate;
    private HashOperations<String ,Long , ShareRoomEnterDto> opsHashChatroom;

    private Map<Long ,ChannelTopic> topicMap;

    private final MemberRepository memberRepository;
    private final ShareRoomRepository shareRoomRepository;
    private final ShareRoomEnterRepository shareRoomEnterRepository;

//    public ShareRoomEnterDto getOneShareRoomEnter(Long shareId) {
//
//        return chatRooms.get(shareId);
//    }

    @PostConstruct
    private void init(){
        opsHashChatroom = redisTemplate.opsForHash();
        topicMap = new HashMap<>();
    }

    @Transactional // DB
    public ShareRoomEnterDto createShareRoomEnter(Long memberId, Long shareId) {

        Member member = memberRepository.getMemberById(memberId);

        ShareRoom shareRoom = shareRoomRepository.getShareById(shareId);

        ShareRoomEnter shareRoomEnter =
            shareRoomEnterRepository.findByShareRoom_ShareIdAndMember_MemberId(
                shareRoom.getShareId(), member.getMemberId()).orElseGet(() ->
                shareRoomEnterRepository.save(
                    ShareRoomEnter.builder().member(member).shareRoom(shareRoom).build()));

        ShareRoomEnterDto shareRoomEnterDto = ShareRoomEnterDto.from(shareRoomEnter);
        log.error("shareDto {} ", shareRoomEnterDto );
        //opsHashChatroom.put(CHAT_ROOMS, shareRoomEnter.getShareRoomEnterId(),shareRoomEnterDto);

        return ShareRoomEnterDto.from(shareRoomEnter);
    }



    public void enterChatRoom(Long shareId) {
        ChannelTopic topic = topicMap.get(shareId);
        if(topic == null){
            topic = new ChannelTopic(shareId.toString());
            log.info(" topic : {}", topic);
            redisMessageListener.addMessageListener(redisSubscriber ,topic);
            topicMap.put(47L,topic);
        }
    }

    public ChannelTopic getTopic(Long shareRoomEnterId) {
        return topicMap.get(shareRoomEnterId);
    }
    public void saveChatLog(String chatRoomId, MessageDto message) {
        redisTemplate.opsForList().rightPush(chatRoomId, message);
    }

    public List<MessageDto> getChatLog(Long shareId) {
        String redisKey = shareId.toString();
        List<Object> messages = redisTemplate.opsForList().range(redisKey, 0, -1);
        return messages.stream()
                .map(message -> objectMapper.convertValue(message, MessageDto.class))
                .collect(Collectors.toList());
    }
}
