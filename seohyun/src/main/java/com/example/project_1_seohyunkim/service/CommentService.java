package com.example.project_1_seohyunkim.service;

import com.example.project_1_seohyunkim.dto.CommentDto;
import com.example.project_1_seohyunkim.entity.CommentEntity;
import com.example.project_1_seohyunkim.entity.SalesItemEntity;
import com.example.project_1_seohyunkim.entity.UserEntity;
import com.example.project_1_seohyunkim.mapping.CommentsMapping;
import com.example.project_1_seohyunkim.repos.CommentRepository;
import com.example.project_1_seohyunkim.repos.SalesItemRepository;
import com.example.project_1_seohyunkim.repos.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final SalesItemRepository salesItemRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public CommentDto createComment(Long itemId, String username, String password, CommentDto dto) {
        Optional<UserEntity> user = userRepository.findByUsername(username);
        Optional<SalesItemEntity> optionalSalesItemEntity = salesItemRepository.findById(itemId);
        if (user.isPresent()) {
            String userPw = user.get().getPassword();
            if (passwordEncoder.matches(password, userPw)) {
                if (optionalSalesItemEntity.isPresent()) {
                    CommentEntity newComment = CommentEntity.builder()
                            .content(dto.getContent())
                            .reply(dto.getReply())
                            .item(optionalSalesItemEntity.get())
                            .user(user.get()).build();
                    return CommentDto.fromEntity(commentRepository.save(newComment));
                } else throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Item not found");
            } else throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Incorrect password");
        } else throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
    }

    public Page<CommentsMapping> readCommentsAll(Long itemId) {
        Pageable pageable = PageRequest.of(0, 25, Sort.by("id").ascending());
        Page<CommentsMapping> commentsMappingPage = commentRepository.findAllProjectedByItemId(itemId, pageable);
        return commentsMappingPage;
    }

    public CommentDto updateComment(Long itemId, Long id, String username, String password, CommentDto dto) {
        Optional<CommentEntity> optionalEntity = commentRepository.findByIdAndItemId(id, itemId);
        if (optionalEntity.isPresent()) {
            CommentEntity commentEntity = optionalEntity.get();
            Optional<UserEntity> user = userRepository.findByUsername(username);
            if (user.isPresent()) {
                String userPw = user.get().getPassword();
                if (passwordEncoder.matches(password, userPw)) {
                    commentEntity.setContent(dto.getContent());
                    commentRepository.save(commentEntity);
                    return CommentDto.fromEntity(commentEntity);
                } else throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Incorrect password");
            } else throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        } else throw new ResponseStatusException(HttpStatus.NOT_FOUND, "comment not found");
    }

    public CommentDto createReply(Long itemId, Long id, String username, String password, CommentDto dto) {
        Optional<CommentEntity> optionalCommentEntity = commentRepository.findByIdAndItemId(id, itemId);
        Optional<SalesItemEntity> optionalSalesItemEntity = salesItemRepository.findById(id);
        if (optionalCommentEntity.isPresent() && optionalSalesItemEntity.isPresent()) {
            CommentEntity commentEntity = optionalCommentEntity.get();
            Optional<UserEntity> user = userRepository.findByUsername(username);
            if (user.isPresent()) {
                String userPw = user.get().getPassword();
                if (passwordEncoder.matches(password, userPw)) {
                    commentEntity.setReply(dto.getReply());
                    commentRepository.save(commentEntity);
                    return CommentDto.fromEntity(commentEntity);
                } else throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Incorrect password");
            } else throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        } else throw new ResponseStatusException(HttpStatus.NOT_FOUND, "comment not found");
    }

    public void deleteComment(Long itemId, Long id, String username, String password, CommentDto dto) {
        Optional<CommentEntity> optionalEntity = commentRepository.findByIdAndItemId(id, itemId);
        if (optionalEntity.isPresent()) {
            Optional<UserEntity> user = userRepository.findByUsername(username);
            CommentEntity commentEntity = optionalEntity.get();
            if (user.isPresent()) {
                String userPw = user.get().getPassword();
                if (passwordEncoder.matches(password, userPw)) {
                    commentRepository.delete(commentEntity);
                } else throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Incorrect password");
            } else throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        } else throw new ResponseStatusException(HttpStatus.NOT_FOUND, "comment not found");
    }
}
