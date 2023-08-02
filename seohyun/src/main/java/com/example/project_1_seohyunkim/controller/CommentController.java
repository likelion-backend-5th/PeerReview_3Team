package com.example.project_1_seohyunkim.controller;

import com.example.project_1_seohyunkim.BaseResponse;
import com.example.project_1_seohyunkim.ResponseMessage;
import com.example.project_1_seohyunkim.dto.CommentDto;
import com.example.project_1_seohyunkim.mapping.CommentsMapping;
import com.example.project_1_seohyunkim.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/items/{itemId}/comments")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping
    public BaseResponse<String> create(
            @PathVariable("itemId") Long itemId,
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            @Valid @RequestBody CommentDto dto) {
        commentService.createComment(itemId, username, password, dto);
        return new BaseResponse<>(ResponseMessage.CREATE_COMMENT);
    }

    @GetMapping
    public Page<CommentsMapping> readAll(@PathVariable("itemId") Long itemId) {
        return commentService.readCommentsAll(itemId);
    }

    @PutMapping("/{commentId}")
    public BaseResponse<String> update(
            @PathVariable("itemId") Long itemId,
            @PathVariable("commentId") Long id,
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            @RequestBody CommentDto dto) {
        commentService.updateComment(itemId, id, username, password, dto);
        return new BaseResponse<>(ResponseMessage.UPDATE_COMMENT);
    }

    @PutMapping("/{commentId}/reply")
    public BaseResponse<String> updateReply(
            @PathVariable("itemId") Long itemId,
            @PathVariable("commentId") Long id,
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            @RequestBody CommentDto dto) {
        commentService.createReply(itemId, id, username, password, dto);
        return new BaseResponse<>(ResponseMessage.CREATE_REPLY);
    }

    @DeleteMapping("/{commentId}")
    public BaseResponse<String> delete(
            @PathVariable("itemId") Long itemId,
            @PathVariable("commentId") Long id,
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            @RequestBody CommentDto dto) {
        commentService.deleteComment(itemId, id, username, password, dto);
        return new BaseResponse<>(ResponseMessage.DELETE_COMMENT);
    }
}
