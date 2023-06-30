package com.example.footstep.component.security;

import com.example.footstep.component.jwt.JwtTokenProvider;
import com.example.footstep.exception.ErrorCode;
import com.example.footstep.exception.GlobalException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthenticationResolver implements HandlerMethodArgumentResolver {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(LoginMember.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
        NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {

        String authorizationHeader = resolveHeader(webRequest);

        validateHeader(authorizationHeader);

        String accessToken = extractAccessToken(authorizationHeader);

        if (ObjectUtils.isEmpty(accessToken)) {
            throw new GlobalException(ErrorCode.WRONG_ACCESS_TOKEN_AUTH);
        }

        try {
            jwtTokenProvider.validate(accessToken);
            String subject = jwtTokenProvider.extractSubject(accessToken);
            return new CurrentMember(Long.valueOf(subject));
        }catch (ExpiredJwtException e) {
            log.info("토큰 만료되었습니다.");
            throw new GlobalException(ErrorCode.EXPIRED_ACCESS_TOKEN);
        }catch (JwtException e) {
            log.info("JWT 오류 발생.");
            throw new GlobalException(ErrorCode.JWT_EXCEPTION);
        }

    }

    private String extractAccessToken(String authorizationHeader) {
        return authorizationHeader.replace("Bearer ", "");
    }

    private void validateHeader(String authorizationHeader) {
        if (ObjectUtils.isEmpty(authorizationHeader) || !authorizationHeader.startsWith("Bearer")) {
            throw new GlobalException(ErrorCode.WRONG_AUTHORIZATION_HEADER);
        }
    }

    private String resolveHeader(NativeWebRequest webRequest) {
        return webRequest.getHeader(HttpHeaders.AUTHORIZATION);
    }
}
