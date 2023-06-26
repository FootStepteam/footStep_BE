package com.example.footstep.component.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtExceptionFilter extends OncePerRequestFilter {

    private final ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
        FilterChain filterChain) throws ServletException, IOException {

        try {
            filterChain.doFilter(request, response);
        } catch (ExpiredJwtException e) {
            log.warn("토큰 기간 만료 예외 발생 : {}", e.getMessage());
            setErrorResponse(HttpStatus.UNAUTHORIZED, e.getMessage(), request, response , "EXPIRED_ACCESS_TOKEN");
        }
        catch (JwtException e) {
            log.warn("JWT 예외 발생 : {}", e.getMessage());
            setErrorResponse(HttpStatus.UNAUTHORIZED, e.getMessage(), request, response, "JWT_EXCEPTION");
        }
    }

    private void setErrorResponse(HttpStatus status, String message, HttpServletRequest request, HttpServletResponse response, String code)
        throws IOException {
        response.setStatus(status.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        final Map<String, Object> body = new HashMap<>();

        // 응답 객체 초기화
        body.put("status", status);
        body.put("errorCode", "Unauthorized");
        body.put("message", message);
        body.put("path", request.getServletPath());
        body.put("code", code);
        // response 객체에 응답 객체를 넣어줌
        objectMapper.writeValue(response.getOutputStream(), body);
    }

//    private static class JwtExceptionResponse {
//
//        private HttpStatus status;
//        private String path;
//        private String message;
//
//
//    }
}
