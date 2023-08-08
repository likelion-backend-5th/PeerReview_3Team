package com.example.project_2_seohyunkim.dto;

import com.example.project_2_seohyunkim.entity.LikeArticleEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LikeArticleDto {
    private Long id;
    private Long ArticleId;
    private Long UserId;

    public static LikeArticleDto fromEntity(LikeArticleEntity entity) {
        LikeArticleDto likeArticleDto = new LikeArticleDto();
        likeArticleDto.setId(entity.getId());
        likeArticleDto.setArticleId(entity.getArticle().getId());
        likeArticleDto.setUserId(entity.getUser().getId());
        return likeArticleDto;
    }
}
