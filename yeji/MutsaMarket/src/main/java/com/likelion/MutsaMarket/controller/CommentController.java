package com.likelion.MutsaMarket.controller;

import com.likelion.MutsaMarket.dto.CommentDto;
import com.likelion.MutsaMarket.dto.ResponseDto;
import com.likelion.MutsaMarket.repository.CommentRepository;
import com.likelion.MutsaMarket.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/items/{itemId}/comments")
public class CommentController {
    private final CommentService service;
    private final ResponseDto responseDto = new ResponseDto();

    // POST /items/{itemId}/comments
    @PostMapping
    public ResponseDto create(
            @PathVariable("itemId") Long itemId,
            @RequestBody CommentDto dto) {
        service.createComment(itemId, dto);
        responseDto.setMessage("댓글이 등록되었습니다.");
        return responseDto;
    }

    // GET /items/{itemId}/comments
    @GetMapping
    public Page<CommentDto> readCommentAll(
            @PathVariable("itemId") Long itemId) {
        return service.readCommentPaged(itemId);
    }

    // PUT /items/{itemId}/comments/{commentId}
    @PutMapping("/{commentId}")
    public ResponseDto update(
            @PathVariable("itemId") Long itemId,  // URL의 ID
            @PathVariable("commentId") Long commentId,
            @RequestBody CommentDto dto   // HTTP Request Body
    ) {
        ResponseDto responseDto = new ResponseDto();
        service.updateComment(itemId, commentId, dto);
        responseDto.setMessage("댓글이 수정되었습니다.");
        return responseDto;
    }

    // PUT /items/{itemId}/comments/{commentId}/reply


    // DELETE /items/{itemId}/comments/{commentId}
    @DeleteMapping("/{commentId}")
    public ResponseDto delete(
            @PathVariable("itemId") Long itemId,  // URL의 ID
            @PathVariable("commentId") Long commentId,
            @RequestBody CommentDto dto   // HTTP Request Body
    ) {
        ResponseDto responseDto = new ResponseDto();
        service.deleteComment(itemId, commentId, dto);
        responseDto.setMessage("댓글이 삭제되었습니다.");
        return responseDto;
    }
}
