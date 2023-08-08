package com.example.project_2_seohyunkim.controller;

import com.example.project_2_seohyunkim.BaseResponse;
import com.example.project_2_seohyunkim.ResponseMessage;
import com.example.project_2_seohyunkim.entity.LikeArticleEntity;
import com.example.project_2_seohyunkim.entity.UserEntity;
import com.example.project_2_seohyunkim.repos.LikeArticleRepository;
import com.example.project_2_seohyunkim.repos.UserRepository;
import com.example.project_2_seohyunkim.service.LikeArticleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/articles/like")
@RequiredArgsConstructor
public class LikeArticleController {
    private final LikeArticleService likeArticleService;
    private final LikeArticleRepository likeArticleRepository;
    private final UserRepository userRepository;

    @PostMapping("/{articleId}")
    public BaseResponse<String> like(
            @PathVariable("articleId") Long articleId,
            @RequestParam("username") String username,
            @RequestParam("password") String password) {
        likeArticleService.likeArticle(articleId, username, password);
        Optional<UserEntity> user = userRepository.findByUsername(username);
        Optional<LikeArticleEntity> likeArticle = likeArticleRepository.findByArticleIdAndUserId(articleId, user.get().getId());
        if (likeArticle.isPresent()) {
            return new BaseResponse<>(ResponseMessage.CREATE_LIKE);
        } else return new BaseResponse<>(ResponseMessage.DELETE_LIKE);
    }
}
