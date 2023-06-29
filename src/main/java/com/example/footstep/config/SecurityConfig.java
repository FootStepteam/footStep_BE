package com.example.footstep.config;

import com.example.footstep.component.jwt.JwtTokenProvider;
import com.example.footstep.component.security.CustomAccessDeniedHandler;
import com.example.footstep.component.security.CustomAuthenticationEntryPoint;
import com.example.footstep.component.security.JwtAuthenticationFilter;
import com.example.footstep.component.security.JwtExceptionFilter;
import com.example.footstep.domain.repository.MemberRepository;
import java.util.Arrays;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig {

    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationConfiguration configuration;
    private final JwtExceptionFilter jwtExceptionFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
            .headers()
            .frameOptions()
            .sameOrigin().and()
            .csrf().disable()
            .cors().configurationSource(corsConfigurationSource())
            .and()
            //  접근 권한(인가)에 실패한 경우
            .exceptionHandling().accessDeniedHandler(customAccessDeniedHandler())
            .and()
            // [ JWT ] 인증에 실패한 경우: 401(UNAUTHORIZED)
            .exceptionHandling().authenticationEntryPoint(customAuthenticationEntryPoint())
            .and()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .formLogin().disable()
            .httpBasic().disable()
            // 각 API 경로에 대한 접근권한 설정
            .authorizeRequests()
            .antMatchers("/api/auth/**", "/api/kakao/**", "/api/members/**", "/api/upload","/api/sendme/**", "/chat/**", "/pub/**","/sub/**").permitAll()
            .antMatchers("/api/share-room/**/destination/**", "/api/share-room/**/schedule/recommend").permitAll()
            .antMatchers(HttpMethod.GET, "/**").permitAll()
            .anyRequest().authenticated()
            .and()
            // JWT 인증 처리
            .addFilterBefore(jwtAuthenticationFilter(), BasicAuthenticationFilter.class)
            .addFilterBefore(jwtExceptionFilter, JwtAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOriginPatterns(Arrays.asList("*"));                         // 허용할 URL
        configuration.setAllowedMethods(
            Arrays.asList("OPTIONS", "HEAD", "GET", "POST", "PUT", "PATCH", "DELETE"));     // 허용할 메서드
        configuration.setAllowedHeaders(
            Arrays.asList("Authorization", "Cache-Control", "Content-Type", "Set-Cookie")); // 허용할 Header
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public CustomAccessDeniedHandler customAccessDeniedHandler() {
        return new CustomAccessDeniedHandler();
    }

    @Bean
    public CustomAuthenticationEntryPoint customAuthenticationEntryPoint() {
        return new CustomAuthenticationEntryPoint();
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() throws Exception {
        return new JwtAuthenticationFilter(authenticationManager(configuration), jwtTokenProvider,
            memberRepository);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration)
        throws Exception {
        return configuration.getAuthenticationManager();
    }

}