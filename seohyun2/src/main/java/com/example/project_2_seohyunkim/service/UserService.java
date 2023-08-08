package com.example.project_2_seohyunkim.service;

import com.example.project_2_seohyunkim.entity.UserEntity;
import com.example.project_2_seohyunkim.jwt.JwtTokenUtils;
import com.example.project_2_seohyunkim.repos.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final JwtTokenUtils jwtTokenUtils;
    private final PasswordEncoder passwordEncoder;
    private final CustomUserDetailsManager customUserDetailsManager;

    private final String imageUploadPath = "/Users/idshk/Documents/GitHub/Project_2_SeoHyunKim/image";

    public void createUser(UserDetails user) {
        customUserDetailsManager.createUser(user);
    }

    public String login(String username, String password) {
        UserDetails user = customUserDetailsManager.loadUserByUsername(username);

        if (!customUserDetailsManager.userExists(username))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        if (!passwordEncoder.matches(password, user.getPassword()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Incorrect password");

        String token = jwtTokenUtils.generateToken(user);
        return token;
    }

    public void uploadImage(String username, String password, MultipartFile imageFile) throws IOException {
        Optional<UserEntity> user = userRepository.findByUsername(username);
        if (user.isPresent()) {
            UserEntity userEntity = user.get();
            if (userEntity.getUsername().equals(username)) {
                if (passwordEncoder.matches(password, userEntity.getPassword())) {
                    // 이미지 파일 이름 생성
                    String fileName = UUID.randomUUID().toString() + "-" +
                            StringUtils.cleanPath(imageFile.getOriginalFilename());

                    // 이미지 파일을 지정된 경로에 저장
                    Path imagePath = Path.of(imageUploadPath, fileName);
                    Files.copy(imageFile.getInputStream(), imagePath, StandardCopyOption.REPLACE_EXISTING);

                    // 이미지 파일의 URL 생성
                    String imageUrl = "/static/image/" + fileName;

                    // user에 imageUrl 설정
                    userEntity.setProfile(imageUrl);
                    userRepository.save(userEntity);
                } else throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Incorrect password");
            } else throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Incorrect username");
        } else throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
    }

    public UserDetails profile(String username){
        return customUserDetailsManager.loadUserByUsername(username);
    }
}
