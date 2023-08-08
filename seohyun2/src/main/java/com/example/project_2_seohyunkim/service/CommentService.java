package com.example.project_2_seohyunkim.service;

import com.example.project_2_seohyunkim.dto.ArticleDto;
import com.example.project_2_seohyunkim.dto.CommentDto;
import com.example.project_2_seohyunkim.entity.ArticleEntity;
import com.example.project_2_seohyunkim.entity.CommentEntity;
import com.example.project_2_seohyunkim.entity.UserEntity;
import com.example.project_2_seohyunkim.repos.ArticleRepository;
import com.example.project_2_seohyunkim.repos.CommentRepository;
import com.example.project_2_seohyunkim.repos.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void createComment(Long articleId, String username, String password, CommentDto dto) {
        Optional<ArticleEntity> article = articleRepository.findById(articleId);
        Optional<UserEntity> user = userRepository.findByUsername(username);
        if (article.isPresent()) {
            if (user.isPresent()) {
                UserEntity userEntity = user.get();
                if (passwordEncoder.matches(password, userEntity.getPassword())) {
                    ArticleEntity articleEntity = article.get();
                    CommentEntity newComment = CommentEntity.builder()
                            .user(userEntity)
                            .article(articleEntity)
                            .content(dto.getContent()).build();
                    commentRepository.save(newComment);
                } else throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Incorrect password");
            } else throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        } else throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Feed not found");
    }

    public void updateComment(Long articleId, Long id, String username, String password, CommentDto dto) {
        Optional<ArticleEntity> article = articleRepository.findById(articleId);
        Optional<CommentEntity> comment = commentRepository.findByIdAndArticleId(id, articleId);
        Optional<UserEntity> user = userRepository.findByUsername(username);
        if (article.isPresent()) {
            if (comment.isPresent()) {
                if (user.isPresent()) {
                    UserEntity userEntity = user.get();
                    if (passwordEncoder.matches(password, userEntity.getPassword())) {
                        if (dto.getContent() != null) {
                            CommentEntity commentEntity = comment.get();
                            commentEntity.setContent(dto.getContent());
                            commentRepository.save(commentEntity);
                        }
                    } else throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Incorrect password");
                } else throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
            } else throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Comment not found");
        } else throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Feed not found");

    }

    public void deleteComment(Long articleId, Long id, String username, String password) {
        Optional<ArticleEntity> article = articleRepository.findById(articleId);
        Optional<CommentEntity> comment = commentRepository.findByIdAndArticleId(id, articleId);
        Optional<UserEntity> user = userRepository.findByUsername(username);
        if (article.isPresent()) {
            if (comment.isPresent()) {
                if (user.isPresent()) {
                    UserEntity userEntity = user.get();
                    if (passwordEncoder.matches(password, userEntity.getPassword())) {
                        CommentEntity commentEntity = comment.get();
                        ArticleEntity articleEntity = article.get();
                        articleEntity.setDeletedAt(LocalDateTime.now());
                        commentRepository.save(commentEntity);
                        articleRepository.save(articleEntity);
                    } else throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Incorrect password");
                } else throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
            } else throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Comment not found");
        } else throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Feed not found");
    }
}
