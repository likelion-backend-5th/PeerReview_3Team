package com.likelion.MutsaMarket.service;

import com.likelion.MutsaMarket.dto.CommentDto;
import com.likelion.MutsaMarket.entity.CommentEntity;
import com.likelion.MutsaMarket.entity.SalesItemEntity;
import com.likelion.MutsaMarket.repository.CommentRepository;
import com.likelion.MutsaMarket.repository.SalesItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.server.ResponseStatusException;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final SalesItemRepository itemRepository;
    private final SalesItemService itemService;

    // create
    public CommentDto createComment(Long itemId, CommentDto dto) {
        if (!itemRepository.existsById(itemId))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        CommentEntity newComment = new CommentEntity();
        newComment.setItemId(itemId);
        newComment.setWriter(dto.getWriter());
        newComment.setPassword(dto.getPassword());
        newComment.setContent(dto.getContent());
        return CommentDto.fromEntity(commentRepository.save(newComment));
    }

    // read pages
    @GetMapping
    public Page<CommentDto> readCommentPaged(Long itemId) {
        if (!itemRepository.existsById(itemId))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        Pageable pageable = PageRequest.of(0, 25, Sort.by("id"));
        Page<CommentEntity> commentEntity = commentRepository.findAll(pageable);
        return commentEntity.map(CommentDto::fromEntity);
    }

    // update
    public void updateComment(Long itemId, Long commentId, CommentDto dto) {
        Optional<SalesItemEntity> optionalSalesItem = itemRepository.findById(itemId);
        Optional<CommentEntity> optionalComment = commentRepository.findById(commentId);

        // 존재하지 않으면 예외 발생
        if (optionalSalesItem.isEmpty() && optionalComment.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        // 아니면 로직 진행
        CommentEntity targetEntity = optionalComment.get();

        // 대상 댓글이 대상 게시글의 댓글이 맞는지
        if (!targetEntity.getPassword().equals(dto.getPassword()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        targetEntity.setWriter(dto.getWriter());
        targetEntity.setContent(dto.getContent());
        CommentDto.fromEntity(commentRepository.save(targetEntity));
    }

    // delete
    public void deleteComment(Long itemId, Long commentId, CommentDto dto) {
        Optional<SalesItemEntity> optionalSalesItem = itemRepository.findById(itemId);
        Optional<CommentEntity> optionalComment = commentRepository.findById(commentId);

        // 존재하지 않으면 예외 발생
        if (optionalSalesItem.isEmpty() && optionalComment.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        // 아니면 로직 진행
        CommentEntity targetEntity = optionalComment.get();

        // 대상 댓글이 대상 게시글의 댓글이 맞는지
        if (!targetEntity.getPassword().equals(dto.getPassword()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        commentRepository.deleteById(commentId);
    }
}
