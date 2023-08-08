package com.example.mutsamarket.controller;

import com.example.mutsamarket.dto.CommentDto;
import com.example.mutsamarket.dto.ResponseDto;
import com.example.mutsamarket.entity.SalesItemEntity;
import com.example.mutsamarket.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import javax.xml.stream.events.Comment;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/items/{itemId}/comments")
public class CommentController {

    private final CommentService service;

    // POST /items/{itemId}/comments
    @PostMapping
    public ResponseDto createComment(
            @PathVariable("itemID") Long id,
            @RequestBody CommentDto dto
    ) {
        service.createComment(dto);
        ResponseDto response = new ResponseDto();
        response.setMessage("댓글이 등록되었습니다.");
        service.updateCommentItemId(id, dto.getId());
        return response;
    }

    // PUT /items/{itemId}/comments/{commentId}
//    @PutMapping("/{commentId}")
//    @ResponseStatus(HttpStatus.NO_CONTENT)
//    public void updateItemComment(
//            @PathVariable("itemId") Long id,
//            @PathVariable("commentId") Long commentId
//    ) {
//        service.updateCommentItemId(id, commentId);
//    }

//     GET /items/{itemId}/comments
    @GetMapping
    public Page<CommentDto> readComment(@PathVariable("itemId") Long id) {
        return service.readCommentPaged(id);
    }

    // PUT /items/{itemId}/comments/{commentId}
    @PutMapping("{commentId}")
    public ResponseDto updateComment(
            @PathVariable("itemId") Long itemId,
            @PathVariable("commentId") Long id,
            @RequestBody CommentDto dto
    ) {
        service.updateComment(itemId, id, dto);
        ResponseDto response = new ResponseDto();
        response.setMessage("댓글이 수정되었습니다.");
        return response;
    }

//    // PUT /items/{itemId}/comments/{commentId}/reply
//    @PutMapping
//    public void updateCommnetReply() {
//
//    }

    // DELETE /items/{itemId}/comments/{commentId}
    @DeleteMapping("/{commentId}")
    public ResponseDto deleteCommnet(
            @PathVariable("itemId") Long itemId,
            @PathVariable("commentId") Long id,
            @RequestBody CommentDto dto
    ) {
        service.deleteComment(itemId, id, dto);
        ResponseDto responseDto = new ResponseDto();
        responseDto.setMessage("댓글을 삭제했습니다");
        return  responseDto;
    }

}
