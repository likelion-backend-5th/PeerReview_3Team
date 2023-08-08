package com.example.project_2_seohyunkim.controller;

import com.example.project_2_seohyunkim.BaseResponse;
import com.example.project_2_seohyunkim.ResponseMessage;
import com.example.project_2_seohyunkim.dto.CommentDto;
import com.example.project_2_seohyunkim.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/{articleId}")
    public BaseResponse<String> create(
            @PathVariable("articleId") Long articleId,
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            @RequestBody CommentDto dto) {
        commentService.createComment(articleId, username, password, dto);
        return new BaseResponse<>(ResponseMessage.CREATE_COMMENT);
    }

    @PutMapping("/{articleId}/{id}")
    public BaseResponse<String> update(
            @PathVariable("articleId") Long articleId,
            @PathVariable("id") Long id,
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            @RequestBody CommentDto dto) {
        commentService.updateComment(articleId, id, username, password, dto);
        return new BaseResponse<>(ResponseMessage.UPDATE_COMMENT);
    }

    @PutMapping("/{articleId}/{id}/local")
    public BaseResponse<String> delete(
            @PathVariable("articleId") Long articleId,
            @PathVariable("id") Long id,
            @RequestParam("username") String username,
            @RequestParam("password") String password) {
        commentService.deleteComment(articleId, id, username, password);
        return new BaseResponse<>(ResponseMessage.DELETE_COMMENT);
    }
}
