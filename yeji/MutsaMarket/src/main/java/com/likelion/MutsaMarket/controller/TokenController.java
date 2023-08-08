package com.likelion.MutsaMarket.controller;

import com.likelion.MutsaMarket.jwt.JwtRequestDto;
import com.likelion.MutsaMarket.jwt.JwtTokenDto;
import com.likelion.MutsaMarket.jwt.JwtTokenUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/token")
public class TokenController {
    private final JwtTokenUtils jwtTokenUtils;
    private final UserDetailsManager manager; // UserDetailsManager: 사용자 정보 회수
    private final PasswordEncoder passwordEncoder; // PasswordEncoder: 비밀번호 대조용 인코더

    // /token/issue: JWT 발급용 Endpoint
    // Jwt 발급을 받는 Mapping
    // RequestBody : 인증 받고자 하는 사용자가 ID 비밀번호를 전달한다.
    // ResponseBody : 발급이 완료된 JWT를 전달한다. (JwtTokenDto)
    @PostMapping("/issue")
    public JwtTokenDto issueJwt(@RequestBody JwtRequestDto dto) {
        // 사용자 정보 회수
        UserDetails userDetails = manager.loadUserByUsername(dto.getUsername());
        // 기록된 비밀번호와 실제 비밀번호가 다를때
        // passwordEncoder.matches(rawPassword, encodedPassword)
        // 평문 비밀번호와 암호화 비밀번호를 비교할 수 있다.
        if (!passwordEncoder.matches(dto.getPassword(), userDetails.getPassword()))
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);

        JwtTokenDto response = new JwtTokenDto();
        response.setToken(jwtTokenUtils.generateToken(userDetails));
        return response;
    }

    // POST /token/secured
    // 인증이 필요한 URL
    @PostMapping("/secured")
    public String checkSecure() {
        log.info(SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName()
        );
        return "success";
    }
}