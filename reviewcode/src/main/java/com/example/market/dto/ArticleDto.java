package com.example.market.dto;

import com.example.market.entity.ArticleEntity;
import lombok.Data;

@Data
public class ArticleDto {
    private Long id;
    private String writer;
    private String title;
    private int wantedPrice;
    private String description;
    private String password;

    public static ArticleDto fromEntity(ArticleEntity entity) {
        ArticleDto dto = new ArticleDto();
        dto.setId(entity.getId());
        dto.setWriter(entity.getWriter());
        dto.setTitle(entity.getTitle());
        dto.setWantedPrice(entity.getWantedPrice());
        dto.setDescription(entity.getDescription());
        dto.setPassword(entity.getPassword());
        return dto;




    }
}
