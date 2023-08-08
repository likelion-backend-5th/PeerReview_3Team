package com.example.project_2_seohyunkim.repos;

import com.example.project_2_seohyunkim.entity.LikeArticleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeArticleRepository extends JpaRepository<LikeArticleEntity, Long> {
    Optional<LikeArticleEntity> findByArticleIdAndUserId(Long articleId, Long userId);
}
