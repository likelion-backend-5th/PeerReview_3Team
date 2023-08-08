package com.example.project_2_seohyunkim.mapping;

import com.example.project_2_seohyunkim.entity.ArticleEntity;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ArticlesMappingImpl implements ArticlesMapping {
    private final ArticleEntity article;
    private Long id;
    private Long userId;
    private String title;
    private String content;
    private String imageUrl;

    @Override
    public Long getId() {
        return article.getId();
    }

    @Override
    public Long getUserId() {
        return article.getUser().getId();
    }

    @Override
    public String getTitle() {
        return article.getTitle();
    }

    @Override
    public String getContent() {
        return article.getContent();
    }

    @Override
    public String getImageUrl() {
        return article.getArticleImages().get(0).getImageUrl();
    }
}
