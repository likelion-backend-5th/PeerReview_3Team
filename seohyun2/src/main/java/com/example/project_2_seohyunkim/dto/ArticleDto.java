package com.example.project_2_seohyunkim.dto;

import com.example.project_2_seohyunkim.entity.ArticleEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ArticleDto {
    private Long id;
    private Long userId;
    private String title;
    private String content;
    private boolean draft;
    private LocalDateTime deletedAt;
    private List<CommentDto> commentList;
    private List<ArticleImagesDto> articleImgList;

    public static ArticleDto fromEntity(ArticleEntity entity) {
        ArticleDto articleDto = new ArticleDto();
        articleDto.setId(entity.getId());
        articleDto.setUserId(entity.getUser().getId());
        articleDto.setTitle(entity.getTitle());
        articleDto.setContent(entity.getContent());
        articleDto.setDraft(entity.isDraft());
        articleDto.setDeletedAt(entity.getDeletedAt());
        articleDto.setCommentList(CommentDto.dtoList(entity.getComments()));
        articleDto.setArticleImgList(ArticleImagesDto.dtoList(entity.getArticleImages()));
        return articleDto;
    }
}
