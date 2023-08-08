package com.example.project_2_seohyunkim.repos;

import com.example.project_2_seohyunkim.entity.ArticleImagesEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ArticleImgRepository extends JpaRepository<ArticleImagesEntity, Long> {
    Optional<ArticleImagesEntity> findByArticle_Id(Long id);
}
