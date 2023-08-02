package com.example.project_1_seohyunkim.dto;

import com.example.project_1_seohyunkim.entity.NegotiationEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NegotiationDto {
    private Long id;
//    private Long itemId;

//    @NotNull(message = "suggestedPrice is required")
    private Integer suggestedPrice;

    private String status;

//    @NotBlank(message = "writer is required")
//    @Size(min = 2, message = "at least 2 characters long")
//    private String writer;
//
//    @Size(min = 8, message = "at least 8 characters long")
//    private String password;

    public static NegotiationDto fromEntity(NegotiationEntity negotiationEntity) {
        NegotiationDto negotiationDto = new NegotiationDto();
        negotiationDto.setId(negotiationEntity.getId());
//        negotiationDto.setItemId(negotiationEntity.getItemId());
        negotiationDto.setSuggestedPrice(negotiationEntity.getSuggestedPrice());
        negotiationDto.setStatus(negotiationEntity.getStatus());
//        negotiationDto.setWriter(negotiationEntity.getWriter());
//        negotiationDto.setPassword(negotiationEntity.getPassword());
        return negotiationDto;
    }
}
