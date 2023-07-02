package com.example.footstep.domain.dto.chat.redis;

import com.example.footstep.domain.dto.chat.MessageDto;
import com.example.footstep.service.ShareRoomEnterService;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class RedisPublisher {
    private final RedisTemplate<String ,Object> redisTemplate;
    private final ShareRoomEnterService shareRoomEnterService;
    public void publish(ChannelTopic topic, MessageDto message){

        log.error("topic: {} , as ~~ : {}",topic, message);
        redisTemplate.convertAndSend(topic.getTopic(), message);

        shareRoomEnterService.saveChatLog(topic.getTopic(),message);
    }
}
