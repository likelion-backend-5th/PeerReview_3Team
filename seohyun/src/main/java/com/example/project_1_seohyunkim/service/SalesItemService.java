package com.example.project_1_seohyunkim.service;


import com.example.project_1_seohyunkim.dto.SalesItemDto;
import com.example.project_1_seohyunkim.entity.SalesItemEntity;
import com.example.project_1_seohyunkim.entity.UserEntity;
import com.example.project_1_seohyunkim.mapping.ItemInfoMapping;
import com.example.project_1_seohyunkim.mapping.ItemsMapping;
import com.example.project_1_seohyunkim.repos.SalesItemRepository;
import com.example.project_1_seohyunkim.repos.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.*;

@Service
@RequiredArgsConstructor
public class SalesItemService {
    private final SalesItemRepository salesItemRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final String imageUploadPath = "/Users/idshk/IdeaProjects/MiniProject_Basic_SeoHyunKim/image";

    public SalesItemDto createItem(String username, String password, SalesItemDto dto) {
        Optional<UserEntity> user = userRepository.findByUsername(username);
        if (user.isPresent()) {
            String userPw = user.get().getPassword();
            if (passwordEncoder.matches(password, userPw)) {
                SalesItemEntity newItem = SalesItemEntity.builder()
                        .title(dto.getTitle())
                        .description(dto.getDescription())
                        .imageUrl(dto.getImageUrl())
                        .minPriceWanted(dto.getMinPriceWanted())
                        .status(dto.getStatus())
                        .user(user.get()).build();
                return SalesItemDto.fromEntity(salesItemRepository.save(newItem));
            } else throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Incorrect password");
        } else throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
    }

    public SalesItemDto readItem(Long id, String username, String password) {
        Optional<ItemInfoMapping> optionalEntity = salesItemRepository.findProjectedById(id);
        if (optionalEntity.isPresent()) {
            Optional<UserEntity> user = userRepository.findByUsername(username);
            if (user.isPresent()) {
                String userPw = user.get().getPassword();
                if (passwordEncoder.matches(password, userPw)) {
                    return SalesItemDto.fromMappingEntity(optionalEntity.get());
                } else throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Incorrect password");
            } else throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        } else throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Item not found");
    }

    public Page<ItemsMapping> readItemsAll(
            Integer pageNumber, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("id").ascending());
        Page<ItemsMapping> itemsMappingPage = salesItemRepository.findAllProjectedBy(pageable);
        return itemsMappingPage;
    }

    public SalesItemDto updateItem(Long id, String username, String password, SalesItemDto dto) {
        Optional<SalesItemEntity> optionalEntity = salesItemRepository.findById(id);
        if (optionalEntity.isPresent()) {
            SalesItemEntity salesItemEntity = optionalEntity.get();
            Optional<UserEntity> user = userRepository.findByUsername(username);
            if (user.isPresent()) {
                String userPw = user.get().getPassword();
                if (passwordEncoder.matches(password, userPw)) {
                    if (dto.getTitle() != null) {
                        salesItemEntity.setTitle(dto.getTitle());
                    }
                    if (dto.getDescription() != null) {
                        salesItemEntity.setDescription(dto.getDescription());
                    }
                    if (dto.getMinPriceWanted() != null) {
                        salesItemEntity.setMinPriceWanted(dto.getMinPriceWanted());
                    }
                    if (dto.getImageUrl() != null) {
                        salesItemEntity.setImageUrl(dto.getImageUrl());
                    }
                    if (dto.getStatus() != null) {
                        salesItemEntity.setStatus(dto.getStatus());
                    }
                    salesItemRepository.save(salesItemEntity);
                    return SalesItemDto.fromEntity(salesItemEntity);
                } else throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Incorrect password");
            } else throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        } else throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Item not found");
    }

    public void uploadImage(Long id, String username, String password, MultipartFile imageFile) throws IOException {
        Optional<SalesItemEntity> optionalEntity = salesItemRepository.findById(id);
        if (optionalEntity.isPresent()) {
            SalesItemEntity salesItemEntity = optionalEntity.get();
            Optional<UserEntity> user = userRepository.findByUsername(username);
            if (user.isPresent()) {
                String userPw = user.get().getPassword();
                if (passwordEncoder.matches(password, userPw)) {
                    // 이미지 파일 이름 생성
                    String fileName = UUID.randomUUID().toString() + "-" +
                            StringUtils.cleanPath(imageFile.getOriginalFilename());

                    // 이미지 파일을 지정된 경로에 저장
                    Path imagePath = Path.of(imageUploadPath, fileName);
                    Files.copy(imageFile.getInputStream(), imagePath, StandardCopyOption.REPLACE_EXISTING);

                    // 이미지 파일의 URL 생성
                    String imageUrl = "/static/images/" + fileName;

                    // SalesItemEntity에 imageUrl 설정
                    salesItemEntity.setImageUrl(imageUrl);
                    salesItemRepository.save(salesItemEntity);
                } else throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Incorrect password");
            } else throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        } else throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Item not found");
    }

    public void deleteItem(Long id, String username, String password) {
        Optional<SalesItemEntity> optionalEntity = salesItemRepository.findById(id);
        if (optionalEntity.isPresent()) {
            Optional<UserEntity> user = userRepository.findByUsername(username);
            if (user.isPresent()) {
                String userPw = user.get().getPassword();
                if (passwordEncoder.matches(password, userPw)) {
                    salesItemRepository.deleteById(id);
                } else throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Incorrect password");
            } else throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Item not found");
        }
    }
}