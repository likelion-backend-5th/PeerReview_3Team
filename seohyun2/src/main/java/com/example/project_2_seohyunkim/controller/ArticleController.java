package com.example.project_2_seohyunkim.controller;

import com.example.project_2_seohyunkim.BaseResponse;
import com.example.project_2_seohyunkim.ResponseMessage;
import com.example.project_2_seohyunkim.dto.ArticleDto;
import com.example.project_2_seohyunkim.dto.ArticleImagesDto;
import com.example.project_2_seohyunkim.mapping.ArticlesMapping;
import com.example.project_2_seohyunkim.service.ArticleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/articles")
@RequiredArgsConstructor
public class ArticleController {
    private final ArticleService articleService;

    @PostMapping
    public BaseResponse<String> create(
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            @RequestPart ArticleDto dto,
            @RequestPart("image") MultipartFile imageFile) throws IOException {
        articleService.createArticle(username, password, dto, imageFile);
        return new BaseResponse<>(ResponseMessage.CREATE_ARTICLE);
    }

    @GetMapping("/{articleId}")
    public ArticleDto read(
            @PathVariable("articleId") Long id,
            @RequestParam("username") String username,
            @RequestParam("password") String password) {
        return articleService.readArticle(id, username, password);
    }

    @GetMapping
    public Page<ArticlesMapping> readAll(
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            @RequestParam(value = "page", defaultValue = "0") Integer pageNumber) {
        return articleService.readArticlesAll(username, password, pageNumber);
    }

    @PutMapping("/{articleId}")
    public BaseResponse<String> update(
            @PathVariable("articleId") Long id,
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            @RequestPart ArticleDto dto,
            @RequestPart("image") MultipartFile imageFile) throws IOException {
        articleService.updateArticle(id, username, password, dto, imageFile);
        return new BaseResponse<>(ResponseMessage.UPDATE_ARTICLE);
    }

    @PutMapping("/{articleId}/local")
    public BaseResponse<String> delete(
            @PathVariable("articleId") Long id,
            @RequestParam("username") String username,
            @RequestParam("password") String password) {
        articleService.deleteArticle(id, username, password);
        return new BaseResponse<>(ResponseMessage.DELETE_ARTICLE);
    }
}
