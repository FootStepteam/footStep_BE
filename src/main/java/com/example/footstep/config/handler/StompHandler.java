package com.example.footstep.config.handler;

import com.example.footstep.component.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor

public class StompHandler implements ChannelInterceptor {

    private final JwtTokenProvider jwtTokenProvider;


    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {

        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);

        if (StompCommand.CONNECT == accessor.getCommand()) {
            final String authorization = jwtTokenProvider.extractJwt(accessor);

            jwtTokenProvider.validate(authorization.replace("Bearer ", ""));
        }
        return message;
    }
}
