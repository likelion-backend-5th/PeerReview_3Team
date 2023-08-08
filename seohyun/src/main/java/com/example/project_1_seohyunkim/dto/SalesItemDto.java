package com.example.project_1_seohyunkim.dto;

import com.example.project_1_seohyunkim.entity.SalesItemEntity;
import com.example.project_1_seohyunkim.mapping.ItemInfoMapping;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;


@Data
@JsonInclude(JsonInclude.Include.NON_NULL) // NULL이면 출력안함
public class SalesItemDto {
    private Long id;

    @NotBlank(message = "title is required")
    private String title;

    @NotBlank(message = "description is required")
    private String description;

    @NotNull(message = "minPrice is required")
    private Integer minPriceWanted;

    private String imageUrl;
    private String status;

//    @NotBlank(message = "writer is required")
//    @Size(min = 2, message = "at least 2 characters long")
//    private String writer;
//
//    @Size(min = 8, message = "at least 8 characters long")
//    private String password;

    public static SalesItemDto fromEntity(SalesItemEntity salesItemEntity) {
        SalesItemDto salesItemDto = new SalesItemDto();
        salesItemDto.setId(salesItemEntity.getId());
        salesItemDto.setTitle(salesItemEntity.getTitle());
        salesItemDto.setDescription(salesItemEntity.getDescription());
        salesItemDto.setMinPriceWanted(salesItemEntity.getMinPriceWanted());
        salesItemDto.setImageUrl(salesItemEntity.getImageUrl());
        salesItemDto.setStatus(salesItemEntity.getStatus());
//        salesItemDto.setWriter(salesItemEntity.getWriter());
//        salesItemDto.setPassword(salesItemEntity.getPassword());
        return salesItemDto;
    }

    public static SalesItemDto fromMappingEntity(ItemInfoMapping itemInfoMapping) {
        SalesItemDto salesItemDto = new SalesItemDto();
        salesItemDto.setTitle(itemInfoMapping.getTitle());
        salesItemDto.setDescription(itemInfoMapping.getDescription());
        salesItemDto.setMinPriceWanted(itemInfoMapping.getMinPriceWanted());
        salesItemDto.setImageUrl(itemInfoMapping.getImageUrl());
        salesItemDto.setStatus(itemInfoMapping.getStatus());
        return salesItemDto;
    }

}
