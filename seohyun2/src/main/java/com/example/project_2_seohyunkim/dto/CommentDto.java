package com.example.project_2_seohyunkim.dto;

import com.example.project_2_seohyunkim.entity.CommentEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.security.core.parameters.P;

import java.util.ArrayList;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommentDto {
    private Long id;
    private String content;

    public static CommentDto fromEntity(CommentEntity entity) {
        CommentDto commentDto = new CommentDto();
        commentDto.setId(entity.getId());
        commentDto.setContent(entity.getContent());
        return commentDto;
    }

    public static List<CommentDto> dtoList(List<CommentEntity> entityList) {
        List<CommentDto> commentDtoList = new ArrayList<>();
        for (CommentEntity entity : entityList) {
            commentDtoList.add(CommentDto.fromEntity(entity));
        }
        return commentDtoList;
    }
}
