package com.example.mutsamarket.controller;

import com.example.mutsamarket.dto.CustomUserDetails;
import com.example.mutsamarket.dto.JwtRequestDto;
import com.example.mutsamarket.dto.JwtTokenDto;
import com.example.mutsamarket.jwt.JwtTokenUtils;
import com.example.mutsamarket.service.JpaUserDetailsManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@Controller
@RequestMapping("/users")
public class UserController {
    private final PasswordEncoder passwordEncoder;
    private final UserDetailsManager manager;
    private final JwtTokenUtils jwtTokenUtils;

    public UserController(
            PasswordEncoder passwordEncoder,
            JpaUserDetailsManager manager,
            JwtTokenUtils jwtTokenUtils) {
        this.passwordEncoder = passwordEncoder;
        this.manager = manager;
        this.jwtTokenUtils = jwtTokenUtils;
    }

    @ResponseBody
    @PostMapping("/login")
    public JwtTokenDto loginForm(@RequestBody JwtRequestDto request) {
        UserDetails userDetails
                = manager.loadUserByUsername(request.getUsername());
        if (!passwordEncoder.matches(request.getPassword(), userDetails.getPassword()))
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        JwtTokenDto response = new JwtTokenDto();
        response.setToken(jwtTokenUtils.generateToken(userDetails));
        return response;
    }

    @GetMapping("/my-profile")
    public String myProfile(Authentication authentication) {
        log.info(((CustomUserDetails) authentication.getPrincipal()).getUsername());
        return "my-profile";
    }

    @GetMapping("/register")
    public String signUpForm() {
        return "register-form";
    }

    @PostMapping("/register")
    public String signUpRequest(
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            @RequestParam("password-check") String passwordCheck
    ) {
        if (password.equals(passwordCheck))
            manager.createUser(CustomUserDetails.builder()
                    .username(username)
                    .password(passwordEncoder.encode(password))
                    .build());
        log.info("success registered");
        return "redirect:/users/login";
    }
}
