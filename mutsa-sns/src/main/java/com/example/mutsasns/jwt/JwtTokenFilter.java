package com.example.mutsasns.jwt;


import com.example.mutsasns.dto.UserDto;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;

@Slf4j
@Component
// 사용자가 Header에 포함한 JWT를 해석하고, 사용자가 인증된 상태인지 확인하는 용도
public class JwtTokenFilter extends OncePerRequestFilter {
    private final JwtTokenUtils jwtTokenUtils;

    public JwtTokenFilter(JwtTokenUtils jwtTokenUtils) {
        this.jwtTokenUtils = jwtTokenUtils;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        // authHeader가 null이 아니면서 "Bearer "로 구성되어 있어야 정상적인 인증 정보.
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            // jwt 회수하여 정상적인 jwt인지 확인
            String token = authHeader.split(" ")[1];
            if (jwtTokenUtils.validate(token)) {
                SecurityContext context
                        = SecurityContextHolder.createEmptyContext();
                // JWT에서 사용자 이름을 가져오기
                String username = jwtTokenUtils
                        .parseClaims(token)
                        .getSubject();
                // 사용자 인증 정보 생성
                AbstractAuthenticationToken authenticationToken
                        = new UsernamePasswordAuthenticationToken(
                        UserDto.builder()
                                .username(username)
                                .build(),
                        token, new ArrayList<>()
                );
                // SecurityContext에 사용자 정보 설정
                context.setAuthentication(authenticationToken);
                // SecurityContextHolder에 SecurityContext 설정
                SecurityContextHolder.setContext(context);
                log.info("set security context with jwt");
            }
            // 아니라면 log.warn을 통해 알림.
            else {
                log.warn("jwt validation failed");
            }
        }
        filterChain.doFilter(request, response);
    }
}
