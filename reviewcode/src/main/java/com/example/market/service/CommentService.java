package com.example.market.service;

import com.example.market.dto.CommentDto;
import com.example.market.entity.CommentEntity;
import com.example.market.repository.ArticleRepository;
import com.example.market.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final ArticleRepository articleRepository;
    private final CommentRepository commentRepository;

    public CommentDto createComment(Long articleId, CommentDto dto) {
        // articleId를 ID로 가진 ArticleEntity 가 존재 하는지?
        if (!articleRepository.existsById(articleId))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);  // 자유롭게 상황대처

        CommentEntity newComment = new CommentEntity();
        newComment.setWriter(dto.getWriter());
        newComment.setContent(dto.getContent());
        newComment.setArticleId(articleId);
        newComment = commentRepository.save(newComment);
        return CommentDto.fromEntity(newComment);
    }
}
