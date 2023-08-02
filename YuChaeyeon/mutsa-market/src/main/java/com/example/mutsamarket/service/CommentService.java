package com.example.mutsamarket.service;

import com.example.mutsamarket.dto.CommentDto;
import com.example.mutsamarket.dto.ResponseDto;
import com.example.mutsamarket.entity.CommentEntity;
import com.example.mutsamarket.entity.SalesItemEntity;
import com.example.mutsamarket.repository.CommentRepository;
import com.example.mutsamarket.repository.SalesItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository repository;
    private final SalesItemRepository salesItemRepository;

    public void createComment(CommentDto dto) {
        if (salesItemRepository.existsById(dto.getId()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        repository.save(dto.newEntity());
    }

    public Page<CommentDto> readCommentPaged(Long itemId) {
        Pageable pageable = PageRequest.of(
                0, 25, Sort.by("id"));
        Page<CommentEntity> commentEntities = repository.findAll(pageable);
        return commentEntities.map(CommentDto::fromEntity);
    }

    public void updateComment(Long itemId, Long id, CommentDto dto) {
        Optional<CommentEntity> entityOptional = repository.findById(id);
        if (entityOptional.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        CommentEntity entity = entityOptional.get();

        if (!itemId.equals(entity.getItemId()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        else if (dto.getPassword().equals(entity.getPassword())) {
            entity.setWriter(dto.getWriter());
            entity.setPassword(dto.getPassword());
            entity.setContent(dto.getContent());
            CommentDto.fromEntity(repository.save(entity));
        }
        else throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }

    public void updateCommentReply(Long id, CommentDto dto) {

    }

    public void updateCommentItemId(Long id, Long commentId) {
        Optional<CommentEntity> optionalComment
                = repository.findById(commentId);
        if (optionalComment.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        Optional<SalesItemEntity> optionalSalesItem
                = salesItemRepository.findById(id);

        if (optionalSalesItem.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        CommentEntity comment = optionalComment.get();
        SalesItemEntity salesItem = optionalSalesItem.get();

        comment.setSalesItem(salesItem);
        repository.save(comment);
    }

    public void deleteComment(Long itemId, Long id, CommentDto dto) {
        Optional<CommentEntity> optionalEntity = repository.findById(id);
        if (optionalEntity.isEmpty()) throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        CommentEntity entity = optionalEntity.get();

        if (entity.getItemId().equals(itemId)) {
            if (entity.getPassword().equals(dto.getPassword()))
                repository.deleteById(id);
        }
        else throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }
}
