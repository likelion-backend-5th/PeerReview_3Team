package com.example.project_2_seohyunkim.repos;

import com.example.project_2_seohyunkim.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
    Optional<CommentEntity> findById(Long id);
    Optional<CommentEntity> findByIdAndArticleId(Long id, Long articleId);
}
