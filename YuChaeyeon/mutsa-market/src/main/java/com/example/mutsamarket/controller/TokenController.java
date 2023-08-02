package com.example.mutsamarket.controller;

import com.example.mutsamarket.dto.JwtRequestDto;
import com.example.mutsamarket.dto.JwtTokenDto;
import com.example.mutsamarket.jwt.JwtTokenUtils;
import com.example.mutsamarket.service.JpaUserDetailsManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


@Slf4j
@RestController
@RequestMapping("/token")
public class TokenController {
    private final JwtTokenUtils jwtTokenUtils;
    private final JpaUserDetailsManager manager;
    private final PasswordEncoder passwordEncoder;

    public TokenController(JwtTokenUtils jwtTokenUtils, JpaUserDetailsManager manager, PasswordEncoder passwordEncoder) {
        this.jwtTokenUtils = jwtTokenUtils;
        this.manager = manager;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/issue")
    public JwtTokenDto issueJwt(@RequestBody JwtRequestDto dto) {
        UserDetails userDetails
                = manager.loadUserByUsername(dto.getUsername());
        if (!passwordEncoder.matches(dto.getPassword(), userDetails.getPassword()))
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        JwtTokenDto response = new JwtTokenDto();
        response.setToken(jwtTokenUtils.generateToken(userDetails));
        return response;
    }
}
