package com.example.mutsamarket.dto;

import com.example.mutsamarket.entity.NegotiationEntity;
import lombok.Data;

@Data
public class NegotiationDto {
    private Long id;
    private Long itemId;
    private Long suggestedPrice;
    private String status;
    private String writer;
    private String password;

    public static NegotiationDto fromEntity(NegotiationEntity entity) {
        NegotiationDto dto = new NegotiationDto();
        dto.setId(entity.getId());
        dto.setItemId(entity.getItemId());
        dto.setSuggestedPrice(entity.getSuggestedPrice());
        dto.setStatus(entity.getStatus());
        dto.setWriter(entity.getWriter());
        dto.setPassword(entity.getPassword());
        return dto;
    }
}
