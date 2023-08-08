package com.example.mutsamarket.dto;

import com.example.mutsamarket.entity.SalesItemEntity;
import lombok.Data;
import org.springframework.web.bind.annotation.RequestBody;

@Data
public class SalesItemDto {
    private Long id;
    private String title;
    private String description;
    private String image_url;
    private Long minPriceWanted;
    private String status;
    private String writer;
    private String password;

    public static SalesItemDto fromEntity(@RequestBody SalesItemEntity entity) {
        SalesItemDto dto = new SalesItemDto();
        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        dto.setDescription(entity.getDescription());
        dto.setImage_url(entity.getImage_url());
        dto.setMinPriceWanted(entity.getMinPriceWanted());
        dto.setStatus(entity.getStatus());
        dto.setWriter(entity.getWriter());
        dto.setPassword(entity.getPassword());
        return dto;
    }

    public SalesItemEntity newEntity() {
        SalesItemEntity entity = new SalesItemEntity();
        entity.setId(id);
        entity.setTitle(title);
        entity.setDescription(description);
        entity.setImage_url(image_url);
        entity.setMinPriceWanted(minPriceWanted);
        entity.setStatus(status);
        entity.setWriter(writer);
        entity.setPassword(password);
        return entity;
    }
}
