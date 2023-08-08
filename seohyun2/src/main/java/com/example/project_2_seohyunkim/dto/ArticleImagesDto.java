package com.example.project_2_seohyunkim.dto;

import com.example.project_2_seohyunkim.entity.ArticleImagesEntity;
import com.example.project_2_seohyunkim.entity.CommentEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ArticleImagesDto {
    private Long id;
    private String imageUrl;

    public static ArticleImagesDto fromEntity(ArticleImagesEntity entity) {
        ArticleImagesDto articleImagesDto = new ArticleImagesDto();
        articleImagesDto.setId(entity.getId());
        articleImagesDto.setImageUrl(entity.getImageUrl());
        return articleImagesDto;
    }

    public static List<ArticleImagesDto> dtoList(List<ArticleImagesEntity> entityList) {
        List<ArticleImagesDto> articleImagesDtoList = new ArrayList<>();
        for (ArticleImagesEntity entity : entityList) {
            articleImagesDtoList.add(ArticleImagesDto.fromEntity(entity));
        }
        return articleImagesDtoList;
    }
}
