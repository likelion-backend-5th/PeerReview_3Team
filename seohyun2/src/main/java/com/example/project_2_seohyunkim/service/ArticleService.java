package com.example.project_2_seohyunkim.service;

import com.example.project_2_seohyunkim.dto.ArticleDto;
import com.example.project_2_seohyunkim.dto.ArticleImagesDto;
import com.example.project_2_seohyunkim.entity.ArticleEntity;
import com.example.project_2_seohyunkim.entity.ArticleImagesEntity;
import com.example.project_2_seohyunkim.entity.UserEntity;
import com.example.project_2_seohyunkim.mapping.ArticlesMapping;
import com.example.project_2_seohyunkim.mapping.ArticlesMappingImpl;
import com.example.project_2_seohyunkim.repos.ArticleImgRepository;
import com.example.project_2_seohyunkim.repos.ArticleRepository;
import com.example.project_2_seohyunkim.repos.CommentRepository;
import com.example.project_2_seohyunkim.repos.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleRepository articleRepository;
    private final ArticleImgRepository articleImgRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final String imageUploadPath = "/Users/idshk/Documents/GitHub/Project_2_SeoHyunKim/image";

    public void createArticle(String username, String password, ArticleDto dto, MultipartFile imageFile) throws IOException {
        Optional<UserEntity> user = userRepository.findByUsername(username);
        if (user.isPresent()) {
            UserEntity userEntity = user.get();
            if (passwordEncoder.matches(password, userEntity.getPassword())) {
                ArticleEntity newArticle = ArticleEntity.builder()
                        .user(userEntity)
                        .title(dto.getTitle())
                        .content(dto.getContent())
                        .draft(dto.isDraft())
                        .deletedAt(dto.getDeletedAt()).build();
                articleRepository.save(newArticle);

                if (!imageFile.isEmpty()) {
                    String fileName = UUID.randomUUID().toString() + "-" +
                            StringUtils.cleanPath(imageFile.getOriginalFilename());
                    Path imagePath = Path.of(imageUploadPath, fileName);
                    Files.copy(imageFile.getInputStream(), imagePath, StandardCopyOption.REPLACE_EXISTING);
                    String imageUrl = "/static/image/" + fileName;

                    ArticleImagesEntity newImage = ArticleImagesEntity.builder()
                            .article(newArticle)
                            .imageUrl(imageUrl).build();
                    articleImgRepository.save(newImage);
                } else { //첨부된 이미지가 없다면 기본 이미지로 저장됨
                    ArticleImagesEntity newImage = ArticleImagesEntity.builder()
                            .article(newArticle)
                            .imageUrl("/static/image/default.png").build();
                    articleImgRepository.save(newImage);
                }
            } else throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Incorrect password");
        } else throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
    }

    public ArticleDto readArticle(Long id, String username, String password) {
        Optional<ArticleEntity> article = articleRepository.findById(id);
        Optional<UserEntity> user = userRepository.findByUsername(username);
        if (article.isPresent()) {
            if (user.isPresent()) {
                UserEntity userEntity = user.get();
                if (passwordEncoder.matches(password, userEntity.getPassword())) {
                    return ArticleDto.fromEntity(article.get());
                } else throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Incorrect password");
            } else throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        } else throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Feed not found");
    }

    public Page<ArticlesMapping> readArticlesAll(
            String username, String password, Integer pageNumber) {
        Optional<UserEntity> user = userRepository.findByUsername(username);
        if (user.isPresent()) {
            UserEntity userEntity = user.get();
            if (passwordEncoder.matches(password, userEntity.getPassword())) {
                Pageable pageable = PageRequest.of(pageNumber, 25, Sort.by("id").ascending());
                Page<ArticleEntity> articlesPage = articleRepository.findAllByUser(user, pageable);

                List<ArticlesMapping> articlesMappings = articlesPage.getContent().stream()
                        .map(this::createArticlesMapping)
                        .collect(Collectors.toList());
                return new PageImpl<>(articlesMappings, pageable, articlesPage.getTotalElements());
            } else throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Incorrect password");
        } else throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
    }

    private ArticlesMapping createArticlesMapping(ArticleEntity articleEntity) {
        return new ArticlesMappingImpl(articleEntity);
    }

    public void updateArticle(Long id, String username, String password, ArticleDto dto, MultipartFile imageFile) throws IOException {
        Optional<ArticleEntity> article = articleRepository.findById(id);
        Optional<ArticleImagesEntity> articleImg = articleImgRepository.findByArticle_Id(id);
        Optional<UserEntity> user = userRepository.findByUsername(username);
        if (article.isPresent()) {
            if (user.isPresent()) {
                UserEntity userEntity = user.get();
                if (passwordEncoder.matches(password, userEntity.getPassword())) {
                    ArticleEntity articleEntity = article.get();
                    ArticleImagesEntity articleImagesEntity = articleImg.get();
                    if (dto.getTitle() != null) {
                        articleEntity.setTitle(dto.getTitle());
                    }
                    if (dto.getContent() != null) {
                        articleEntity.setContent(dto.getContent());
                    }
                    if (!imageFile.isEmpty()) {
                        String fileName = UUID.randomUUID().toString() + "-" +
                                StringUtils.cleanPath(imageFile.getOriginalFilename());
                        Path imagePath = Path.of(imageUploadPath, fileName);
                        Files.copy(imageFile.getInputStream(), imagePath, StandardCopyOption.REPLACE_EXISTING);
                        String imageUrl = "/static/image/" + fileName;
                        articleImagesEntity.setImageUrl(imageUrl);
                    } else {
                        articleImagesEntity.setImageUrl("/static/image/default.png");
                    }
                    articleImgRepository.save(articleImagesEntity);
                    articleRepository.save(articleEntity);
                } else throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Incorrect password");
            } else throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        } else throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Feed not found");
    }

    public void deleteArticle(Long id, String username, String password) {
        Optional<ArticleEntity> article = articleRepository.findById(id);
        Optional<UserEntity> user = userRepository.findByUsername(username);
        if (article.isPresent()) {
            if (user.isPresent()) {
                UserEntity userEntity = user.get();
                if (passwordEncoder.matches(password, userEntity.getPassword())) {
                    ArticleEntity articleEntity = article.get();
                    articleEntity.setDeletedAt(LocalDateTime.now());
                    // 실제로 DB에서 삭제하지 않고, 삭제 표시 남김
                    articleRepository.save(articleEntity);
                } else throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Incorrect password");
            } else throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        } else throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Feed not found");
    }
}