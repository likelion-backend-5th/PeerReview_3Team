package com.example.mutsasns.controller;

import com.example.mutsasns.dto.ResponseDto;
import com.example.mutsasns.dto.UserDto;
import com.example.mutsasns.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService service;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserService service, PasswordEncoder passwordEncoder) {
        this.service = service;
        this.passwordEncoder = passwordEncoder;
    }

    // POST /users/register
    @PostMapping("/register")
    public ResponseDto register(
            @RequestBody UserDto dto
    ) {
        service.register(dto);
        log.info("createUser success");
        ResponseDto responseDto = new ResponseDto();
        responseDto.getResponse().put("message", "가입이 완료되었습니다.");
        return responseDto;
    }

    // POST /users/login
    @PostMapping("/login")
    public ResponseDto login(
            @RequestBody UserDto dto
    ) {
        ResponseDto response = new ResponseDto();
        response.getResponse().put("token", service.login(dto));
        return response;
    }

    // PUT /users/profile
//    @PutMapping(value = "/profile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    public ResponseDto profile(
//            @RequestBody UserDto dto,
//            Authentication authentication
//    ) {
//        service.
//    }

}