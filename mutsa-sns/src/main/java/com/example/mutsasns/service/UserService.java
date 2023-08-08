package com.example.mutsasns.service;

import com.example.mutsasns.dto.UserDto;
import com.example.mutsasns.entity.UserEntity;
import com.example.mutsasns.jwt.JwtTokenUtils;
import com.example.mutsasns.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Slf4j
@Service
public class UserService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtils jwtTokenUtils;
    private final CustomUserDetailsManager manager;

    public UserService(UserRepository repository, PasswordEncoder passwordEncoder, JwtTokenUtils jwtTokenUtils, CustomUserDetailsManager manager) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenUtils = jwtTokenUtils;
        this.manager = manager;
    }

    public void register(UserDetails user) {
        // TODO CustomUserDetailsManager에 createUser랑 잘 분리시키기 ..
        if (manager.userExists(user.getUsername()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        try {
            // UserDto 타입으로 형변환해서 레포에 저장.
//            repository.save(UserDto.builder()
//                    .username(user.getUsername())
//                    .password(passwordEncoder.encode(user.getPassword()))
//                    .build()
//                    .newEntity());
            manager.createUser(UserDto.builder()
                    .username(user.getUsername())
                    .password(passwordEncoder.encode(user.getPassword()))
                    .build());
        } catch (ClassCastException e) {
            // 타입 캐스팅 실패 시 예외 처리.
            log.error("failed to cast to {}", UserDto.class);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public String login(UserDto dto) {
        Optional<UserEntity> optionalUser = repository.findByUsername(dto.getUsername());
        // 전달받은 dto의 username에 해당하는 사용자 존재하지 않을 경우 예외 처리
        if (optionalUser.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        // 비밀번호 불일치 시 예외 처리
        if (!passwordEncoder.matches(dto.getPassword(), optionalUser.get().getPassword()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        // 로그인 성공 시 토큰 발급
        String token = jwtTokenUtils.generateToken(dto);
        return token;
    }

}