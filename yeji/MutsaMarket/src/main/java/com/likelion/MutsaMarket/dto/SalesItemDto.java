package com.likelion.MutsaMarket.dto;

import com.likelion.MutsaMarket.entity.SalesItemEntity;
import lombok.Data;

@Data
public class SalesItemDto {
    private Long id;
    private String title;
    private String description;
    private String imageURL;
    private Long minPriceWanted;
    private String status;
    private String writer;
    private String password;

    public static SalesItemDto fromEntity(SalesItemEntity entity) {
        SalesItemDto dto = new SalesItemDto();
        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        dto.setDescription(entity.getDescription());
        dto.setImageURL(entity.getImageURL());
        dto.setMinPriceWanted(entity.getMinPriceWanted());
        dto.setStatus(entity.getStatus());
        dto.setWriter(entity.getWriter());
        dto.setPassword(entity.getPassword());
        return dto;
    }
}
