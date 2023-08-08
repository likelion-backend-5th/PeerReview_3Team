package com.example.project_2_seohyunkim.repos;

import com.example.project_2_seohyunkim.entity.ArticleEntity;
import com.example.project_2_seohyunkim.entity.UserEntity;
import com.example.project_2_seohyunkim.mapping.ArticlesMapping;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ArticleRepository extends JpaRepository<ArticleEntity, Long>  {
    Optional<ArticleEntity> findById(Long id);
    Page<ArticlesMapping> findAllProjectedByUser(Optional<UserEntity> userEntity, Pageable pageable);
    Page<ArticleEntity> findAllByUser(Optional<UserEntity> userEntity, Pageable pageable);

}
