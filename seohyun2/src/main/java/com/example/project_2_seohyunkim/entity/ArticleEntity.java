package com.example.project_2_seohyunkim.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "article")
public class ArticleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "users_id")
    private UserEntity user;

    @Column
    private String title;

    @Column
    private String content;

    @Column
    private boolean draft;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @OneToMany(mappedBy = "article")
    private List<CommentEntity> comments = new ArrayList<>();

    @OneToMany(mappedBy = "article")
    private List<ArticleImagesEntity> articleImages = new ArrayList<>();

    @OneToMany(mappedBy = "article")
    private List<LikeArticleEntity> likeArticles = new ArrayList<>();
}
