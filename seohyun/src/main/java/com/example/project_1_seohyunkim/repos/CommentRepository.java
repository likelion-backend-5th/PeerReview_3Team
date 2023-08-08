package com.example.project_1_seohyunkim.repos;

import com.example.project_1_seohyunkim.entity.CommentEntity;
import com.example.project_1_seohyunkim.mapping.CommentsMapping;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
    Page<CommentsMapping> findAllProjectedByItemId(Long itemId, Pageable pageable);

    Optional<CommentEntity> findByIdAndItemId(Long id, Long itemId);

}
