package com.likelion.MutsaMarket.dto;

import com.likelion.MutsaMarket.entity.NegotiationEntity;
import lombok.Data;

@Data
public class NegotiationDto {
    private Long id;
    private Long itemId;
    private Integer suggestedPrice;
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
