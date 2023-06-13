package com.example.footstep.component.security;


import com.example.footstep.component.jwt.JwtTokenProvider;
import com.example.footstep.domain.entity.Member;
import com.example.footstep.domain.repository.MemberRepository;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

public class JwtAuthenticationFilter extends BasicAuthenticationFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final MemberRepository memberRepository;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager,
        JwtTokenProvider jwtTokenProvider,
        MemberRepository memberRepository) {
        super(authenticationManager);
        this.jwtTokenProvider = jwtTokenProvider;
        this.memberRepository = memberRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
        FilterChain chain) throws IOException, ServletException {

        String jwtHeader = request.getHeader("Authorization");

        if (jwtHeader == null || !jwtHeader.startsWith("Bearer")) {

            chain.doFilter(request, response);
            return;
        }

        String jwtToken = jwtHeader.replace("Bearer ", "");

        if (jwtTokenProvider.isValid(jwtToken)) {

            // 현재는 subject 에 memberId 만 들어가 있음
            String subject = jwtTokenProvider.extractSubject(jwtToken);

            Member member = memberRepository.getMemberById(Long.valueOf(subject));

            LoginUser loginUser = new LoginUser();
            loginUser.setMemberId(member.getMemberId());
            // 필요에 따라 member 에서 원하는 정보 set 하면 됨.

            Authentication authentication = new UsernamePasswordAuthenticationToken(
                loginUser,
                null,
                null    // 만약 권한에 대한 것이 생기면 여기다 넣으면 됨.
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        chain.doFilter(request, response);

    }

}

