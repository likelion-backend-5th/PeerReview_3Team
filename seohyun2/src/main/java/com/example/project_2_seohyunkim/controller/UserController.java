package com.example.project_2_seohyunkim.controller;

import com.example.project_2_seohyunkim.ResponseMessage;
import com.example.project_2_seohyunkim.dto.UserDto;
import com.example.project_2_seohyunkim.entity.CustomUserDetails;
import com.example.project_2_seohyunkim.service.UserService;
import com.example.project_2_seohyunkim.BaseResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController // Controller 대신 RestController를 써야 403 에러 안남
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserDetailsManager manager;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;

    @GetMapping("/login")
    public BaseResponse<String> login(
            @RequestParam("username") String username,
            @RequestParam("password") String password){
        String token = userService.login(username, password);
        Map<String, String> result = new HashMap<>();
        result.put("token", token);

        return new BaseResponse<>(ResponseMessage.LOGIN_USER);
    }

    @PostMapping("/register")
    public BaseResponse<String> register(
            @RequestBody UserDto dto,
            @RequestParam("password-check") String passwordCheck
    ) {
            if (dto.getPassword().equals(passwordCheck)) {
            log.info("password match!");
            manager.createUser(CustomUserDetails.builder()
                    .username(dto.getUsername())
                    .password(passwordEncoder.encode(dto.getPassword()))
                    .phone(dto.getPhone())
                    .email(dto.getEmail())
                    .profile(dto.getProfile())
                    .build());
            return new BaseResponse<>(ResponseMessage.REGISTER_USER);
        } else {
            log.warn("password does not match...");
            return new BaseResponse<>(ResponseMessage.REGISTER_FAIL_USER);
        }
    }

    @PutMapping("/image")
    public BaseResponse<String> uploadProfileImage(
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            @RequestPart("image") MultipartFile imageFile) throws IOException {
        userService.uploadImage(username, password, imageFile);
        return new BaseResponse<>(ResponseMessage.CREATE_IMAGE);
    }


    @GetMapping("/my-profile")
    public String myProfile(Authentication authentication) {
        CustomUserDetails userDetails
                = (CustomUserDetails) authentication.getPrincipal();
        log.info(userDetails.getUsername());
        log.info(userDetails.getPhone());
        log.info(userDetails.getEmail());
        log.info(userDetails.getProfile());
        return "my-profile";
    }

}
